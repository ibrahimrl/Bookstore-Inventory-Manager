package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.model.Sale;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates console reports based on inventory and sales data.
 *
 * <p>This class provides methods to format and print:</p>
 * <ul>
 *   <li>Total revenue across all sales</li>
 *   <li>Top-selling books by quantity</li>
 *   <li>Books low in stock below a given threshold</li>
 *   <li>Sales within a specific date range</li>
 * </ul>
 */
public class ReportGenerator {
    private final InventoryManager inventoryManager;
    private final SalesManager salesManager;

    /**
     * Constructs a ReportGenerator using the given managers.
     *
     * @param inventoryManager the InventoryManager to query stock levels
     * @param salesManager     the SalesManager to query sales history
     */
    public ReportGenerator(InventoryManager inventoryManager, SalesManager salesManager) {
        this.inventoryManager = inventoryManager;
        this.salesManager = salesManager;
    }

    /**
     * Prints the total revenue from all recorded sales.
     *
     * <p>Revenue is computed as sum of (price × quantity) per sale.</p>
     */
    public void printTotalSales() {
        List<Sale> sales = salesManager.listAllSales();
        double total = sales.stream()
                .mapToDouble(s -> s.getBook().getPrice() * s.getQuantity())
                .sum();
        System.out.printf("Total Revenue: €%.2f%n", total);
    }

    /**
     * Prints the top N selling books by total units sold.
     *
     * @param topN the number of top titles to display
     */
    public void printTopSellingBooks(int topN) {
        Map<String, Integer> counts = salesManager.listAllSales().stream()
                .collect(Collectors.groupingBy(
                        s -> s.getBook().getIsbn() + " - " + s.getBook().getTitle(),
                        Collectors.summingInt(Sale::getQuantity)
                ));

        System.out.println("\nTop " + topN + " Selling Books:");
        System.out.printf("%-30s %10s%n", "Book (ISBN - Title)", "Units Sold");
        counts.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .forEach(e -> System.out.printf("%-30s %10d%n", e.getKey(), e.getValue()));
    }

    /**
     * Prints all books whose stock quantity is below the given threshold.
     *
     * @param threshold the maximum stock level to flag as low
     */
    public void printLowStock(int threshold) {
        List<Book> books = inventoryManager.listAllBooks().stream()
                .filter(b -> b.getQuantity() < threshold)
                .collect(Collectors.toList());

        System.out.println("\nLow Stock Books (below " + threshold + "): ");
        System.out.printf("%-15s %-25s %10s%n", "ISBN", "Title", "Quantity");
        for (Book b : books) {
            System.out.printf("%-15s %-25s %10d%n", b.getIsbn(), b.getTitle(), b.getQuantity());
        }
    }

    /**
     * Prints all sales that occurred between the given start and end dates (inclusive).
     *
     * @param start inclusive start date
     * @param end   inclusive end date
     */
    public void printSalesByDateRange(LocalDate start, LocalDate end) {
        List<Sale> filtered = salesManager.listAllSales().stream()
                .filter(s -> {
                    LocalDate d = s.getTimestamp().toLocalDate();
                    return (d.isEqual(start) || d.isAfter(start))
                            && (d.isEqual(end)   || d.isBefore(end));
                })
                .collect(Collectors.toList());

        System.out.println("\nSales from " + start + " to " + end + ":");
        System.out.printf("%-15s %-25s %10s %20s%n", "ISBN", "Title", "Quantity", "Timestamp");
        for (Sale s : filtered) {
            System.out.printf("%-15s %-25s %10d %20s%n",
                    s.getBook().getIsbn(),
                    s.getBook().getTitle(),
                    s.getQuantity(),
                    s.getTimestamp());
        }
    }
}
