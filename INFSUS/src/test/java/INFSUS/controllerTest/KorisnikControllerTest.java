package INFSUS.controllerTest;

import INFSUS.controller.KorisnikController;
import INFSUS.dto.request.KorisnikPromjenaValuteDTO;
import INFSUS.service.KorisnikService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // ✅ OVO je ispravno
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KorisnikControllerTest {
    private MockMvc mockMvc;
    private KorisnikService korisnikService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        korisnikService = Mockito.mock(KorisnikService.class);
        KorisnikController controller = new KorisnikController(korisnikService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    void testPromijeniValutu() throws Exception {
        KorisnikPromjenaValuteDTO dto = new KorisnikPromjenaValuteDTO();
        dto.setValutaId(2L);
        mockMvc.perform(put("/korisnici/1/valuta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        verify(korisnikService).promijeniValutu(eq(1L), any(KorisnikPromjenaValuteDTO.class));
    }
}