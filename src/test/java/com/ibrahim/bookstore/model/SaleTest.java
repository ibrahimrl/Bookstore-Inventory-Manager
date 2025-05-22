package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Sale} model.
 */
class SaleTest {

    /**
     * Verifies constructor and getters on Sale.
     */
    @Test
    void constructorAndGetters() {
        Book b = new Book("1", "X", "Y", 1.0, 1);
        LocalDateTime now = LocalDateTime.now();
        Sale s = new Sale(b, 2, now);

        assertEquals(b,   s.getBook(),      "Sale should reference same book");
        assertEquals(2,   s.getQuantity(),  "Quantity should match");
        assertEquals(now, s.getTimestamp(), "Timestamp should match");
    }
}