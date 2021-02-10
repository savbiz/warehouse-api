package com.savbiz.ikea.warehouse.repository;

import com.savbiz.ikea.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAll();

  void deleteById(Long id);
}
