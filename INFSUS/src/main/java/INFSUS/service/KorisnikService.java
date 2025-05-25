package INFSUS.service;

import INFSUS.dto.request.KorisnikPromjenaValuteDTO;

public interface KorisnikService {
    void promijeniValutu(Long korisnikId, KorisnikPromjenaValuteDTO dto);
}

