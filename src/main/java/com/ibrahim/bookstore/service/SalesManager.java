package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Sale;
import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.util.CSVUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages sale transactions and history.
 *
 * <p>This class records each sale, persists to CSV, and can
 * generate summary statistics such as total revenue and
 * top-selling titles.</p>
 *
 * @author ...
 */
public class SalesManager {
    private final List<Sale> sales;

    /**
     * Constructs an empty SalesManager.
     */
    public SalesManager() {
        this.sales = new ArrayList<>();
    }

    /**
     * Loads sales history from CSV.
     *
     * @param csvPath path to sales CSV
     * @throws IOException if the file cannot be read
     */
    public SalesManager(String csvPath) throws IOException {
        this.sales = CSVUtil.readSalesFromCsv(csvPath);
    }

    /**
     * Records a sale of {@code quantity} copies of {@code book}
     * at the current timestamp.
     *
     * @param book     the book sold
     * @param quantity number of copies sold
     */
    public void recordSale(Book book, int quantity) {
        sales.add(new Sale(book, quantity, LocalDateTime.now()));
    }

    /**
     * Returns all recorded sales.
     */
    public List<Sale> listAllSales() {
        return new ArrayList<>(sales);
    }

    /**
     * Saves current sales history to CSV.
     *
     * @param csvPath file to write to
     * @throws IOException if writing fails
     */
    public void saveSales(String csvPath) throws IOException {
        CSVUtil.writeSalesToCsv(sales, csvPath);
    }

    // TODO: add methods for totalRevenue(), topSellingBooks(), salesByDateRange()...
}
