package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Book} covering constructor, getters, and setters.
 */
class BookTest {

    /**
     * Verifies constructor and getters return expected values.
     */
    @Test
    void constructorAndGetters() {
        Book b = new Book("978-1234567890", "Test Title", "Ibrahim Rahimli", 19.95, 10);

        assertEquals("978-1234567890", b.getIsbn(),   "ISBN should match");
        assertEquals("Test Title",        b.getTitle(),  "Title should match");
        assertEquals("Ibrahim Rahimli",   b.getAuthor(), "Author should match");
        assertEquals(19.95,               b.getPrice(),  0.001,        "Price should match within tolerance");
        assertEquals(10,                  b.getQuantity(),"Quantity should match");
    }

    /**
     * Tests setters and ensures they update fields correctly.
     */
    @Test
    void settersAndGetters() {
        Book b = new Book("111", "Old", "OldAuth", 5.0, 1);
        b.setIsbn("222");
        b.setTitle("New");
        b.setAuthor("NewAuth");
        b.setPrice(7.5);
        b.setQuantity(3);

        assertEquals("222", b.getIsbn(),    "ISBN setter/getter");
        assertEquals("New", b.getTitle(),    "Title setter/getter");
        assertEquals("NewAuth", b.getAuthor(),"Author setter/getter");
        assertEquals(7.5, b.getPrice(), 0.001,  "Price setter/getter");
        assertEquals(3, b.getQuantity(),       "Quantity setter/getter");
    }

    /**
     * Ensures that invalid inputs are handled or produce expected behavior.
     * Current implementation accepts any values, so this verifies no exception thrown.
     */
    @Test
    void invalidInputs() {
        Book b = new Book(null, "", null, -1.0, -5);
        // null and negative values are allowed; getters return the same
        assertNull(b.getIsbn(), "Null ISBN allowed");
        assertEquals("", b.getTitle(), "Empty title allowed");
        assertNull(b.getAuthor(), "Null author allowed");
        assertEquals(-1.0, b.getPrice(), 0.001, "Negative price allowed");
        assertEquals(-5, b.getQuantity(), "Negative quantity allowed");
    }

    // TODO: if validation rules are added later, update tests accordingly
}