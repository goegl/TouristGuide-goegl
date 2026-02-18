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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(model().attribute("attraction", mock));


    }

    @Test
    void showTags() {
    }

    @Test
    void shouldShowAddForm() throws Exception{
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addAttraction"));
    }

    @Test
    void saveAttraction() throws Exception{
        TouristAttraction touristAttraction = new TouristAttraction("Tivoli", "En forlystelsespark", "København", List.of("Sjovt", "Klassisk"));
        when(service.add(any(TouristAttraction.class))).thenReturn(touristAttraction);

        mockMvc.perform(post("/attractions/save")
                        .param("name", "Tivoli")
                        .param("description", "En forlystelsespark")
                        .param("city", "København")
                        .param("tags", String.valueOf(List.of("Sjovt", "Klassisk"))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).add(captor.capture());
        TouristAttraction captured = captor.getValue();
        assertEquals("Tivoli", captured.getName());
        assertEquals("En forlystelsespark", captured.getDescription());
        assertEquals("København", captured.getCity());
        assertNotNull(captured.getTags());
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