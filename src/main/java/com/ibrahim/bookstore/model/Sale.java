package com.ibrahim.bookstore.model;

import java.time.LocalDateTime;

/**
 * Represents a sale transaction for a given book.
 */
public class Sale {
    private final Book book;
    private final int quantity;
    private final LocalDateTime timestamp;

    /**
     * Constructs a Sale record.
     *
     * @param book      the book sold
     * @param quantity  how many copies
     * @param timestamp when the sale occurred
     */
    public Sale(Book book, int quantity, LocalDateTime timestamp) {
        this.book = book;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
