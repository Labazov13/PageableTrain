package com.example.PageableTrain.repositories;

import com.example.PageableTrain.entities.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends PagingAndSortingRepository<Book, Long> {

}
