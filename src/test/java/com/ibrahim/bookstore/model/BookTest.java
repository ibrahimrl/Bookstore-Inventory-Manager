package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Book}.
 */
class BookTest {

    @Test
    void constructorAndGetters() {
        Book b = new Book("978-1234567890", "Test Title", "Ibrahim Rahimli", 19.95, 10);

        assertEquals("978-1234567890", b.getIsbn(),   "ISBN should match");
        assertEquals("Test Title",        b.getTitle(),  "Title should match");
        assertEquals("Ibrahim Rahimli",          b.getAuthor(), "Author should match");
        assertEquals(19.95,               b.getPrice(),  0.001,        "Price should match within tolerance");
        assertEquals(10,                  b.getQuantity(),"Quantity should match");
    }

    // TODO: add tests for setters and invalid inputs
}
