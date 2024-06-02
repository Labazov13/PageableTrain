package com.example.PageableTrain.controllers;

import com.example.PageableTrain.dto.BookDTO;
import com.example.PageableTrain.entities.Author;
import com.example.PageableTrain.entities.Book;
import com.example.PageableTrain.services.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryService libraryService;

    @Test
    void getAllBooks_ReturnedListBooksWithStatus_OK() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", new Author(1L, "Peter")));
        books.add(new Book(2L, "Book 2", new Author(1L, "Peter")));
        books.add(new Book(3L, "Book 3", new Author(1L, "Peter")));
        Page<Book> page = new PageImpl<>(books);
       // when(libraryService.getLibrary(anyInt(), anyInt(), anyString(), anyString())).thenReturn(page);
        mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[2].name", is("Book 3")));
    }

    @Test
    void getBookWithCorrectId_ReturnedBookWithStatus_OK() throws Exception {
        Book book = new Book(1L, "Peter Pan", new Author(1L, "James Barry"));
        when(libraryService.getBook(anyLong())).thenReturn(book);
        mockMvc.perform(get("/api/books/{bookId}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Peter Pan")));
    }

    @Test
    void createBook() throws Exception {
        BookDTO bookDTO = new BookDTO("Peter Pan", "James Barry");
        Book createdBook = new Book(1L, "Peter Pen", new Author(1L, "James Barry", new ArrayList<>()));

        when(libraryService.createBook(bookDTO)).
                thenReturn(createdBook);
        String jsonRequest = new ObjectMapper().writeValueAsString(bookDTO);
        mockMvc.perform(post("/api/books/new").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Peter Pen")));
    }

    @Test
    void editBook() throws Exception {
        Long searchBookId = 1L;
        String newBookName = "When a person is alone";
        Book editedBook = new Book(1L, newBookName,
                new Author(1L, "James Barry", new ArrayList<>()));
        when(libraryService.editBook(searchBookId, newBookName)).thenReturn(editedBook);
        String jsonRequest = new ObjectMapper().writeValueAsString(newBookName);
        mockMvc.perform(put("/api/books/edit/{bookId}", searchBookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        Long bookId = 1L;
        doNothing().when(libraryService).deleteBook(bookId);
        mockMvc.perform(delete("/api/books/delete/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string("Success!"));
    }
}