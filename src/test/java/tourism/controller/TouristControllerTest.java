package tourism.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldListAttractions() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"));
    }

    @Test
    void shouldFindAttractionByName() throws Exception {
        TouristAttraction mock = new TouristAttraction(/*"HoolaHoopRink", "yay", "Grenaa", List.of("tag")*/);
        when(service.findByName("HoolaHoopRink")).thenReturn(mock);

        mockMvc.perform(get("/attractions/HoolaHoopRink"))
                .andExpect(status().isOk())
                .andExpect(view().name("findAttractionByName"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", mock));


//        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
//        TouristAttraction captured = captor.getValue();
//        assertEquals("HoolaHoopRink", captured.getName());
//        assertEquals("yay", captured.getDescription());
//        assertEquals("Grenaa", captured.getCity());
//        assertNotNull(captured.getTags());
    }

    @Test
    void showTags() {
    }

    @Test
    void showAddForm() {
    }

    @Test
    void saveAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void showEditForm() {
    }

    @Test
    void updateAttraction() {
    }
}