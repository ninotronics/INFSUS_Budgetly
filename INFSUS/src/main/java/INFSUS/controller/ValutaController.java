package INFSUS.controller;

import INFSUS.dto.response.ValutaResponseDTO;
import INFSUS.service.ValutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valute")
@RequiredArgsConstructor
public class ValutaController {

    private final ValutaService valutaService;

    @GetMapping
    public ResponseEntity<List<ValutaResponseDTO>> getAll() {
        return ResponseEntity.ok(valutaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValutaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(valutaService.getById(id));
    }
}