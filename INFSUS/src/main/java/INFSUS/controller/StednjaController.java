package INFSUS.controller;

import INFSUS.dto.request.StednjaRequestDTO;
import INFSUS.dto.response.StednjaResponseDTO;
import INFSUS.service.StednjaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stednja")
@RequiredArgsConstructor
public class StednjaController {

    private final StednjaService service;

    @PostMapping
    public StednjaResponseDTO create(@RequestBody StednjaRequestDTO request) {
        return service.createStednja(request);
    }

    @PutMapping("/{id}")
    public StednjaResponseDTO update(@PathVariable Long id, @RequestBody StednjaRequestDTO request) {
        return service.updateStednja(id, request);
    }

    @GetMapping("/{id}")
    public StednjaResponseDTO getOne(@PathVariable Long id) {
        return service.getStednja(id);
    }

    @GetMapping
    public List<StednjaResponseDTO> getAll() {
        return service.getAllStednje();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteStednja(id);
    }
}
