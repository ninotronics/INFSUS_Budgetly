package INFSUS.controllerTest;

import INFSUS.controller.TransakcijaController;
import INFSUS.dto.response.TransakcijaResponseDTO;
import INFSUS.service.TransakcijaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransakcijaControllerTest {
    private MockMvc mockMvc;
    private TransakcijaService transakcijaService;

    @BeforeEach
    void setUp() {
        transakcijaService = mock(TransakcijaService.class);
        TransakcijaController controller = new TransakcijaController(transakcijaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllTransakcije() throws Exception {
        Mockito.when(transakcijaService.getAllTransakcije()).thenReturn(List.of(new TransakcijaResponseDTO()));
        mockMvc.perform(get("/transakcije"))
                .andExpect(status().isOk());
    }
}

