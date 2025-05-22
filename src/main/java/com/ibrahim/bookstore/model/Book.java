package com.ibrahim.bookstore.model;

/**
 * Represents a single book in inventory.
 *
 * <p>Each {@code Book} has an ISBN, title, author, unit price, and
 * a current stock quantity.</p>
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;
    private int quantity;

    /**
     * Returns the ISBN code of this book.
     * @return non-null ISBN string
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN code.
     * @param isbn the ISBN to assign (must not be null or empty)
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the book title.
     * @return non-null title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book title.
     * @param title the title to assign (must not be null or empty)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author’s name.
     * @return non-null author string
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author’s name.
     * @param author the author to assign (must not be null or empty)
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the unit price of this book.
     * @return price as a non-negative double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the unit price.
     * @param price non-negative price value
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the current stock quantity.
     * @return non-negative integer quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the stock quantity.
     * @param quantity non-negative integer
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Constructs a new Book.
     *
     * @param isbn     the ISBN code
     * @param title    the book title
     * @param author   the author name
     * @param price    the unit price
     * @param quantity initial stock
     */
    public Book(String isbn, String title, String author, double price, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }
}
