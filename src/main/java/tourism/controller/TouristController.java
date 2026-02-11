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

    @GetMapping
    public String listAttractions(Model model) {
        model.addAttribute("attractions", service.getAllAttractions());
        return "attractionList";
    }

    @GetMapping("/{name}")
    public String findAttractionByName(@PathVariable String name, Model model) {
        model.addAttribute("attraction", service.findByName(name));
        return "findAttractionByName";
    }

    @GetMapping("/{name}/tags")
    public String showTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.findByName(name);
        model.addAttribute("attraction", attraction);
        return "tags";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction());

        model.addAttribute("cities", service.getCities());
        model.addAttribute("tags", service.getTags());

        return "addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
        service.add(attraction);
        return "redirect:/attractions";
    }

}
