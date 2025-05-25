package INFSUS.controllerTest;

import INFSUS.controller.ValutaController;
import INFSUS.dto.response.ValutaResponseDTO;
import INFSUS.service.ValutaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValutaControllerTest {
    private MockMvc mockMvc;
    private ValutaService valutaService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        valutaService = mock(ValutaService.class);
        ValutaController controller = new ValutaController(valutaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllValute() throws Exception {
        Mockito.when(valutaService.getAll()).thenReturn(List.of(new ValutaResponseDTO()));
        mockMvc.perform(get("/valute"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetValutaById() throws Exception {
        Mockito.when(valutaService.getById(anyLong())).thenReturn(new ValutaResponseDTO());
        mockMvc.perform(get("/valute/1"))
                .andExpect(status().isOk());
    }
}

