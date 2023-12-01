package com.upao.Backend23.repository;

import com.upao.Backend23.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
