package INFSUS.service.implementation;

import INFSUS.dto.request.KorisnikPromjenaValuteDTO;
import INFSUS.model.Korisnik;
import INFSUS.model.Valuta;
import INFSUS.model.Prihod;
import INFSUS.model.Trosak;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.ValutaRepo;
import INFSUS.repository.PrihodRepo;
import INFSUS.repository.TrosakRepo;
import INFSUS.service.KorisnikService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class KorisnikServiceImpl implements KorisnikService {
    private final KorisnikRepo korisnikRepo;
    private final ValutaRepo valutaRepo;
    private final PrihodRepo prihodRepo;
    private final TrosakRepo trosakRepo;

    @Override
    @Transactional
    public void promijeniValutu(Long korisnikId, KorisnikPromjenaValuteDTO dto) {
        Korisnik korisnik = korisnikRepo.findById(korisnikId)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronađen"));
        Valuta staraValuta = korisnik.getValuta();
        Valuta novaValuta = valutaRepo.findById(dto.getValutaId())
                .orElseThrow(() -> new EntityNotFoundException("Valuta nije pronađena"));
        if (staraValuta.getId().equals(novaValuta.getId())) return;
        BigDecimal tecajStara = staraValuta.getTecajPremaBazi();
        BigDecimal tecajNova = novaValuta.getTecajPremaBazi();
        BigDecimal faktor = tecajNova.divide(tecajStara, 6, RoundingMode.HALF_UP);
        korisnik.setValuta(novaValuta);
        korisnik.setUkupniIznos(korisnik.getUkupniIznos().multiply(faktor));
        korisnik.setOsnovniIznos(korisnik.getOsnovniIznos().multiply(faktor));
        korisnikRepo.save(korisnik);
        List<Prihod> prihodi = prihodRepo.findByKorisnikId(korisnikId);
        for (Prihod prihod : prihodi) {
            prihod.setIznos(prihod.getIznos().multiply(faktor));
            prihodRepo.save(prihod);
        }
        List<Trosak> troskovi = trosakRepo.findByKorisnikId(korisnikId);
        for (Trosak trosak : troskovi) {
            trosak.setIznos(trosak.getIznos().multiply(faktor));
            trosakRepo.save(trosak);
        }
    }
}

