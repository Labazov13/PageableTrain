package com.example.PageableTrain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO{
    @NotBlank(message = "Name cannot be empty")
    String name;
    @NotBlank(message = "Author name cannot be empty")
    String authorName;

    public BookDTO(String name, String authorName) {
        this.name = name;
        this.authorName = authorName;
    }

    public BookDTO(String name) {
        this.name = name;
    }
}

