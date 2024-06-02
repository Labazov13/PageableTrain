package com.example.PageableTrain.services;

import com.example.PageableTrain.dto.BookDTO;
import com.example.PageableTrain.entities.Author;
import com.example.PageableTrain.entities.Book;
import com.example.PageableTrain.exceptions.AddingBookException;
import com.example.PageableTrain.exceptions.NotFoundBookException;
import com.example.PageableTrain.repositories.AuthorRepository;
import com.example.PageableTrain.repositories.BookRepository;
import com.example.PageableTrain.repositories.LibraryRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LibraryRepository libraryRepository;

    public LibraryService(BookRepository bookRepository, AuthorRepository authorRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.libraryRepository = libraryRepository;
    }

    @Transactional
    public Book createBook(BookDTO bookDTO) {
        List<Book> bookList = bookRepository.findAll();
        List<Author> authorList = authorRepository.findAll();
        if (checkBook(bookList, bookDTO.getName())) {
            throw new AddingBookException("A book with the same title is already in the library.");
        }
        if (checkAuthor(authorList, bookDTO.getAuthorName())) {
            Author author = authorRepository.findByName(bookDTO.getAuthorName());
            Book book = new Book(bookDTO.getName(), author);
            author.getBookList().add(book);
            authorRepository.save(author);
            return bookRepository.save(book);
        }
        Author author = new Author(bookDTO.getAuthorName());
        Book book = new Book(bookDTO.getName(), author);
        author.getBookList().add(book);
        authorRepository.save(author);
        return bookRepository.save(book);
    }

    public boolean checkBook(List<Book> bookList, String bookName) {
        return bookList.stream().anyMatch(book -> book.getName().equals(bookName));
    }

    public boolean checkAuthor(List<Author> authors, String name) {
        return authors.stream().anyMatch(author -> author.getName().equals(name));
    }

    public Page<Book> getLibrary(int page, int size, String sortField, String orderField) {
        Sort sort = null;
        Pageable pageable = null;
        if (orderField.equals("DESC")) {
            sort = Sort.by(sortField).descending();
            pageable = PageRequest.of(page, size, sort);
            return libraryRepository.findAll(pageable);
        }
        sort = Sort.by(sortField).ascending();
        pageable = PageRequest.of(page, size, sort);
        return libraryRepository.findAll(pageable);
    }

    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookException("Cannot find book with ID: " + bookId));
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Book editBook(Long bookId, String bookName) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookException("Cannot find book with ID: " + bookId));
        book.setName(bookName);
        return bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookException("Cannot find book with ID: " + bookId));
        bookRepository.delete(book);
    }
}



