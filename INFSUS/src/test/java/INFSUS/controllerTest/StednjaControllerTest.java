package INFSUS.controllerTest;
import INFSUS.controller.StednjaController;
import INFSUS.dto.request.StednjaRequestDTO;
import INFSUS.dto.response.StednjaResponseDTO;
import INFSUS.service.StednjaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StednjaControllerTest {

    private MockMvc mockMvc;
    private StednjaService stednjaService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        stednjaService = mock(StednjaService.class);
        StednjaController controller = new StednjaController(stednjaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    void testCreateStednja() throws Exception {
        StednjaRequestDTO request = new StednjaRequestDTO(
                "Putovanje", "Za Japan", LocalDate.now().plusMonths(6),
                BigDecimal.valueOf(3000), 1L
        );

        StednjaResponseDTO response = new StednjaResponseDTO(
                1L, "Putovanje", "Za Japan", LocalDate.now(), request.getDatumKraj(),
                request.getCiljniIznos(), BigDecimal.ZERO, 1L, List.of()
        );

        Mockito.when(stednjaService.createStednja(any())).thenReturn(response);

        mockMvc.perform(post("/stednja")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("Putovanje"))
                .andExpect(jsonPath("$.korisnik").value(1));
    }

    @Test
    void testUpdateStednja() throws Exception {
        Long id = 1L;
        StednjaRequestDTO request = new StednjaRequestDTO(
                "PutovanjeUpdate", "Za Japan Update", LocalDate.now().plusMonths(12),
                BigDecimal.valueOf(5000), 1L
        );
        StednjaResponseDTO response = new StednjaResponseDTO(
                id, "PutovanjeUpdate", "Za Japan Update", LocalDate.now(), request.getDatumKraj(),
                request.getCiljniIznos(), BigDecimal.ZERO, 1L, List.of()
        );
        Mockito.when(stednjaService.updateStednja(Mockito.eq(id), Mockito.any())).thenReturn(response);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/stednja/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("PutovanjeUpdate"));
    }

    @Test
    void testDeleteStednja() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(stednjaService).deleteStednja(id);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/stednja/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOneStednja() throws Exception {
        Long id = 1L;
        StednjaResponseDTO response = new StednjaResponseDTO(
                id, "Putovanje", "Za Japan", LocalDate.now(), LocalDate.now().plusMonths(6),
                BigDecimal.valueOf(3000), BigDecimal.ZERO, 1L, List.of()
        );
        Mockito.when(stednjaService.getStednja(id)).thenReturn(response);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/stednja/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("Putovanje"));
    }

    @Test
    void testGetAllStednje() throws Exception {
        StednjaResponseDTO response = new StednjaResponseDTO(
                1L, "Putovanje", "Za Japan", LocalDate.now(), LocalDate.now().plusMonths(6),
                BigDecimal.valueOf(3000), BigDecimal.ZERO, 1L, List.of()
        );
        Mockito.when(stednjaService.getAllStednje()).thenReturn(List.of(response));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/stednja"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].naziv").value("Putovanje"));
    }
}

