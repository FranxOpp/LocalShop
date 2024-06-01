package com.localshop.repositories;

import com.localshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List <Product> findByNameContaining(String name);
    List <Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
