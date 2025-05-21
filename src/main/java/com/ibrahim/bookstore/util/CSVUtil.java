package com.ibrahim.bookstore.util;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.model.Inventory;
import com.ibrahim.bookstore.model.Sale;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and writing Inventory and Sales data to CSV files.
 * <p>
 * Supports inventory CSVs with header:
 * <code>isbn,title,author,price,quantity</code>
 * and sales CSVs with header:
 * <code>isbn,title,author,price,quantity,timestamp</code>.
 * </p>
 */
public class CSVUtil {
    private static final String INVENTORY_HEADER = "isbn,title,author,price,quantity";
    private static final String SALES_HEADER = "isbn,title,author,price,quantity,timestamp";

    /**
     * Reads inventory data from a CSV file and returns a populated Inventory object.
     *
     * @param path the path to the CSV file containing inventory data
     * @return an Inventory containing all Book entries from the file
     * @throws IOException if there is an I/O error or invalid header
     */
    public static Inventory readInventoryFromCsv(String path) throws IOException {
        Inventory inv = new Inventory();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine();
            if (header == null || !header.equals(INVENTORY_HEADER)) {
                throw new IOException("Invalid inventory CSV header: " + header);
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 5) {
                    continue; // skip malformed lines
                }
                String isbn = parts[0];
                String title = parts[1];
                String author = parts[2];
                double price = Double.parseDouble(parts[3]);
                int qty = Integer.parseInt(parts[4]);
                inv.addBook(new Book(isbn, title, author, price, qty));
            }
        }
        return inv;
    }

    /**
     * Writes the given Inventory to a CSV file.
     *
     * @param inventory the Inventory to write
     * @param path      the file path for the output CSV
     * @throws IOException if there is an I/O error during writing
     */
    public static void writeInventoryToCsv(Inventory inventory, String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(INVENTORY_HEADER);
            for (Book b : inventory.getBooks()) {
                pw.printf("%s,%s,%s,%.2f,%d%n",
                        b.getIsbn(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getQuantity());
            }
        }
    }

    /**
     * Reads sales data from a CSV file and returns a list of Sale objects.
     *
     * @param path the path to the CSV file containing sales data
     * @return a List of Sale records from the file
     * @throws IOException if there is an I/O error or invalid header
     */
    public static List<Sale> readSalesFromCsv(String path) throws IOException {
        List<Sale> sales = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine();
            if (header == null || !header.equals(SALES_HEADER)) {
                throw new IOException("Invalid sales CSV header: " + header);
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 6) {
                    continue; // skip malformed lines
                }
                String isbn = parts[0];
                String title = parts[1];
                String author = parts[2];
                double price = Double.parseDouble(parts[3]);
                int qty = Integer.parseInt(parts[4]);
                LocalDateTime timestamp;
                try {
                    timestamp = LocalDateTime.parse(parts[5]);
                } catch (DateTimeParseException e) {
                    // skip entries with invalid timestamp
                    continue;
                }
                Book book = new Book(isbn, title, author, price, qty);
                sales.add(new Sale(book, qty, timestamp));
            }
        }
        return sales;
    }

    /**
     * Writes the given list of Sale records to a CSV file.
     *
     * @param sales the list of Sale records to write
     * @param path  the file path for the output CSV
     * @throws IOException if there is an I/O error during writing
     */
    public static void writeSalesToCsv(List<Sale> sales, String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(SALES_HEADER);
            for (Sale s : sales) {
                Book b = s.getBook();
                pw.printf("%s,%s,%s,%.2f,%d,%s%n",
                        b.getIsbn(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getQuantity(),
                        s.getTimestamp().toString());
            }
        }
    }
}
