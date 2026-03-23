package tourism.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tourism.model.City;
import tourism.model.Tag;
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
                .andExpect(view().name("attraction-list"));
    }

    @Test
    void shouldFindAttractionById() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction(1, "HoolaHoopRink", "A rink where you hoolahoop", 1);
        touristAttraction.setCity(new City(1, "Chicago"));
        when(service.findAttractionById(1)).thenReturn(touristAttraction);

        mockMvc.perform(get("/attractions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-details"))
                .andExpect(model().attribute("attraction", touristAttraction));
        verify(service).findAttractionById(1);
    }

    @Test
    void shouldShowTags() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction(1, "HoolaHoopRink", "Great fun with friends", 1);

        when(service.findAttractionById(1)).thenReturn(touristAttraction);
        mockMvc.perform(get("/attractions/1/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-tags"));
        verify(service).findAttractionById(1);
    }

    @Test
    void shouldShowAddForm() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-add-form"));
    }

    @Test
    void shouldSaveAttraction() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(1);
        touristAttraction.setName("Tivoli");
        touristAttraction.setDescription("En forlystelsespark");
        touristAttraction.setCity_id(1);
        touristAttraction.setTagIds(List.of(1,2));

        when(service.createAttraction(any(TouristAttraction.class))).thenReturn(touristAttraction);

        mockMvc.perform(post("/attractions/save")
                .param("name", "Tivoli")
                .param("description", "En forlystelsespark")
                .param("id","1")
                .param("city_id", "1")
                .param("tagIds", "1","2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).createAttraction(captor.capture());
        TouristAttraction captured = captor.getValue();

        assertEquals("Tivoli", captured.getName());
        assertEquals("En forlystelsespark", captured.getDescription());
        assertEquals(1, captured.getCity_id());
        assertEquals(List.of(1,2), captured.getTagIds());
    }

    @Test
    void shouldDeleteAttraction() throws Exception {
        mockMvc.perform(get("/attractions/delete/3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(service).deleteAttraction(3);
    }


    @Test
    void shouldShowEditForm() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(1);
        touristAttraction.setName("Tivoli");
        touristAttraction.setDescription("En forlystelsespark");
        touristAttraction.setCity_id(1);

        when(service.findAttractionById(1)).thenReturn(touristAttraction);

        mockMvc.perform(get("/attractions/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-edit"))
                .andExpect(model().attribute("attraction", touristAttraction));
        verify(service).findAttractionById(1);
    }

    @Test
    void shouldUpdateAttraction() throws Exception {
        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(1);
        touristAttraction.setName("Tivoli");
        touristAttraction.setDescription("En forlystelsespark");
        touristAttraction.setCity_id(1);
        touristAttraction.setTagIds(List.of());

        when(service.findAttractionById(1)).thenReturn(touristAttraction);

        mockMvc.perform(post("/attractions/update")
                        .param("name", "Opdateret Navn")
                        .param("description", "Opdateret Beskrivelse")
                        .param("id","1")
                        .param("city_id", "3")
                        .param("tagIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(service).updateAttraction((any(TouristAttraction.class)));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(service).updateAttraction(captor.capture());
        TouristAttraction captured = captor.getValue();

        assertEquals(1, captured.getId());
        assertEquals("Opdateret Navn", captured.getName());
        assertEquals("Opdateret Beskrivelse", captured.getDescription());
        assertEquals(3, captured.getCity_id());
        assertEquals(List.of(1,2), captured.getTagIds());

    }
}

