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
        attraction.setCity(getCityByAttractionId(attraction.getId()));
        attraction.setTagList(getTagsByAttractionId(attraction.getId()));
        List<Integer> tagIdsToAdd = new ArrayList<>();
        for (Tag tag : attraction.getTagList()){
            tagIdsToAdd.add(tag.getId());
        }
        attraction.setTagIds(tagIdsToAdd);
        return attraction;
    };


    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT * FROM attraction ORDER BY attraction_id";

        return jdbcTemplate.query(sql, attractionRowMapper);

    }


    public List<City> getAllCities() {
        String sql = "SELECT * FROM city ORDER BY city_id";
        return jdbcTemplate.query(sql, cityRowMapper);
    }

    public City getCityByAttractionId(int attractionId) {
        String sql = """
                SELECT city.city_id, city.name
                FROM city
                JOIN attraction
                    ON attraction.city_id = city.city_id
                WHERE attraction.attraction_id = ?
                """;

        return jdbcTemplate.queryForObject(sql, cityRowMapper, attractionId);
    }


    public List<Tag> getAllTags() {
        String sql = "SELECT * FROM tag ORDER BY tag_id";
        return jdbcTemplate.query(sql, tagRowMapper);
    }

    public List<Tag> getTagsByAttractionId(int attractionId) {
        String sql = """
                SELECT tag.tag_id, tag.name
                FROM tag 
                JOIN attraction_tag 
                    ON tag.tag_id = attraction_tag.tag_id
                WHERE attraction_tag.attraction_id = ?
                """;
        return jdbcTemplate.query(sql, tagRowMapper, attractionId);
    }


    public TouristAttraction createAttraction(TouristAttraction attraction) {
        String sql = "INSERT INTO attraction(name, description, city_id) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            ps.setInt(3, attraction.getCity_id());
            return ps;
        }, keyHolder);

        int attractionId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        addTagsToAttraction(attractionId, attraction.getTagIds());

        if (attractionId != -1) {
            //return new TouristAttraction(attractionId, attraction.getName(), attraction.getDescription(), attraction.getCity().getId());
            return findAttractionById(attractionId);
        } else {
            throw new RuntimeException("could not add the attraction");
        }
    }


    public TouristAttraction findAttractionById(int attractionId) {
        String sql = "SELECT * FROM attraction WHERE attraction_id = ?";
        return jdbcTemplate.queryForObject(sql, attractionRowMapper, attractionId);
    }


    public void updateAttraction(TouristAttraction attraction) {
        String sql = "UPDATE attraction SET name=?, description=?, city_id=? WHERE attraction_id=?";

        jdbcTemplate.update(sql,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity_id(),
                attraction.getId());

        updateTagsForAttraction(attraction.getId(), attraction.getTagIds());
    }

    public void deleteAttraction(int attractionId) {
        String sql = "DELETE FROM attraction WHERE attraction_id = ?";
        jdbcTemplate.update(sql, attractionId);
    }


    private void deleteTagsForAttraction(int attractionId) {
        String sql = "DELETE FROM attraction_tag WHERE attraction_id = ?";
        jdbcTemplate.update(sql, attractionId);
    }

    private void addTagsToAttraction(int attractionId, List<Integer> tagIds) {
        if (tagIds == null) return;

        String sql = "INSERT INTO attraction_tag(attraction_id, tag_id) VALUES (?, ?)";

        for (Integer tagId : tagIds) {
            jdbcTemplate.update(sql, attractionId, tagId);
        }
    }

    private void updateTagsForAttraction(int attractionId, List<Integer> tagIds) {
        deleteTagsForAttraction(attractionId);
        addTagsToAttraction(attractionId, tagIds);
    }

}

