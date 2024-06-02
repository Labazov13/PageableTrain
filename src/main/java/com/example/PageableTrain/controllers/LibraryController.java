package com.example.PageableTrain.controllers;

import com.example.PageableTrain.dto.BookDTO;
import com.example.PageableTrain.entities.Book;
import com.example.PageableTrain.services.LibraryService;
import com.example.PageableTrain.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortField,
                                                  @RequestParam(defaultValue = "ASC") String sortOrder) {
        return ResponseEntity.ok(libraryService.getLibrary(page, size, sortField, sortOrder));
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(@PathVariable(name = "bookId") Long bookId) {
        return ResponseEntity.ok(libraryService.getBook(bookId));
    }

    @Validated
    @PostMapping("/new")
    @JsonView(Views.AuthorSummary.class)
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(libraryService.createBook(bookDTO));
    }

        @PutMapping(value = "/edit/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Book> editBook(@PathVariable(name = "bookId") Long bookId,
                                             @RequestBody @NotBlank(message = "Book name cannot be empty") String bookName) {
            return ResponseEntity.ok(libraryService.editBook(bookId, bookName));
        }

    @DeleteMapping(value = "/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable(name = "bookId") Long bookId) {
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok("Success!");
    }

}
