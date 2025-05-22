package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Sale;
import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.util.CSVUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages sale transactions and history.
 *
 * <p>This class records each sale, persists to CSV, and provides
 * analytics such as total revenue, top-selling titles, and filtering
 * by date range.</p>
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
     * Returns all recorded sales as a new list.
     *
     * @return defensive copy of sales history
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

    /**
     * Computes the total revenue from all sales.
     *
     * @return sum of (price × quantity) across all sales
     */
    public double totalRevenue() {
        return sales.stream()
                .mapToDouble(s -> s.getBook().getPrice() * s.getQuantity())
                .sum();
    }

    /**
     * Returns the top‐N selling books by total units sold.
     *
     * @param topN the number of top titles to return
     * @return a map of "ISBN - Title" to units sold, sorted descending
     */
    public Map<String, Integer> topSellingBooks(int topN) {
        Map<String, Integer> counts = sales.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getBook().getIsbn() + " - " + s.getBook().getTitle(),
                        Collectors.summingInt(Sale::getQuantity)
                ));

        return counts.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .collect(LinkedHashMap::new,
                        (m,e) -> m.put(e.getKey(), e.getValue()),
                        LinkedHashMap::putAll);
    }

    /**
     * Filters sales between the given start and end dates (inclusive).
     *
     * @param start inclusive start date
     * @param end   inclusive end date
     * @return list of {@code Sale} in the date range
     */
    public List<Sale> salesByDateRange(LocalDate start, LocalDate end) {
        return sales.stream()
                .filter(s -> {
                    LocalDate d = s.getTimestamp().toLocalDate();
                    return (d.isEqual(start) || d.isAfter(start))
                            && (d.isEqual(end)   || d.isBefore(end));
                })
                .collect(Collectors.toList());
    }
}
