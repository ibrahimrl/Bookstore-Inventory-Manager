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
 * Unit tests for {@link ReportGenerator}.
 */
class ReportGeneratorTest {
    private InventoryManager invMgr;
    private SalesManager salesMgr;
    private ReportGenerator reportGen;

    private static final String TEST_SALES_CSV = "test-sales-report.csv";

    @BeforeEach
    void setup() {
        // Set up inventory with two books
        invMgr = new InventoryManager();
        Book bookA = new Book("A1", "Book A", "Author A", 10.0, 1);
        Book bookB = new Book("B2", "Book B", "Author B", 5.0, 2);
        invMgr.addBook(bookA);
        invMgr.addBook(bookB);

        // Set up sales manager and record sales
        salesMgr = new SalesManager();
        salesMgr.recordSale(bookA, 2); // revenue = 20
        salesMgr.recordSale(bookB, 3); // revenue = 15

        reportGen = new ReportGenerator(invMgr, salesMgr);
    }

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
        assertTrue(output.contains("€35.00"), "Revenue should be €35.00");
    }

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
        assertTrue(output.contains("A1 - Book A"), "Should list Book A");
        assertTrue(output.contains("B2 - Book B"), "Should list Book B");
    }

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
        assertTrue(output.contains("Low Stock Books"), "Should print low-stock header");
        assertTrue(output.contains("A1"), "Book A1 (qty=1) should appear");
        assertFalse(output.contains("B2"), "Book B2 (qty=2) should not appear when threshold is 2");
    }

    @Nested
    class DateRangeTests {
        @BeforeEach
        void writeCsv() throws IOException {
            // Create a CSV with three sale records at known dates
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

        @AfterEach
        void cleanup() throws IOException {
            Files.deleteIfExists(Paths.get(TEST_SALES_CSV));
        }

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
            assertTrue(output.contains("X2"), "Should include sale X2");
            assertFalse(output.contains("X1"), "Should exclude sale before range");
            assertFalse(output.contains("X3"), "Should exclude sale after range");
        }
    }
}
