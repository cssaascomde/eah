package de.civento.eahtools.routingrepository.ui.service;

import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.db.DeliveryType;
import de.civento.eahtools.routingrepository.db.ResponsibilityType;
import de.civento.eahtools.routingrepository.impl.service.Service;
import de.civento.eahtools.routingrepository.impl.service.ServiceSearchObject;
import de.civento.eahtools.routingrepository.impl.service.ServiceService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Scope(value="session")
@RequestMapping(path = ServiceController.PATH)
public class ServiceController {
    private static final String ATTRIBUTE_SEARCH_OBJECT = "so";
    private static final String ATTRIBUTE_DATA = "data";
    private static final String ATTRIBUTE_DATA_COUNT = "dataCount";
    private static final String ATTRIBUTE_PAGE_CURRENT = "pageCurrent";
    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";
    public static final String PATH = "/services";
    private final ServiceService service;
    private ServiceSearchObject searchObject = new ServiceSearchObject();

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(required = false, defaultValue = "0") int page) {
        this.searchObject.setPageNumber(page);
        return prepareListView(model);
    }

    @PostMapping("/list")
    public String searchList(Model model, @ModelAttribute ServiceSearchObject so) {
        this.searchObject = so;
        return prepareListView(model);
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable String id) {
        model.addAttribute(ATTRIBUTE_DATA, this.service.getById(id));
        return "services/detail";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        Service obj = new Service();
        obj.setDeliveryType(DeliveryType.internal);
        obj.setResponsibilityType(ResponsibilityType.individual);
        model.addAttribute(ATTRIBUTE_DATA, obj);
        return "services/detail";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Service obj) {
        this.service.createOrUpdate(obj);
        return "redirect:/services/list";
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        this.service.deleteById(id);
        return "redirect:/services/list";
    }

    private String prepareListView(Model model) {
        IPageBusinessObjects<Service> result = this.service.search(this.searchObject);
        model.addAttribute(ATTRIBUTE_DATA, result.getContent());
        model.addAttribute(ATTRIBUTE_DATA_COUNT, result.getTotalElements());
        model.addAttribute(ATTRIBUTE_SEARCH_OBJECT, this.searchObject);
        model.addAttribute(ATTRIBUTE_PAGE_COUNT, result.getTotalPages());
        model.addAttribute(ATTRIBUTE_PAGE_CURRENT, result.getPageNumber());

        return "services/list";
    }

}