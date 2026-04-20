package com.rizz.learn.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @GetMapping
  public String getAllProducts(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    return "Get all products | Page: %d | Size: %d".formatted(page, size);
  }

  @GetMapping("/{id}")
  public String getProductById(@PathVariable long id) {
    return "Get product with ID: %d".formatted(id);
  }

  @PostMapping
  public String createProduct() {
    return "Product created!";
  }

  @PatchMapping("/{id}")
  public String updateProduct(@PathVariable long id) {
    return "Product with ID: %d updated!".formatted(id);
  }

  @DeleteMapping("/{id}")
  public String deleteProduct(@PathVariable long id) {
    return "Product with ID: %d deleted!".formatted(id);
  }

}
