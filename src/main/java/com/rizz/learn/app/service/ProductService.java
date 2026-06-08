package com.rizz.learn.app.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rizz.learn.app.dto.CategoryResponse;
import com.rizz.learn.app.dto.ProductRequest;
import com.rizz.learn.app.dto.ProductResponse;
import com.rizz.learn.app.entity.Category;
import com.rizz.learn.app.entity.Product;
import com.rizz.learn.app.repository.CategoryRepository;
import com.rizz.learn.app.repository.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  // * Inject entity yang berhubungan di service
  private final CategoryRepository categoryRepository;

  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  private CategoryResponse toCategoryResponse(Category category) {
    return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
  }

  // * Mapping helper entity to response
  private ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        toCategoryResponse(product.getCategory()),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }

  // * Find all dengan paginasi
  @Transactional(readOnly = true)
  @Cacheable(value = "products", key = "#page + '-' + #size")
  public Page<ProductResponse> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    return productRepository.findAll(pageable).map(this::toResponse);
  }

  @Transactional(readOnly = true)
  private Category findCategoryById(Long categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NoSuchElementException("Category with ID " + categoryId + " not found"));
  }

  // * Find by id
  @Transactional(readOnly = true)
  @Cacheable(value = "product", key = "#id")
  public ProductResponse findById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with ID: %d not found".formatted(id)));
    return toResponse(product);
  }

  // * Create
  @Transactional
  public ProductResponse create(ProductRequest productRequest) {
    Category category = findCategoryById(productRequest.categoryId());
    Product product = new Product(
        productRequest.name(),
        productRequest.description(),
        productRequest.price(),
        category);
    return toResponse(productRepository.save(product));
  }

  // * Update
  @Transactional
  @CachePut(value = "product", key = "#id")
  public ProductResponse update(Long id, ProductRequest productRequest) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with ID: %d not found".formatted(id)));
    Category category = findCategoryById(productRequest.categoryId());

    product.setName(productRequest.name());
    product.setDescription(productRequest.description());
    product.setPrice(productRequest.price());
    product.setCategory(category);

    return toResponse(productRepository.save(product));
  }

  // * Delete
  @Transactional
  @CacheEvict(value = { "products", "product" }, allEntries = true)
  public void delete(Long id) {
    if (!productRepository.existsById(id)) {
      throw new NoSuchElementException("Product with ID: %d not found".formatted(id));
    }
    productRepository.deleteById(id);
  }

}

// * Sesi 2.2
// @Service
// public class ProductService {

// // * Simulasi database sementara (akan diganti di Modul 3)
// private static Long idCounter = 1L;

// public List<ProductResponse> findAll() {
// return List.of(
// new ProductResponse(1L, "iPhone 15 Pro", "Apple flagship phone", 19999.99,
// "Electronics"),
// new ProductResponse(2L, "MacBook Air M3", "Apple laptop", 24999.99,
// "Electronics"),
// new ProductResponse(3L, "Nike Air Max", "Running shoes", 1999.99,
// "Fashion"));
// }

// public ProductResponse findById(Long id) {
// // * Simulasi find by id (nanti diganti query DB)
// return new ProductResponse(id, "Sample Product", "Sample description",
// 9999.99, "General");
// }

// public ProductResponse create(ProductRequest request) {
// Long newId = idCounter++;
// // * Mapping dari Request DTO ke Response DTO (manual mapper)
// return new ProductResponse(
// newId,
// request.name(),
// request.description(),
// request.price(),
// request.category());
// }

// public ProductResponse update(Long id, ProductRequest request) {
// // * Simulasi find by id (nanti diganti query DB)
// return new ProductResponse(
// id,
// request.name(),
// request.description(),
// request.price(),
// request.category());
// }

// public void delete(Long id) {
// System.out.println("Product with ID: %d deleted.".formatted(id));
// }

// }
