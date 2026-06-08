package com.rizz.learn.app.dto;

import java.io.Serializable;

public record CategoryResponse(
        Long Id,
        String name,
        String description) implements Serializable {
}
