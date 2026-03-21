package tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;


@Controller
@RequestMapping("/attractions")
public class TouristController {
    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }


    //DB-Method
    @GetMapping()
    public String listAttractionsFromDB(Model model) {
        model.addAttribute("attractions", service.getAllAttractionsFromDB());
        return "attractionListFromDB";
    }


    //DB-Method
    @GetMapping("/{id}")
    public String findAttractionByIdFromDB(@PathVariable int id, Model model) {
        model.addAttribute("attraction", service.findByIdFromDB(id));
        return "findAttractionByIdFromDB";
    }


    //DB-Method
    @GetMapping("/{id}/tags")
    public String showTagsFromDB(@PathVariable int id, Model model) {
        TouristAttraction attraction = service.findByIdFromDB(id);
        model.addAttribute("attraction", attraction);
        return "tagsFromDB";
    }


    //DB-Method
    @GetMapping("/add")
    public String showAddFormWithDB(Model model) {
        model.addAttribute("attraction", new TouristAttraction());

        model.addAttribute("cities", service.getAllCitiesFromDB());
        model.addAttribute("tags", service.getAllTagsFromDB());

        return "addAttractionWithDB";
    }


    //DB-Method
    @PostMapping("/save")
    public String saveAttractionToDB(@ModelAttribute TouristAttraction attraction) {
        service.addAttractionToDB(attraction);
        return "redirect:/attractions";
    }


    //DB-Method
    @GetMapping("/delete/{id}")
    public String deleteAttractionFromDB(@PathVariable int id) {
        service.deleteAttractionFromDB(id);
        return "redirect:/attractions";
    }


    //DB-Method
    // FORM
    @GetMapping("/{id}/edit")
    public String showEditFormForEditingInDB(@PathVariable int id, Model model) {
        TouristAttraction attraction = service.findByIdFromDB(id);

        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", service.getAllCitiesFromDB());
        model.addAttribute("tags", service.getAllTagsFromDB());

        return "editAttractionFromDB";
    }

    //DB-Method
    // EDIT HANDLER
    @PostMapping("/update")
    public String updateAttractionInDB(@ModelAttribute TouristAttraction attraction) {
        service.updateAttractionInDB(attraction);
        return "redirect:/attractions";
    }


}
