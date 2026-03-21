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



    public TouristAttraction addAttractionToDB(TouristAttraction attraction){
        return repository.addAttractionToDB(attraction);
    }



    public void updateAttractionInDB(TouristAttraction attraction){
        repository.updateAttractionInDB(attraction);
    }



    //DB-Method
    public TouristAttraction deleteAttractionFromDB(int attractionId){
        TouristAttraction attractionToBeDeleted = repository.findByIdFromDB(attractionId);
        repository.deleteAttractionFromDB(attractionId);
        return attractionToBeDeleted;
    }


    public TouristAttraction findByIdFromDB(int id){ return repository.findByIdFromDB(id);}

    public List<TouristAttraction> getAllAttractionsFromDB(){
        return repository.getAttractionsFromDB();
    }

    public List<City> getAllCitiesFromDB(){
        return repository.getCitiesFromDB();
    }

    public List<Tag> getAllTagsFromDB(){
        return repository.getTagsFromDB();
    }


}
