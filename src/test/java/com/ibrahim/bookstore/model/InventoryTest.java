package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Inventory}.
 */
class InventoryTest {
    private Inventory inv;
    private Book sample;

    @BeforeEach
    void setup() {
        inv = new Inventory();
        sample = new Book("111", "Sample", "Author", 5.0, 2);
    }

    @Test
    void constructorAndGetBooks() {
        assertNotNull(inv.getBooks(), "Book list should not be null");
        assertTrue(inv.getBooks().isEmpty(), "Inventory should start empty");
    }

    @Test
    void addBookAndFind() {
        inv.addBook(sample);
        assertEquals(1, inv.getBooks().size(), "Inventory should have one book");
        assertSame(sample, inv.findByIsbn("111"), "findByIsbn should return the same instance");
    }

    @Test
    void removeBookByIsbn() {
        inv.addBook(sample);
        assertTrue(inv.removeBookByIsbn("111"), "removeBookByIsbn should return true when removing existing book");
        assertTrue(inv.getBooks().isEmpty(), "Inventory should be empty after removal");
        // removing a non‐existent ISBN should return false
        assertFalse(inv.removeBookByIsbn("000"), "removeBookByIsbn returns false for missing ISBN");
    }

    @Test
    void updateBook() {
        inv.addBook(sample);
        Book updated = new Book("111", "New Title", "New Author", 6.0, 3);

        // should succeed on existing ISBN
        assertTrue(inv.updateBook(updated), "updateBook should return true for existing ISBN");

        Book b2 = inv.findByIsbn("111");
        assertEquals("New Title",  b2.getTitle(),    "Title should be updated");
        assertEquals("New Author", b2.getAuthor(),   "Author should be updated");
        assertEquals(6.0,          b2.getPrice(),    0.001, "Price should be updated within tolerance");
        assertEquals(3,            b2.getQuantity(), "Quantity should be updated");

        // updating a non‐existent ISBN should fail
        assertFalse(inv.updateBook(new Book("999", "X", "Y", 1.0, 1)),
                "updateBook returns false for missing ISBN");
    }

    @Test
    void getBooksUnmodifiable() {
        inv.addBook(sample);
        // Attempting to modify the returned list should throw
        assertThrows(UnsupportedOperationException.class, () -> {
            inv.getBooks().add(new Book("222", "A", "B", 2.0, 1));
        }, "getBooks() should return an unmodifiable list");
    }
}
