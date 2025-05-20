package com.ibrahim.bookstore.model;

/**
 * Represents a single book in inventory.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    // TODO: generate getters and setters, all with Javadoc
}
