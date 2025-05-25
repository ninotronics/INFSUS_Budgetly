package INFSUS.controllerTest;

import INFSUS.controller.PrihodController;
import INFSUS.dto.request.PrihodRequestDTO;
import INFSUS.dto.response.PrihodResponseDTO;
import INFSUS.service.PrihodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PrihodControllerTest {
    private MockMvc mockMvc;
    private PrihodService prihodService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        prihodService = mock(PrihodService.class);
        PrihodController controller = new PrihodController(prihodService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreatePrihod() throws Exception {
        PrihodRequestDTO request = new PrihodRequestDTO();
        PrihodResponseDTO response = new PrihodResponseDTO();
        Mockito.when(prihodService.createPrihod(any())).thenReturn(response);

        mockMvc.perform(post("/prihodi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePrihod() throws Exception {
        PrihodRequestDTO request = new PrihodRequestDTO();
        PrihodResponseDTO response = new PrihodResponseDTO();
        Mockito.when(prihodService.updatePrihod(Mockito.eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/prihodi/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPrihodById() throws Exception {
        Mockito.when(prihodService.getPrihod(1L)).thenReturn(new PrihodResponseDTO());
        mockMvc.perform(get("/prihodi/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllPrihodi() throws Exception {
        Mockito.when(prihodService.getAllPrihodi()).thenReturn(List.of(new PrihodResponseDTO()));
        mockMvc.perform(get("/prihodi"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePrihod() throws Exception {
        doNothing().when(prihodService).deletePrihod(1L);
        mockMvc.perform(delete("/prihodi/1"))
                .andExpect(status().isOk());
    }
}

