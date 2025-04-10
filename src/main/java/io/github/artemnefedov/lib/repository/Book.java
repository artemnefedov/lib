package io.github.artemnefedov.lib.repository;

import io.github.artemnefedov.lib.service.BookDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String vendorCode;

    private String title;
    private Integer year;
    private String brand;
    private Integer stock;
    private BigDecimal price;

    public Book() {
    }

    public Book(Long id, String vendorCode, String title, Integer year, String brand, Integer stock,
            BigDecimal price) {
        this.id = id;
        this.vendorCode = vendorCode;
        this.title = title;
        this.year = year;
        this.brand = brand;
        this.stock = stock;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public BookDto toDto() {
        return new BookDto(
                id,
                vendorCode,
                title,
                year,
                brand,
                stock,
                price.doubleValue()
        );
    }
}
