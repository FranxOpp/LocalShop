package com.localshop.repositories;

import com.localshop.models.Negozio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegozioRepository extends JpaRepository<Negozio, String>{
}