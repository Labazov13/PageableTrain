package com.example.PageableTrain.entities;

import com.example.PageableTrain.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.AuthorSummary.class)
    private Long id;

    @JsonView(Views.AuthorSummary.class)
    private String name;

    @ManyToOne
    @JsonView(Views.AuthorSummary.class)
    private Author author;

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
    }
}
