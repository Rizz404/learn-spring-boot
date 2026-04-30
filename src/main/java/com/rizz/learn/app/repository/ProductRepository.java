package com.rizz.learn.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rizz.learn.app.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  // * Auto-generated query: WHERE LOWER(name) LIKE LOWER('%keyword%')
  Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

  // * Auto-generated query: WHERE category = ?
  Page<Product> findByCategory(String category, Pageable pageable);
}
