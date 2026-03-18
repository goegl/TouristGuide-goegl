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
        TouristAttraction touristAttraction = new TouristAttraction();
        when(service.findByName("HoolaHoopRink")).thenReturn(touristAttraction);

        mockMvc.perform(get("/attractions/HoolaHoopRink"))
                .andExpect(status().isOk())
                .andExpect(view().name("findAttractionByName"))
                .andExpect(model().attribute("attraction", touristAttraction));
        verify(service).findByName("HoolaHoopRink");
    }

    @Test
    void shouldShowTags() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction("Tivoli", "En forlystelsespark", "København", List.of("Sjovt", "Klassisk"));
        when(service.findByName("Tivoli")).thenReturn(touristAttraction);
        mockMvc.perform(get("/attractions/Tivoli/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"));
        verify(service).findByName("Tivoli");
    }

    @Test
    void shouldShowAddForm() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addAttraction"));
    }

    @Test
    void shouldSaveAttraction() throws Exception {
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
        assertEquals("København", captured.getCityOld());
        assertNotNull(captured.getTags());
    }

    @Test
    void shouldDeleteAttraction() throws Exception {
        mockMvc.perform(post("/attractions/delete/Tivoli"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(service).delete("Tivoli");
    }

    @Test
    void shouldShowEditForm() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction("Tivoli", "En forlystelsespark", "København", List.of("Sjovt", "Klassisk"));

        when(service.findByName("Tivoli")).thenReturn(touristAttraction);

        mockMvc.perform(get("/attractions/Tivoli/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("editAttraction"))
                .andExpect(model().attribute("attraction", touristAttraction));
        verify(service).findByName("Tivoli");

    }

    @Test
    void shouldUpdateAttraction() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction("Tivoli", "En forlystelsespark", "København", List.of("Sjovt", "Klassisk"));
        when(service.findByName("Tivoli")).thenReturn(touristAttraction);
        mockMvc.perform(post("/attractions/update")
                        .param("name", "Tivoli")
                        .param("description", "En forlystelsespark")
                        .param("city", "Grenaa")
                        .param("tags", "Sjovt", "Klassisk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
        verify(service).update((any(TouristAttraction.class)));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).update(captor.capture());
        TouristAttraction captured = captor.getValue();
        assertEquals("Tivoli", captured.getName());
        assertEquals("En forlystelsespark", captured.getDescription());
        assertEquals("Grenaa", captured.getCityOld());
        assertEquals(List.of("Sjovt", "Klassisk"), captured.getTags());
        assertNotNull(captured.getTags());
    }
}

