package de.civento.eahtools.routingrepository.ui.responsibility;

import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.impl.ou.Ou;
import de.civento.eahtools.routingrepository.impl.ou.OuSearchObject;
import de.civento.eahtools.routingrepository.impl.ou.OuService;
import de.civento.eahtools.routingrepository.impl.responsibility.Responsibility;
import de.civento.eahtools.routingrepository.impl.responsibility.ResponsibilitySearchObject;
import de.civento.eahtools.routingrepository.impl.responsibility.ResponsibilityService;
import de.civento.eahtools.routingrepository.impl.service.Service;
import de.civento.eahtools.routingrepository.impl.service.ServiceSearchObject;
import de.civento.eahtools.routingrepository.impl.service.ServiceService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Scope(value="session")
@RequestMapping(path = ResponsibilityController.PATH)
public class ResponsibilityController {
    private static final String ATTRIBUTE_SEARCH_OBJECT = "so";
    private static final String ATTRIBUTE_DATA = "data";
    private static final String ATTRIBUTE_DATA_COUNT = "dataCount";
    private static final String ATTRIBUTE_PAGE_CURRENT = "pageCurrent";
    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";
    private static final String ATTRIBUTE_OUS = "ous";
    private static final String ATTRIBUTE_SERVICES = "services";
    public static final String PATH = "/responsibilities";
    private final ResponsibilityService service;
    private final OuService ouService;
    private final ServiceService serviceService;
    private ResponsibilitySearchObject searchObject = new ResponsibilitySearchObject();

    public ResponsibilityController(ResponsibilityService service,
                                    OuService ouService,
                                    ServiceService serviceService) {
        this.service = service;
        this.ouService = ouService;
        this.serviceService = serviceService;
    }

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(required = false, defaultValue = "0") int page) {
        this.searchObject.setPageNumber(page);
        return prepareListView(model);
    }

    @PostMapping("/list")
    public String searchList(Model model, @ModelAttribute ResponsibilitySearchObject so) {
        this.searchObject = so;
        return prepareListView(model);
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable String id) {
        model.addAttribute(ATTRIBUTE_DATA, this.service.getById(id));
        model.addAttribute(ATTRIBUTE_OUS, getOus());
        model.addAttribute(ATTRIBUTE_SERVICES, getServices());
        return "/responsibilities/detail";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute(ATTRIBUTE_DATA, new Responsibility());
        model.addAttribute(ATTRIBUTE_OUS, getOus());
        model.addAttribute(ATTRIBUTE_SERVICES, getServices());
        return "/responsibilities/detail";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Responsibility obj) {
        this.service.createOrUpdate(obj);
        return "redirect:/responsibilities/list";
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        this.service.deleteById(id);
        return "redirect:/responsibilities/list";
    }

    @GetMapping("/ous")
    public List<AutoCompleteResult> getOus(@RequestParam(value = "q", required = false) String query) {
        ArrayList<AutoCompleteResult> list = new ArrayList<>();
        OuSearchObject so = new OuSearchObject();
        if (!StringUtils.hasLength(query)) {
            so.setName("*");
        } else {
            so.setName("*" + query + "*");
        }

        for (Ou ou : this.ouService.search(so).getContent()) {
            list.add(AutoCompleteResult.builder().id(ou.getId()).text(ou.getName()).build());
        }

        return list;
    }


    @NotNull
    private String prepareListView(Model model) {
        IPageBusinessObjects<Responsibility> result = this.service.search(this.searchObject);
        model.addAttribute(ATTRIBUTE_DATA, result.getContent());
        model.addAttribute(ATTRIBUTE_DATA_COUNT, result.getTotalElements());
        model.addAttribute(ATTRIBUTE_SEARCH_OBJECT, this.searchObject);
        model.addAttribute(ATTRIBUTE_PAGE_COUNT, result.getTotalPages());
        model.addAttribute(ATTRIBUTE_PAGE_CURRENT, result.getNumber());

        model.addAttribute(ATTRIBUTE_OUS, getOus());
        model.addAttribute(ATTRIBUTE_SERVICES, getServices());

        return "/responsibilities/list";
    }

    private List<AutoCompleteResult> getOus() {
        ArrayList<AutoCompleteResult> list = new ArrayList<>();
        for (Ou item : this.ouService.search(new OuSearchObject()).getContent()) {
            list.add(AutoCompleteResult.builder().id(item.getId()).text(item.getName()).build());
        }
        return list;
    }

    private List<AutoCompleteResult> getServices() {
        ArrayList<AutoCompleteResult> list = new ArrayList<>();
        for (Service item : this.serviceService.search(new ServiceSearchObject()).getContent()) {
            list.add(AutoCompleteResult.builder().id(item.getId()).text(item.getName()).build());
        }
        return list;
    }

}