package com.rizz.learn.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// * Pake jakarta untuk validation
// ! NotBlank buat string dan NotNull buat number, salah bisa fatal
public record ProductRequest(
        @NotBlank(message = "Product name can not be empty") @Size(min = 3, max = 100, message = "Product name must between 3 and 100 characters") String name,

        @NotBlank(message = "Description can not be empty") String description,

        @Positive(message = "Price must be higher than 0") Double price,

        @NotNull(message = "Category ID cannot be null") Long categoryId) {
}
