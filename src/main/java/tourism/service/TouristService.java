package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private TouristRepository repository;

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

    public TouristAttraction delete(String name) {
        repository.delete(name);
        return repository.findByName(name);
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
    public List<TouristAttraction> getAllAttractions(){
        return repository.getAttractions();
    }


}
