package tourism.attraction_h2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
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

    //Need updateAttractionTest

    //Need getAllTagsTest

    //Need createAttractionTest

    //Need deleteAttractionTest
}
