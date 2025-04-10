package io.github.artemnefedov.lib.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto addBook(BookDto bookDto);

    BookDto updateBook(long id, BookDto bookDto);

    void deleteBook(long id);

    List<BookDto> getBooks();

    Page<BookDto> findBooks(String title, String brand, Integer year, Pageable pageable);

    BookDto findById(Long id);
}
