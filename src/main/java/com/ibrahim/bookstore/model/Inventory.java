package com.ibrahim.bookstore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the bookstore's inventory of books.
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
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    // TODO later: add methods to add/update/remove books
}
