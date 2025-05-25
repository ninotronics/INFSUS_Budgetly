package INFSUS.controller;

import INFSUS.dto.request.PodsjetnikRequestDTO;
import INFSUS.dto.response.PodsjetnikResponseDTO;
import INFSUS.service.PodsjetnikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/podsjetnici")
@RequiredArgsConstructor
public class PodsjetnikController {

    private final PodsjetnikService podsjetnikService;

    @PostMapping
    public ResponseEntity<PodsjetnikResponseDTO> create(@RequestBody PodsjetnikRequestDTO requestDTO) {
        return ResponseEntity.ok(podsjetnikService.createPodsjetnik(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodsjetnikResponseDTO> update(@PathVariable Long id, @RequestBody PodsjetnikRequestDTO requestDTO) {
        return ResponseEntity.ok(podsjetnikService.update(id, requestDTO));
    }


    @GetMapping
    public ResponseEntity<List<PodsjetnikResponseDTO>> getAll() {
        return ResponseEntity.ok(podsjetnikService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodsjetnikResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(podsjetnikService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        podsjetnikService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
