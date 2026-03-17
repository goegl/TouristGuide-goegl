package tourism.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tourism.model.City;
import tourism.model.Tag;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private final JdbcTemplate jdbcTemplate;
    List<TouristAttraction> attractions = new ArrayList<>();
    private final List<String> cities = new ArrayList<>(List.of("Grenaa","København", "Århus", "Roskilde", "Slagelse"));
    private final List<String> tags = new ArrayList<>(List.of("Cafe", "Cats", "Cozy","Sea Creatures", "Acrobatics"));

    private final RowMapper<City> cityRowMapper = (rs, rowNum) -> {
        City city = new City();
        city.setId(rs.getInt("city_id"));
        city.setName(rs.getString("name"));
        return city;
    };

    private final RowMapper<Tag> tagRowMapper = (rs, rowNum) -> {
        Tag tag = new Tag();
        tag.setId(rs.getInt("tag_id"));
        tag.setName(rs.getString("name"));
        return tag;
    };

    private final RowMapper<TouristAttraction> attractionRowMapper = (rs, rowNum) -> {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setId(rs.getInt("attraction_id"));
        attraction.setCity_id(rs.getInt("city_id"));
        attraction.setName(rs.getString("name"));
        attraction.setDescription(rs.getString("description"));

    }


    public TouristRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        populateAttractions();
    }

    public List<TouristAttraction> getAttractions() {
        return attractions;
    }

    public List<String> getCities() {
        return cities;
    }

    public List<String> getTags() {
        return tags;
    }


    public TouristAttraction add(TouristAttraction attraction){
        if(attraction.getName() != null) {
            attractions.add(attraction);
        }
        return attraction;
    }

    public void populateAttractions(){
        add(new TouristAttraction("Dolphin Show", "A show with dolphins", "Grenaa", List.of("Sea Creatures", "Acrobatics")));
        add(new TouristAttraction("Cat Cafe", "A cafe with cats you can pet", "København", List.of("Cafe", "Cats", "Cozy")));
    }

    public TouristAttraction findByName(String name){
        for (TouristAttraction attraction : attractions){
            if (attraction.getName().equalsIgnoreCase(name)){
                return attraction;
            }
        }
        return null;
    }

    public void update(TouristAttraction attraction) {
        TouristAttraction existing = findByName(attraction.getName());
        if (existing == null) {
            return;
        }

        existing.setDescription(attraction.getDescription());
        existing.setCity(attraction.getCity());
        existing.setTags(attraction.getTags());
    }

    public void delete(String name){
        TouristAttraction found = findByName(name);
        if (found != null) {
            attractions.remove(found);
        }
    }

}
