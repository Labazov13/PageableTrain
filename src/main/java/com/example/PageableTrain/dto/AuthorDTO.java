package com.example.PageableTrain.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDTO(@NotBlank(message = "Name cannot be Empty") String name) {
}
