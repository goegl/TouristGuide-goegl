package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.City;
import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }


    public TouristAttraction createAttraction(TouristAttraction attraction) {
        return repository.createAttraction(attraction);
    }


    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        repository.updateAttraction(attraction);
        for (City city : getAllCities()){
            if (city.getId() == attraction.getCity_id()){
                attraction.setCity(city);
            }
        }
        List<Tag> tempTagList = new ArrayList<>();
        for(Tag tag : getAllTags()){
            if(attraction.getTagIds().contains(tag.getId())){
                tempTagList.add(tag);
            }
        }
        attraction.setTagList(tempTagList);
        return attraction;
    }


    public TouristAttraction deleteAttraction(int attractionId) {
        TouristAttraction attractionToBeDeleted = repository.findAttractionById(attractionId);
        repository.deleteAttraction(attractionId);
        return attractionToBeDeleted;
    }


    public TouristAttraction findAttractionById(int attractionId) {
        return repository.findAttractionById(attractionId);
    }

    public List<TouristAttraction> getAllAttractions() {
        return repository.getAllAttractions();
    }

    public List<City> getAllCities() {
        return repository.getAllCities();
    }

    public List<Tag> getAllTags() {
        return repository.getAllTags();
    }


}
