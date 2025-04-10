package io.github.artemnefedov.lib.controller;

import io.github.artemnefedov.lib.service.BookDto;
import io.github.artemnefedov.lib.service.BookService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class LibRestController {

    private final BookService bookService;

    public LibRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.addBook(bookDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
