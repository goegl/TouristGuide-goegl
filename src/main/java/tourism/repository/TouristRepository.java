package tourism.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tourism.model.City;
import tourism.model.Tag;
import tourism.model.TouristAttraction;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        attraction.setCity(getCityByAttractionIdFromDB(attraction.getId()));
        attraction.setTagList(getTagsByAttractionIdFromDB(attraction.getId()));
        return attraction;
    };


    public TouristRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        populateAttractions();
    }

    public List<TouristAttraction> getAttractionsFromDB(){
        String sql = "SELECT * FROM attraction";

        return jdbcTemplate.query(sql, attractionRowMapper);

    }

    public List<TouristAttraction> getAttractions() {
        return attractions;
    }

    public List<City> getCitiesFromDB(){
        String sql = "SELECT * FROM city";
        return jdbcTemplate.query(sql, cityRowMapper);
    }

    public City getCityByAttractionIdFromDB(int attractionId){
        String sql = """
                SELECT city.city_id, city.name
                FROM city
                JOIN attraction
                    ON attraction.city_id = city.city_id
                WHERE attraction.attraction_id = ?
                """;

        return jdbcTemplate.queryForObject(sql, cityRowMapper, attractionId);
    }

    public List<String> getCities() {
        return cities;
    }

    public List<Tag> getTagsFromDB(){
        String sql = "SELECT * FROM tag";
        return jdbcTemplate.query(sql, tagRowMapper);
    }

    public List<Tag> getTagsByAttractionIdFromDB(int attractionId){
        String sql = """
                SELECT tag.tag_id, tag.name
                FROM tag 
                JOIN attraction_tag 
                    ON tag.tag_id = attraction_tag.tag_id
                WHERE attraction_tag.attraction_id = ?
                """;
        return jdbcTemplate.query(sql, tagRowMapper, attractionId);
    }

    public List<String> getTags() {
        return tags;
    }

    public TouristAttraction addAttractionToDB(TouristAttraction attraction){
        String sql = "INSERT INTO attraction(name, description, city_id) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            ps.setInt(3, attraction.getCity_id());
            return ps;
        }, keyHolder);

        int attractionId = keyHolder.getKey() != null ? keyHolder.getKey().intValue(): -1;
        addTagsToAttraction(attractionId, attraction.getTagIds());

        if (attractionId != -1){
            //return new TouristAttraction(attractionId, attraction.getName(), attraction.getDescription(), attraction.getCity().getId());
            return findByIdFromDB(attractionId);
        }
        else {
            throw new RuntimeException("could not add the attraction");
        }
    }

    public TouristAttraction add(TouristAttraction attraction){
        if(attraction.getName() != null) {
            attractions.add(attraction);
        }
        return attraction;
    }

    public TouristAttraction findByIdFromDB(int id){
        String sql = "SELECT * FROM attraction WHERE attraction_id = ?";
        return jdbcTemplate.queryForObject(sql, attractionRowMapper, id);
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
        existing.setCityOld(attraction.getCityOld());
        existing.setTags(attraction.getTags());
    }

    public void delete(String name){
        TouristAttraction found = findByName(name);
        if (found != null) {
            attractions.remove(found);
        }
    }

    private void addTagsToAttraction(int attractionId, List<Integer> tagIds){
        if (tagIds == null) return;

        String sql = "INSERT INTO attraction_tag(attraction_id, tag_id) VALUES (?, ?)";

        for (Integer tagId : tagIds){
            jdbcTemplate.update(sql, attractionId, tagId);
        }
    }

}
