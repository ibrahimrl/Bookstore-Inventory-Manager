package com.ibrahim.bookstore.service;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.model.Inventory;
import com.ibrahim.bookstore.util.CSVUtil;

import java.io.IOException;
import java.util.List;

/**
 * Manages the bookstore inventory: in-memory operations plus load/save.
 */
public class InventoryManager {
    private final Inventory inventory;

    /**
     * Constructs a new empty manager.
     */
    public InventoryManager() {
        this.inventory = new Inventory();
    }

    /**
     * Constructs a manager and loads inventory from CSV.
     *
     * @param csvPath path to CSV file
     * @throws IOException if the file can't be read
     */
    public InventoryManager(String csvPath) throws IOException {
        this.inventory = CSVUtil.readInventoryFromCsv(csvPath);
    }

    /**
     * Returns all books currently in inventory.
     *
     * @return a {@link List} of {@link Book} instances in inventory
     */
    public List<Book> listAllBooks() {
        return inventory.getBooks();
    }

    /**
     * Adds a new book.
     *
     * @param book the {@link Book} to add
     */
    public void addBook(Book book) {
        inventory.addBook(book);
    }

    /**
     * Removes a book by ISBN.
     *
     * @param isbn the ISBN to remove
     * @return {@code true} if removed, {@code false} otherwise
     */
    public boolean removeBook(String isbn) {
        return inventory.removeBookByIsbn(isbn);
    }

    /**
     * Updates a book. Matches by ISBN.
     *
     * @param book updated data
     * @return {@code true} if updated, {@code false} otherwise
     */
    public boolean updateBook(Book book) {
        return inventory.updateBook(book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to find
     * @return the {@link Book} or {@code null} if not found
     */
    public Book findBook(String isbn) {
        return inventory.findByIsbn(isbn);
    }

    /**
     * Saves the current inventory to a CSV file.
     *
     * @param csvPath file to write to
     * @throws IOException if writing fails
     */
    public void saveInventory(String csvPath) throws IOException {
        CSVUtil.writeInventoryToCsv(inventory, csvPath);
    }
}