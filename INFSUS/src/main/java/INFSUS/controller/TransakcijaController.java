package INFSUS.controller;

import INFSUS.dto.response.TransakcijaResponseDTO;
import INFSUS.service.TransakcijaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transakcije")
@RequiredArgsConstructor
public class TransakcijaController {

    private final TransakcijaService service;

    @GetMapping
    public List<TransakcijaResponseDTO> getAllTransakcije() {
        return service.getAllTransakcije();
    }
}