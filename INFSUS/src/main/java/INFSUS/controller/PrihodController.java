package INFSUS.controller;

import INFSUS.dto.request.PrihodRequestDTO;
import INFSUS.dto.response.PrihodResponseDTO;

import INFSUS.service.PrihodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prihodi")
@RequiredArgsConstructor
public class PrihodController {

    private final PrihodService service;

    @PostMapping
    public PrihodResponseDTO createPrihod(@RequestBody PrihodRequestDTO prihod) {
        return service.createPrihod(prihod);
    }

    @PutMapping("/{id}")
    public PrihodResponseDTO updatePrihod(@PathVariable Long id, @RequestBody PrihodRequestDTO prihod) {
        return service.updatePrihod(id, prihod);
    }

    @GetMapping("/{id}")
    public PrihodResponseDTO getPrihod(@PathVariable Long id) {
        return service.getPrihod(id);
    }

    @GetMapping
    public List<PrihodResponseDTO> getAllPrihodi() {
        return service.getAllPrihodi();
    }

    @DeleteMapping("/{id}")
    public void deletePrihod(@PathVariable Long id) {
        service.deletePrihod(id);
    }
}