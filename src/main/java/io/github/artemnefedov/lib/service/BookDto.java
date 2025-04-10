package io.github.artemnefedov.lib.service;

import io.github.artemnefedov.lib.repository.Book;
import java.math.BigDecimal;

public record BookDto(
        long id,
        String vendorCode,
        String title,
        int year,
        String brand,
        int stock,
        double price
) {

    public Book toEntity() {
        return new Book(
                id,
                vendorCode,
                title,
                year,
                brand,
                stock,
                BigDecimal.valueOf(price)
        );
    }

}
