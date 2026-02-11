package tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "attractionlist";
    }
}
