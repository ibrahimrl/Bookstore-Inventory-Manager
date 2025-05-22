package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Book;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link InventoryManager} service.
 */
class InventoryManagerTest {
    private static final String TEST_CSV = "test-inv.csv";
    private InventoryManager mgr;

    @BeforeEach
    void setup() throws IOException {
        try (FileWriter fw = new FileWriter(TEST_CSV)) {
            fw.write("isbn,title,author,price,quantity\n");
            fw.write("A1,Book A,Author A,10.00,1\n");
        }
        mgr = new InventoryManager(TEST_CSV);
    }

    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(new File(TEST_CSV).toPath());
    }

    /**
     * Loads from CSV and lists books.
     */
    @Test
    void loadAndList() {
        List<Book> all = mgr.listAllBooks();
        assertEquals(1, all.size(), "Should load one book");
        assertEquals("A1", all.get(0).getIsbn(), "ISBN loaded correctly");
    }

    /**
     * Tests add, remove, save, and reload functionality.
     */
    @Test
    void addRemoveSave() throws IOException {
        Book b2 = new Book("B2","Book B","Author B",5.5,2);
        mgr.addBook(b2);
        assertTrue(mgr.removeBook("A1"), "Should remove existing book");
        mgr.saveInventory(TEST_CSV);

        InventoryManager m2 = new InventoryManager(TEST_CSV);
        assertEquals(1, m2.listAllBooks().size(), "Reload should have one book");
        assertEquals("B2", m2.listAllBooks().get(0).getIsbn(), "...with correct ISBN");
    }
}
