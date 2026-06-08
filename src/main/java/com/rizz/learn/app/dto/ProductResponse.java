package com.rizz.learn.app.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        CategoryResponse category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) implements Serializable {
}
