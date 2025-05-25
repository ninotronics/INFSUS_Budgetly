package INFSUS.controllerTest;

import INFSUS.controller.PodsjetnikController;
import INFSUS.dto.request.PodsjetnikRequestDTO;
import INFSUS.dto.response.PodsjetnikResponseDTO;
import INFSUS.service.PodsjetnikService;
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
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PodsjetnikControllerTest {
    private MockMvc mockMvc;
    private PodsjetnikService podsjetnikService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        podsjetnikService = mock(PodsjetnikService.class);
        PodsjetnikController controller = new PodsjetnikController(podsjetnikService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreatePodsjetnik() throws Exception {
        PodsjetnikRequestDTO request = new PodsjetnikRequestDTO();
        PodsjetnikResponseDTO response = new PodsjetnikResponseDTO();
        Mockito.when(podsjetnikService.createPodsjetnik(any())).thenReturn(response);

        mockMvc.perform(post("/podsjetnici")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllPodsjetnici() throws Exception {
        Mockito.when(podsjetnikService.getAll()).thenReturn(List.of(new PodsjetnikResponseDTO()));
        mockMvc.perform(get("/podsjetnici"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPodsjetnikById() throws Exception {
        Mockito.when(podsjetnikService.getById(1L)).thenReturn(new PodsjetnikResponseDTO());
        mockMvc.perform(get("/podsjetnici/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePodsjetnikById() throws Exception {
        doNothing().when(podsjetnikService).deleteById(1L);
        mockMvc.perform(delete("/podsjetnici/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdatePodsjetnik() throws Exception {
        Long id = 1L;
        PodsjetnikRequestDTO request = new PodsjetnikRequestDTO();
        PodsjetnikResponseDTO response = new PodsjetnikResponseDTO();
        Mockito.when(podsjetnikService.update(eq(id), any())).thenReturn(response);

        mockMvc.perform(put("/podsjetnici/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
