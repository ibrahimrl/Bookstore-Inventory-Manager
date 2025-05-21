package com.ibrahim.bookstore.util;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.model.Inventory;

import java.io.*;
import java.util.List;

/**
 * Simple CSV loader/writer for Inventory.
 */
public class CSVUtil {
    private static final String HEADER = "isbn,title,author,price,quantity";

    /**
     * Reads CSV and returns a populated Inventory.
     *
     * @param path the CSV file path
     * @return an Inventory with all books
     * @throws IOException on I/O error
     */
    public static Inventory readInventoryFromCsv(String path) throws IOException {
        Inventory inv = new Inventory();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // header
            if (line == null || !line.equals(HEADER)) {
                throw new IOException("Invalid CSV header");
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
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
     * Writes the given inventory to CSV.
     *
     * @param inventory the inventory to write
     * @param path      the file path
     * @throws IOException on I/O error
     */
    public static void writeInventoryToCsv(Inventory inventory, String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(HEADER);
            for (Book b : inventory.getBooks()) {
                pw.printf("%s,%s,%s,%.2f,%d%n",
                        b.getIsbn(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getQuantity());
            }
        }
    }
}
