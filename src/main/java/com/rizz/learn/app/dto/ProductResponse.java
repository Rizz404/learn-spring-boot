package com.rizz.learn.app.dto;

import java.time.LocalDateTime;

public record ProductResponse(
                Long id,
                String name,
                String description,
                Double price,
                String category,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
