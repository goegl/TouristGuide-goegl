package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.City;
import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public TouristAttraction add(TouristAttraction attraction) {
        repository.add(attraction);
        return attraction;
    }

    public void update(TouristAttraction attraction) {
        repository.update(attraction);
    }

    public void delete(String name) {
        repository.delete(name);
    }

    public List<String> getCities() {
        return repository.getCities();
    }

    public List<String> getTags() {
        return repository.getTags();
    }
    public TouristAttraction findByName(String name){
        return repository.findByName(name);
    }

    public TouristAttraction findByIdFromDB(int id){ return repository.findByIdFromDB(id);}

    public List<TouristAttraction> getAllAttractionsFromDB(){
        return repository.getAttractionsFromDB();
    }

    public List<TouristAttraction> getAllAttractions(){
        return repository.getAttractions();
    }

    public List<City> getAllCitiesFromDB(){
        return repository.getCitiesFromDB();
    }

    public List<Tag> getAllTagsFromDB(){
        return repository.getTagsFromDB();
    }


}
