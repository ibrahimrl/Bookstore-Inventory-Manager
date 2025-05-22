package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.model.Sale;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ReportGenerator}, verifying each reporting method:
 * <ul>
 *   <li>printTotalSales()</li>
 *   <li>printTopSellingBooks()</li>
 *   <li>printLowStock()</li>
 *   <li>printSalesByDateRange()</li>
 * </ul>
 */
class ReportGeneratorTest {
    private InventoryManager invMgr;
    private SalesManager salesMgr;
    private ReportGenerator reportGen;

    private static final String TEST_SALES_CSV = "test-sales-report.csv";

    /**
     * Sets up an inventory with two books and records two sales.
     */
    @BeforeEach
    void setup() {
        invMgr = new InventoryManager();
        Book bookA = new Book("A1", "Book A", "Author A", 10.0, 1);
        Book bookB = new Book("B2", "Book B", "Author B", 5.0, 2);
        invMgr.addBook(bookA);
        invMgr.addBook(bookB);

        salesMgr = new SalesManager();
        salesMgr.recordSale(bookA, 2); // revenue = 20
        salesMgr.recordSale(bookB, 3); // revenue = 15

        reportGen = new ReportGenerator(invMgr, salesMgr);
    }

    /**
     * Verifies that total revenue is calculated and printed correctly.
     */
    @Test
    void testPrintTotalSales() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            reportGen.printTotalSales();
        } finally {
            System.setOut(original);
        }
        String output = out.toString();
        assertTrue(output.contains("Total Revenue"), "Should print revenue header");
        assertTrue(output.contains("€35.00"),        "Revenue should be €35.00");
    }

    /**
     * Verifies that top-selling books are listed in descending order.
     */
    @Test
    void testPrintTopSellingBooks() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            reportGen.printTopSellingBooks(2);
        } finally {
            System.setOut(original);
        }
        String output = out.toString();
        assertTrue(output.contains("Top 2 Selling Books"), "Should print top-selling header");
        assertTrue(output.contains("A1 - Book A"),          "Should list Book A");
        assertTrue(output.contains("B2 - Book B"),          "Should list Book B");
    }

    /**
     * Verifies low-stock alert prints only books below threshold.
     */
    @Test
    void testPrintLowStock() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            reportGen.printLowStock(2);
        } finally {
            System.setOut(original);
        }
        String output = out.toString();
        assertTrue(output.contains("Low Stock Books"),      "Should print low-stock header");
        assertTrue(output.contains("A1"),                   "Book A1 (qty=1) should appear");
        assertFalse(output.contains("B2"),                  "Book B2 (qty=2) should not appear at threshold 2");
    }

    /**
     * Nested tests for date-range filtering of sales.
     */
    @Nested
    class DateRangeTests {
        /**
         * Writes a CSV with sales on three known dates.
         */
        @BeforeEach
        void writeCsv() throws IOException {
            List<String> lines = List.of(
                    "isbn,title,author,price,quantity,timestamp",
                    "X1,Book X,Author X,1.0,1,2025-01-01T00:00:00",
                    "X2,Book Y,Author Y,2.0,2,2025-05-20T12:00:00",
                    "X3,Book Z,Author Z,3.0,3,2025-12-31T23:59:59"
            );
            Files.write(Paths.get(TEST_SALES_CSV), lines);
            salesMgr = new SalesManager(TEST_SALES_CSV);
            reportGen = new ReportGenerator(invMgr, salesMgr);
        }

        /**
         * Cleans up the temporary CSV.
         */
        @AfterEach
        void cleanup() throws IOException {
            Files.deleteIfExists(Paths.get(TEST_SALES_CSV));
        }

        /**
         * Verifies only sales within the given date range are printed.
         */
        @Test
        void testPrintSalesByDateRange() {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream original = System.out;
            System.setOut(new PrintStream(out));
            try {
                reportGen.printSalesByDateRange(
                        LocalDate.of(2025, 5, 1),
                        LocalDate.of(2025, 5, 30)
                );
            } finally {
                System.setOut(original);
            }
            String output = out.toString();
            assertTrue(output.contains("Sales from 2025-05-01 to 2025-05-30"),
                    "Should print date-range header");
            assertTrue(output.contains("X2"),      "Should include sale X2");
            assertFalse(output.contains("X1"),     "Should exclude sale before range");
            assertFalse(output.contains("X3"),     "Should exclude sale after range");
        }
    }
}
