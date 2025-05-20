package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Sale}.
 */
class SaleTest {

    @Test
    void constructorAndGetters() {
        Book b = new Book("1", "X", "Y", 1.0, 1);
        LocalDateTime now = LocalDateTime.now();
        Sale s = new Sale(b, 2, now);

        assertEquals(b,         s.getBook());
        assertEquals(2,         s.getQuantity());
        assertEquals(now,       s.getTimestamp());
    }
}
