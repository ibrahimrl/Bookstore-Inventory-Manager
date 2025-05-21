package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {
    private static final String TEST_CSV = "test-inv.csv";
    private InventoryManager mgr;

    @BeforeEach
    void setup() throws IOException {
        // create a temp CSV
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

    @Test
    void loadAndList() {
        List<Book> all = mgr.listAllBooks();
        assertEquals(1, all.size());
        assertEquals("A1", all.get(0).getIsbn());
    }

    @Test
    void addRemoveSave() throws IOException {
        Book b2 = new Book("B2","Book B","Author B",5.5,2);
        mgr.addBook(b2);
        assertTrue(mgr.removeBook("A1"));
        mgr.saveInventory(TEST_CSV);

        // reload into a fresh manager
        InventoryManager m2 = new InventoryManager(TEST_CSV);
        assertEquals(1, m2.listAllBooks().size());
        assertEquals("B2", m2.listAllBooks().get(0).getIsbn());
    }
}
