package INFSUS.controller;

import INFSUS.dto.request.TrosakRequestDTO;
import INFSUS.dto.response.TrosakResponseDTO;
import INFSUS.model.Trosak;
import INFSUS.service.TrosakService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/troskovi")
@RequiredArgsConstructor
public class TrosakController {

    private final TrosakService service;

    @PostMapping
    public TrosakResponseDTO createTrosak(@RequestBody TrosakRequestDTO trosak) {
        return service.createTrosak(trosak);
    }

    @PutMapping("/{id}")
    public TrosakResponseDTO updateTrosak(@PathVariable Long id, @RequestBody TrosakRequestDTO trosak) {
        return service.updateTrosak(id, trosak);
    }

    @GetMapping("/{id}")
    public TrosakResponseDTO getTrosak(@PathVariable Long id) {
        return service.getTrosak(id);
    }

    @GetMapping
    public List<TrosakResponseDTO> getAllTroskovi() {
        return service.getAllTroskovi();
    }

    @DeleteMapping("/{id}")
    public void deleteTrosak(@PathVariable Long id) {
        service.deleteTrosak(id);
    }
}