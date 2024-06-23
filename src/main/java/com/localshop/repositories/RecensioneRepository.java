package com.localshop.repositories;

import com.localshop.models.Recensione;
import com.localshop.models.Negozio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    List<Recensione> findByNegozio(Negozio negozio);
}
