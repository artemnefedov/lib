package io.github.artemnefedov.lib.config;

import io.github.artemnefedov.lib.repository.Book;
import io.github.artemnefedov.lib.repository.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final Random random = new Random();

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() > 0) return;

        List<String> brands = List.of("O'Reilly", "Penguin", "Manning", "Apress", "Springer");
        List<String> titles = List.of(
                "Java in Action", "Spring Boot Up & Running", "Effective Java", "Clean Code",
                "Refactoring", "Microservices Patterns", "Kubernetes in Action", "Hibernate Tips",
                "Design Patterns", "Algorithms", "Computer Networks", "Operating Systems",
                "Cloud Native Java", "Distributed Systems", "The Pragmatic Programmer",
                "Software Architecture", "Domain-Driven Design", "Clean Architecture",
                "Building Microservices", "High Performance Java"
        );

        for (int i = 0; i < 20; i++) {
            Book book = new Book();
            book.setVendorCode("VC" + (1000 + i));
            book.setTitle(titles.get(i));
            book.setYear(2000 + random.nextInt(25));
            book.setBrand(brands.get(i % brands.size()));
            book.setStock(5 + random.nextInt(20));
            book.setPrice(BigDecimal.valueOf(10 + random.nextDouble(90)));

            bookRepository.save(book);
        }

        System.out.println("🔰 20 книг добавлены в базу при старте приложения.");
    }

}
