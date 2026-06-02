package com.rizz.learn.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rizz.learn.app.dto.ProductRequest;
import com.rizz.learn.app.dto.ProductResponse;
import com.rizz.learn.app.entity.Category;
import com.rizz.learn.app.entity.Product;
import com.rizz.learn.app.repository.CategoryRepository;
import com.rizz.learn.app.repository.ProductRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  // * Buat mock harus pakai annotaion mock
  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  // ! yang di moc repository dan logika jangan di mock tapi di injectmoc buat
  // ! ngetes yang di moc itu
  @InjectMocks
  private ProductService productService;

  // * Buat dummy
  private Category dummyCategory() {
    return new Category("Electronics", "Gadet and devices");
  }

  private Product dummyProduct(Category category) {
    return new Product("iPhone 17 Pro", "Apple flagship", 19999.99, category);
  }

  @Test
  void findById_whenProductExists_shouldReturnProductResponse() {
    // * Arrange
    Category category = dummyCategory();
    Product product = dummyProduct(category);
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    // * Act
    ProductResponse result = productService.findById(1L);

    assertThat(result).isNotNull();

    // * Assert
    assertThat(result.name()).isEqualTo("iPhone 17 Pro");
    assertThat(result.price()).isEqualTo(19999.99);
    assertThat(result.category().name()).isEqualTo("Electronics");
    verify(productRepository).findById(1L);

  }

  @Test
  void findById_whenProductNotFound_shouldThrowNoSuchElementException() {
    // * Arrange
    when(productRepository.findById(99L)).thenReturn(Optional.empty());

    // * Act & assert
    assertThatThrownBy(() -> productService.findById(99L))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("99");
  }

  @Test
  void create_whenCategoryExists_shouldSaveAndReturnProductResponse() {
    // Arrange
    Category category = dummyCategory();
    Product savedProduct = dummyProduct(category);
    ProductRequest request = new ProductRequest("iPhone 17 Pro", "Apple flagship", 19999.99, 1L);

    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

    // Act
    ProductResponse result = productService.create(request);

    assertThat(result).isNotNull();
    // Assert
    assertThat(result.name()).isEqualTo("iPhone 17 Pro");
    verify(categoryRepository).findById(1L);
    verify(productRepository).save(any(Product.class));
  }

  @Test
  void create_whenCategoryNotFound_shouldThrowNoSuchElementException() {
    // Arrange
    ProductRequest request = new ProductRequest("iPhone 17 Pro", "Apple flagship", 19999.99, 99L);
    when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThatThrownBy(() -> productService.create(request))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("99");
  }

  @Test
  void delete_whenProductExists_shouldCallDeleteById() {
    when(productRepository.existsById(1L)).thenReturn(true);

    productService.delete(1L);

    verify(productRepository).deleteById(1L);
  }

  @Test
  void delete_whenProductNotFound_shouldThrowNoSuchElementException() {
    when(productRepository.existsById(99L)).thenReturn(false);

    assertThatThrownBy(() -> productService.delete(99L))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("99");
  }
}
