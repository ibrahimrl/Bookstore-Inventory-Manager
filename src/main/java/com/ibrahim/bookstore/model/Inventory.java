package com.ibrahim.bookstore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the bookstore’s inventory of books.
 */
public class Inventory {
    private final List<Book> books;

    /**
     * Constructs an empty Inventory.
     */
    public Inventory() {
        this.books = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable list of all books in inventory.
     *
     * @return an unmodifiable {@link List} of {@link Book} instances
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Adds a new book to the inventory.
     *
     * @param book the {@link Book} to add (must not be null)
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Removes a book by its ISBN.
     *
     * @param isbn the ISBN of the book to remove
     * @return {@code true} if a book was removed, {@code false} otherwise
     */
    public boolean removeBookByIsbn(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    /**
     * Updates an existing book’s details, matching by ISBN.
     *
     * @param updated the {@link Book} containing new data (same ISBN)
     * @return {@code true} if the book was found and updated, {@code false} otherwise
     */
    public boolean updateBook(Book updated) {
        Optional<Book> existing = books.stream()
                .filter(b -> b.getIsbn().equals(updated.getIsbn()))
                .findFirst();
        if (existing.isPresent()) {
            Book b = existing.get();
            b.setTitle(updated.getTitle());
            b.setAuthor(updated.getAuthor());
            b.setPrice(updated.getPrice());
            b.setQuantity(updated.getQuantity());
            return true;
        }
        return false;
    }

    /**
     * Searches for a book by ISBN.
     *
     * @param isbn the ISBN to search
     * @return the {@link Book} if found, or {@code null} if not present
     */
    public Book findByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }
}
