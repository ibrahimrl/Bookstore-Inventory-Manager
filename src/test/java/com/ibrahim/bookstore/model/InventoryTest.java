package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Inventory} model.
 */
class InventoryTest {
    private Inventory inv;
    private Book sample;

    /**
     * Initializes a fresh Inventory and sample Book before each test.
     */
    @BeforeEach
    void setup() {
        inv = new Inventory();
        sample = new Book("111", "Sample", "Author", 5.0, 2);
    }

    /**
     * Inventory should start empty and return an unmodifiable list.
     */
    @Test
    void constructorAndGetBooks() {
        assertNotNull(inv.getBooks(), "Book list should not be null");
        assertTrue(inv.getBooks().isEmpty(), "Inventory should start empty");
        assertThrows(UnsupportedOperationException.class,
                () -> inv.getBooks().add(sample),
                "getBooks() should be unmodifiable");
    }

    /**
     * Adding a book increases list size and findByIsbn returns the instance.
     */
    @Test
    void addBookAndFind() {
        inv.addBook(sample);
        assertEquals(1, inv.getBooks().size(), "Inventory should have one book");
        assertSame(sample, inv.findByIsbn("111"), "findByIsbn should return the same instance");
    }

    /**
     * Removing by existing ISBN works; removing non-existent ISBN returns false.
     */
    @Test
    void removeBookByIsbn() {
        inv.addBook(sample);
        assertTrue(inv.removeBookByIsbn("111"), "Existing ISBN should be removed");
        assertTrue(inv.getBooks().isEmpty(), "Inventory should be empty after removal");
        assertFalse(inv.removeBookByIsbn("000"), "Non-existent ISBN removal should return false");
    }

    /**
     * Updating an existing book changes its fields; updating missing ISBN fails.
     */
    @Test
    void updateBook() {
        inv.addBook(sample);
        Book updated = new Book("111", "New Title", "New Author", 6.0, 3);

        assertTrue(inv.updateBook(updated), "updateBook should succeed for existing ISBN");
        Book b2 = inv.findByIsbn("111");
        assertEquals("New Title",  b2.getTitle(),    "Title updated");
        assertEquals("New Author", b2.getAuthor(),   "Author updated");
        assertEquals(6.0,          b2.getPrice(),    0.001, "Price updated");
        assertEquals(3,            b2.getQuantity(),     "Quantity updated");

        assertFalse(inv.updateBook(new Book("999","x","y",1.0,1)),
                "updateBook should fail for missing ISBN");
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
