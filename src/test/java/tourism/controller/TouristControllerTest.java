package tourism.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tourism.service.TouristService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        mockMvc.perform(get("/attractions")).
                andExpect(status().isOk()).
                andExpect(view().name("attractionList"));
    }

    @Test
    void findAttractionByName() {
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