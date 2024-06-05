package com.localshop.repositories;

import com.localshop.models.Carrello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrelloRepository extends JpaRepository<Carrello,Long> {
}
