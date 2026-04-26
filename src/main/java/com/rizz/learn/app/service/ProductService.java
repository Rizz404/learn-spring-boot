package com.rizz.learn.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rizz.learn.app.dto.ProductRequest;
import com.rizz.learn.app.dto.ProductResponse;

@Service
public class ProductService {

  // * Simulasi database sementara (akan diganti di Modul 3)
  private static Long idCounter = 1L;

  public List<ProductResponse> findAll() {
    return List.of(
        new ProductResponse(1L, "iPhone 15 Pro", "Apple flagship phone", 19999.99, "Electronics"),
        new ProductResponse(2L, "MacBook Air M3", "Apple laptop", 24999.99, "Electronics"),
        new ProductResponse(3L, "Nike Air Max", "Running shoes", 1999.99, "Fashion"));
  }

  public ProductResponse findById(Long id) {
    // * Simulasi find by id (nanti diganti query DB)
    return new ProductResponse(id, "Sample Product", "Sample description", 9999.99, "General");
  }

  public ProductResponse create(ProductRequest request) {
    Long newId = idCounter++;
    // * Mapping dari Request DTO ke Response DTO (manual mapper)
    return new ProductResponse(
        newId,
        request.name(),
        request.description(),
        request.price(),
        request.category());
  }

  public ProductResponse update(Long id, ProductRequest request) {
    // * Simulasi find by id (nanti diganti query DB)
    return new ProductResponse(
        id,
        request.name(),
        request.description(),
        request.price(),
        request.category());
  }

  public void delete(Long id) {
    System.out.println("Product with ID: %d deleted.".formatted(id));
  }

}
