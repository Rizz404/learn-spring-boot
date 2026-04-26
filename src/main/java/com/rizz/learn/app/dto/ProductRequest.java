package com.rizz.learn.app.dto;

public record ProductRequest(
    String name,
    String description,
    Double price,
    String category) {
}
