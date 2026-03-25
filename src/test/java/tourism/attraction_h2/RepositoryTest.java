/*
package tourism.attraction_h2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class RepositoryTest {
    @Autowired
    private TouristRepository repository;

    @Test
    void readAll() {
        List<TouristAttraction> all = repository.getAllAttractions();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getName()).isEqualTo("Dolphin Show");
        assertThat(all.get(1).getName()).isEqualTo("Cat Cafe");
    }

    //Need findByIdTest
    @Test
    void shouldFindById() {
        TouristAttraction attraction = repository.findAttractionById(1);
        assertThat(attraction.getId()).isEqualTo(1);
        assertThat(attraction.getName()).isEqualTo("Dolphin Show");
        assertThat(attraction.getDescription()).isEqualTo("A show with dolphins");
        assertThat(attraction.getCity_id()).isEqualTo(1);
        assertThat(attraction.getTagList()).isNotNull();
        assertThat(attraction.getCity().getName()).isEqualTo(repository.getAllCities().getFirst().getName());
        assertThat(attraction.getTagList().getFirst().getName()).isEqualTo(repository.getAllTags().get(3).getName());
        assertThat(attraction.getTagIds()).isEqualTo(List.of(4, 5));
    }

    //Need updateAttractionTest
    @Test
    void shouldUpdateAttraction() {
        TouristAttraction updatedAttraction = new TouristAttraction(1, "Whale Show", "A Show With Whales", 2);
        updatedAttraction.setTagIds(List.of(1, 2));
        updatedAttraction.setCity(repository.getAllCities().get(1));
        repository.updateAttraction(updatedAttraction);
        assertThat(repository.findAttractionById(1).getName()).isEqualTo("Whale Show");
        assertThat(repository.findAttractionById(1).getDescription()).isEqualTo("A Show With Whales");
        assertThat(updatedAttraction.getCity().getName()).isEqualTo("København");
        assertThat(repository.findAttractionById(1).getTagIds()).isEqualTo(List.of(1, 2));
    }

    //Need getAllTagsTest
    @Test
    void shouldGetAllTags(){
        List<Tag> foundTags = repository.getAllTags();
        assertThat(foundTags).hasSize(5);
        assertThat(foundTags.getFirst().getName()).isEqualTo("Cafe");
        assertThat(foundTags.getFirst().getId()).isEqualTo(1);
    }

    //Need createAttractionTest
    @Test
    void shouldCreateAttraction(){
        TouristAttraction newAttraction = new TouristAttraction();
        newAttraction.setName("Whale Show");
        newAttraction.setDescription("A Show With Whales");
        newAttraction.setCity_id(2);
        newAttraction.setTagIds(List.of(1, 2));
        newAttraction.setTagList(List.of(repository.getAllTags().get(0), repository.getAllTags().get(1)));
        newAttraction.setCity(repository.getAllCities().get(1));

        repository.createAttraction(newAttraction);
        assertThat(repository.findAttractionById(3).getName()).isEqualTo("Whale Show");
        assertThat(repository.findAttractionById(3).getDescription()).isEqualTo("A Show With Whales");
        assertThat(repository.findAttractionById(3).getCity().getName()).isEqualTo("København");
        assertThat(repository.findAttractionById(3).getTagIds()).isEqualTo(List.of(1, 2));
        assertThat(repository.findAttractionById(3).getTagList().getFirst().getName()).isEqualTo("Cafe");
    }

    //Need deleteAttractionTest
    @Test
    void shouldDeleteAttraction(){
        repository.deleteAttraction(1);
        repository.deleteAttraction(2);
        assertThat(repository.getAllAttractions()).hasSize(0);
    }
}
*/
