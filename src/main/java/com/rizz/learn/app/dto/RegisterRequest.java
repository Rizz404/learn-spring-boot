package com.rizz.learn.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @Email(message = "Format email tidak valid") @NotBlank(message = "Email tidak boleh kosong")
        String email,
    @NotBlank(message = "Password tidak boleh kosong")
        @Size(min = 8, message = "Password minimal 8 karakter")
        String password,
    @NotBlank(message = "Nama tidak boleh kosong") String name) {}
