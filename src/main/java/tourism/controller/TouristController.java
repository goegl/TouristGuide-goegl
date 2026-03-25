package tourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;


@Controller
@RequestMapping("/attractions")
public class TouristController {
    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }


    @GetMapping()
    public String showAllAttractions(Model model) {
        model.addAttribute("attractions", service.getAllAttractions());
        return "attraction-list";
    }


    @GetMapping("/{attractionId}")
    public String showAttraction(@PathVariable int attractionId, Model model) {
        model.addAttribute("attraction", service.findAttractionById(attractionId));
        return "attraction-details";
    }


    @GetMapping("/{attractionId}/tags")
    public String showTagsForAttraction(@PathVariable int attractionId, Model model) {
        TouristAttraction attraction = service.findAttractionById(attractionId);
        model.addAttribute("attraction", attraction);
        return "attraction-tags";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction());

        model.addAttribute("cities", service.getAllCities());
        model.addAttribute("tags", service.getAllTags());

        return "attraction-add-form";
    }


    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction, RedirectAttributes redirectAttributes) {
        TouristAttraction savedAttraction = service.createAttraction(attraction);
        redirectAttributes.addFlashAttribute("attraction", savedAttraction);
        return "redirect:/attractions/confirm-save";
    }

    @GetMapping("/confirm-save")
    public String confirmSavedAttraction() {
        return "confirmation-templates/attraction-confirm-save";
    }


    @GetMapping("/delete/{attractionId}")
    public String deleteAttraction(@PathVariable int attractionId, RedirectAttributes redirectAttributes) {
        TouristAttraction deletedAttraction = service.deleteAttraction(attractionId);
        redirectAttributes.addFlashAttribute("attraction", deletedAttraction);
        return "redirect:/attractions/confirm-delete";
    }

    @GetMapping("/confirm-delete")
    public String confirmDeletedAttraction() {
        return "confirmation-templates/attraction-confirm-delete";
    }

    @GetMapping("/{attractionId}/edit")
    public String showEditForm(@PathVariable int attractionId, Model model) {
        TouristAttraction attraction = service.findAttractionById(attractionId);

        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", service.getAllCities());
        model.addAttribute("tags", service.getAllTags());

        return "attraction-edit";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        service.updateAttraction(attraction);
        return "redirect:/attractions";
    }


}
