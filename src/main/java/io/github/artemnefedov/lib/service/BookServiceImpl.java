package io.github.artemnefedov.lib.service;

import io.github.artemnefedov.lib.repository.Book;
import io.github.artemnefedov.lib.repository.BookRepository;
import io.github.artemnefedov.lib.repository.spec.BookSpec;
import io.github.artemnefedov.lib.repository.spec.SearchCriteria;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        return bookRepository.save(bookDto.toEntity()).toDto();
    }

    @Override
    public BookDto updateBook(long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setVendorCode(bookDto.vendorCode());
                    book.setTitle(bookDto.title());
                    book.setYear(bookDto.year());
                    book.setBrand(bookDto.brand());
                    book.setStock(bookDto.stock());
                    book.setPrice(BigDecimal.valueOf(bookDto.price()));
                    return bookRepository.save(book).toDto();
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getVendorCode(),
                        book.getTitle(),
                        book.getYear(),
                        book.getBrand(),
                        book.getStock(),
                        book.getPrice().doubleValue()
                ))
                .toList();
    }

    @Override
    public Page<BookDto> findBooks(String title, String brand, Integer year, Pageable pageable) {
        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            criteriaList.add(new SearchCriteria("title", title, "like"));
        }
        if (brand != null && !brand.isBlank()) {
            criteriaList.add(new SearchCriteria("brand", brand, "like"));
        }
        if (year != null) {
            criteriaList.add(new SearchCriteria("year", year, "="));
        }
        BookSpec spec = new BookSpec(criteriaList);
        return bookRepository.findAll(spec, pageable).map(Book::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(book -> new BookDto(
                        book.getId(),
                        book.getVendorCode(),
                        book.getTitle(),
                        book.getYear(),
                        book.getBrand(),
                        book.getStock(),
                        book.getPrice().doubleValue()
                ))
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
