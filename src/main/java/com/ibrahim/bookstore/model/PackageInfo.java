/**
 * Defines the core domain entities for the Bookstore Inventory Manager.
 *
 * <p>
 * The {@code com.ibrahim.bookstore.model} package contains plain‐old‐Java‐objects
 * (POJOs) that represent the fundamental data structures:
 * </p>
 * <ul>
 *   <li>{@link com.ibrahim.bookstore.model.Book} — encapsulates the ISBN, title,
 *       author, price, and stock quantity of a single book.</li>
 *   <li>{@link com.ibrahim.bookstore.model.Inventory} — holds an in‐memory list
 *       of {@code Book} instances and provides add/update/remove/find operations.</li>
 *   <li>{@link com.ibrahim.bookstore.model.Sale} — records a sale transaction
 *       including the book sold, quantity moved, and timestamp of the sale.</li>
 * </ul>
 *
 * <p>
 * These model classes are designed with full Javadoc on every constructor,
 * getter, and setter, and they form the immutable core of the application’s data
 * layer.  Business logic lives elsewhere, ensuring a clear separation of concerns.
 * </p>
 */
package com.ibrahim.bookstore.model;
