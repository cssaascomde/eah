package de.civento.eahtools.routingrepository.ui.ou;

import de.civento.eahtools.routingrepository.base.businessobjects.IPageBusinessObjects;
import de.civento.eahtools.routingrepository.impl.ou.Ou;
import de.civento.eahtools.routingrepository.impl.ou.OuSearchObject;
import de.civento.eahtools.routingrepository.impl.ou.OuService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Scope(value="session")
@RequestMapping(path = OuController.PATH)
public class OuController {
    private static final String ATTRIBUTE_SEARCH_OBJECT = "so";
    private static final String ATTRIBUTE_DATA = "data";
    private static final String ATTRIBUTE_DATA_COUNT = "dataCount";
    private static final String ATTRIBUTE_PAGE_CURRENT = "pageCurrent";
    private static final String ATTRIBUTE_PAGE_COUNT = "pageCount";
    public static final String PATH = "/ous";
    private final OuService service;
    private OuSearchObject searchObject = new OuSearchObject();

    public OuController(OuService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(required = false, defaultValue = "0") int page) {
        this.searchObject.setPageNumber(page);
        return prepareListView(model);
    }

    @PostMapping("/list")
    public String searchList(Model model, @ModelAttribute OuSearchObject so) {
        this.searchObject = so;
        return prepareListView(model);
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable String id) {
        model.addAttribute(ATTRIBUTE_DATA, this.service.getById(id));
        return "ous/detail";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute(ATTRIBUTE_DATA, new Ou());
        return "ous/detail";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Ou obj) {
        this.service.createOrUpdate(obj);
        return "redirect:/ous/list";
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        this.service.deleteById(id);
        return "redirect:/ous/list";
    }

    @NotNull
    private String prepareListView(Model model) {
        IPageBusinessObjects<Ou> result = this.service.search(this.searchObject);
        model.addAttribute(ATTRIBUTE_DATA, result.getContent());
        model.addAttribute(ATTRIBUTE_DATA_COUNT, result.getTotalElements());
        model.addAttribute(ATTRIBUTE_SEARCH_OBJECT, this.searchObject);
        model.addAttribute(ATTRIBUTE_PAGE_COUNT, result.getTotalPages());
        model.addAttribute(ATTRIBUTE_PAGE_CURRENT, result.getNumber());

        return "ous/list";
    }

}