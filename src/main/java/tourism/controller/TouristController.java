package tourism.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.List;

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

    // ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction());

        model.addAttribute("cities", service.getCities());
        model.addAttribute("tags", service.getTags());

        return "addAttraction";
    }

    // ADD HANDLER
    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
        service.add(attraction);
        return "redirect:/attractions";
    }

    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name){
        service.delete(name);
        return "redirect:/attractions";
    }

    //EDIT FORM
    @GetMapping("/{name}/edit")
    public String showEditForm(@PathVariable String name, Model model){
        TouristAttraction attraction = service.findByName(name);
        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", service.getCities());
        model.addAttribute("tags", service.getTags());

        return "editAttraction";
    }

    // EDIT HANDLER
    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction){
        service.update(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/db")
    public ResponseEntity<List<TouristAttraction>> getTagListFromDB(){
        List<TouristAttraction> attractions = service.getAllAttractionsFromDB();
        return new ResponseEntity<List<TouristAttraction>>(attractions,HttpStatus.OK);
    }

}
