package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.model.Sale;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SalesManager}, covering loading, recording,
 * persistence, defensive copying, and file creation.
 */
class SalesManagerTest {
    private static final String TEST_SALES_CSV = "test-sales.csv";
    private SalesManager mgr;

    /**
     * Writes a sample CSV and loads it into the manager.
     */
    @BeforeEach
    void setup() throws IOException {
        try (FileWriter fw = new FileWriter(TEST_SALES_CSV)) {
            fw.write("isbn,title,author,price,quantity,timestamp\n");
            LocalDateTime ts = LocalDateTime.of(2025, 5, 20, 12, 34, 56);
            fw.write(String.format("A1,Book A,Author A,10.00,2,%s%n", ts));
        }
        mgr = new SalesManager(TEST_SALES_CSV);
    }

    /**
     * Deletes the temporary CSV after each test.
     */
    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(new File(TEST_SALES_CSV).toPath());
    }

    /**
     * Verifies loading and listing of sales from CSV.
     */
    @Test
    void loadAndListSales() {
        List<Sale> sales = mgr.listAllSales();
        assertEquals(1, sales.size(), "Should load exactly one sale");
        Sale s = sales.get(0);
        assertEquals("A1", s.getBook().getIsbn(),    "ISBN should match");
        assertEquals("Book A", s.getBook().getTitle(),"Title should match");
        assertEquals("Author A", s.getBook().getAuthor(),"Author should match");
        assertEquals(10.00, s.getBook().getPrice(), 0.001,"Price should match");
        assertEquals(2, s.getQuantity(),            "Quantity should match");
        assertEquals(LocalDateTime.of(2025,5,20,12,34,56),
                s.getTimestamp(),"Timestamp should match");
    }

    /**
     * Records a new sale, saves to CSV, and reloads to ensure persistence.
     */
    @Test
    void recordSaleAndPersistence() throws IOException {
        Book bookB = new Book("B2","Book B","Author B",5.50,3);
        mgr.recordSale(bookB, 3);

        List<Sale> before = mgr.listAllSales();
        assertEquals(2, before.size(), "Should have two sales after recording");

        mgr.saveSales(TEST_SALES_CSV);
        SalesManager reloaded = new SalesManager(TEST_SALES_CSV);
        List<Sale> after = reloaded.listAllSales();
        assertEquals(2, after.size(), "Reload should preserve both sales");

        Sale s2 = after.stream().filter(s -> "B2".equals(s.getBook().getIsbn()))
                .findFirst().orElse(null);
        assertNotNull(s2, "Second sale should be present");
        assertEquals(3, s2.getQuantity(), "Quantity of second sale should match");
    }

    /**
     * Ensures listAllSales returns a defensive copy.
     */
    @Test
    void listAllSalesReturnsCopy() {
        List<Sale> sales = mgr.listAllSales();
        sales.clear();
        assertFalse(mgr.listAllSales().isEmpty(),
                "Clearing returned list should not affect internal data");
    }

    /**
     * Verifies that saveSales creates or leaves a non-empty CSV file.
     */
    @Test
    void saveSalesCreatesFile() throws IOException {
        File f = new File(TEST_SALES_CSV);
        assertTrue(f.exists(), "CSV file should exist after setup");
        mgr.saveSales(TEST_SALES_CSV);
        assertTrue(f.length() > 0, "CSV file should be non-empty after saving");
    }
}
