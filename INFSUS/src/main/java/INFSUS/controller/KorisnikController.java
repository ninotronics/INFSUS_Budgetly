package INFSUS.controller;

import INFSUS.dto.request.KorisnikPromjenaValuteDTO;
import INFSUS.service.KorisnikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/korisnici")
@RequiredArgsConstructor
public class KorisnikController {
    private final KorisnikService korisnikService;

    @PutMapping("/{id}/valuta")
    public ResponseEntity<Void> promijeniValutu(@PathVariable Long id, @RequestBody KorisnikPromjenaValuteDTO dto) {
        korisnikService.promijeniValutu(id, dto);
        return ResponseEntity.ok().build();
    }
}

