package tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tourism.service.TouristService;

@Controller
@RequestMapping("/attractions")
public class TouristController {
private final TouristService service;

    public TouristController(TouristService service){
        this.service = service;
    }

    @GetMapping
    public String listAttractions(Model model){
        model.addAttribute("attractions", service.getAllAttractions());
        return "attractionList";
    }
    @GetMapping("/{name}")
    public String findAttractionByName(@PathVariable String name, Model model){
        model.addAttribute("attraction", service.findByName(name));
        return "findattractionbyname";
    }
    @GetMapping("/{name}/tags")
    public String showTags(@PathVariable String name, Model model){
        model.addAttribute("attraction", service.findByName(name));
        return "tags";
    }
}
