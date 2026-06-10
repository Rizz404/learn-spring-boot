package com.rizz.learn.app.controller;

import com.rizz.learn.app.dto.CategoryResponse;
import com.rizz.learn.app.entity.Category;
import com.rizz.learn.app.repository.CategoryRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  public List<CategoryResponse> getAll() {
    return categoryRepository.findAll().stream()
        .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getDescription()))
        .toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryResponse create(@RequestBody Category category) {
    Category saved = categoryRepository.save(category);
    return new CategoryResponse(saved.getId(), saved.getName(), saved.getDescription());
  }
}
