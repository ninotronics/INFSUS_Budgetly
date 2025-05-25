package INFSUS.controllerTest;

import INFSUS.controller.TrosakController;
import INFSUS.dto.request.TrosakRequestDTO;
import INFSUS.dto.response.TrosakResponseDTO;
import INFSUS.service.TrosakService;
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
public class TrosakControllerTest {
    private MockMvc mockMvc;
    private TrosakService trosakService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        trosakService = mock(TrosakService.class);
        TrosakController controller = new TrosakController(trosakService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateTrosak() throws Exception {
        TrosakRequestDTO request = new TrosakRequestDTO();
        TrosakResponseDTO response = new TrosakResponseDTO();
        Mockito.when(trosakService.createTrosak(any())).thenReturn(response);

        mockMvc.perform(post("/troskovi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTrosak() throws Exception {
        TrosakRequestDTO request = new TrosakRequestDTO();
        TrosakResponseDTO response = new TrosakResponseDTO();
        Mockito.when(trosakService.updateTrosak(Mockito.eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/troskovi/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTrosakById() throws Exception {
        Mockito.when(trosakService.getTrosak(1L)).thenReturn(new TrosakResponseDTO());
        mockMvc.perform(get("/troskovi/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTroskovi() throws Exception {
        Mockito.when(trosakService.getAllTroskovi()).thenReturn(List.of(new TrosakResponseDTO()));
        mockMvc.perform(get("/troskovi"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTrosak() throws Exception {
        doNothing().when(trosakService).deleteTrosak(1L);
        mockMvc.perform(delete("/troskovi/1"))
                .andExpect(status().isOk());
    }
}

