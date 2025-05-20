package com.ibrahim.bookstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Inventory}.
 */
class InventoryTest {

    @Test
    void constructorAndGetBooks() {
        Inventory inv = new Inventory();
        assertNotNull(inv.getBooks(), "Book list should not be null");
        assertTrue(inv.getBooks().isEmpty(), "Inventory should start empty");
    }
}
