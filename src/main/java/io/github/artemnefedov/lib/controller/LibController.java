package io.github.artemnefedov.lib.controller;

import io.github.artemnefedov.lib.service.BookDto;
import io.github.artemnefedov.lib.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibController {

    private final BookService bookService;

    public LibController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDto> booksPage = bookService.findBooks(title, brand, year, pageable);

        model.addAttribute("booksPage", booksPage);
        model.addAttribute("title", title);
        model.addAttribute("brand", brand);
        model.addAttribute("year", year);
        return "index";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDto(
                0,
                null,
                null,
                0,
                null,
                0,
                0.0
        ));
        return "add";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookDto book = bookService.findById(id); // выбрасывает исключение, если не найдено
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") BookDto updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "redirect:/";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book") BookDto dto) {
        bookService.addBook(dto);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
