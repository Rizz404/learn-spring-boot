package com.rizz.learn.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizz.learn.app.dto.ProductRequest;
import com.rizz.learn.app.dto.ProductResponse;
import com.rizz.learn.app.service.ProductService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<ProductResponse> getAllProducts(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public ProductResponse getProductById(@PathVariable Long id) {
    return productService.findById(id);
  }

  // * Jangan lupa annotationnya ya
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse createProduct(@RequestBody ProductRequest request) {
    return productService.create(request);
  }

  @PatchMapping("/{id}")
  public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
    return productService.update(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT) // * Return status code 204
  public void deleteProduct(@PathVariable Long id) {
    productService.delete(id);
  }

}

// * Sesi 2.1
/*
 * @RestController
 *
 * @RequestMapping("/api/products")
 * public class ProductController {
 *
 * @GetMapping
 * public String getAllProducts(@RequestParam(defaultValue = "1") int page,
 *
 * @RequestParam(defaultValue = "10") int size) {
 * return "Get all products | Page: %d | Size: %d".formatted(page, size);
 * }
 *
 * @GetMapping("/{id}")
 * public String getProductById(@PathVariable long id) {
 * return "Get product with ID: %d".formatted(id);
 * }
 *
 * @PostMapping
 * public String createProduct() {
 * return "Product created!";
 * }
 *
 * @PatchMapping("/{id}")
 * public String updateProduct(@PathVariable long id) {
 * return "Product with ID: %d updated!".formatted(id);
 * }
 *
 * @DeleteMapping("/{id}")
 * public String deleteProduct(@PathVariable long id) {
 * return "Product with ID: %d deleted!".formatted(id);
 * }
 *
 * }
 */
