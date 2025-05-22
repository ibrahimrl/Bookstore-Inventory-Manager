/**
 * Defines the core domain entities for the Bookstore Inventory Manager.
 *
 * <p>The {@code com.ibrahim.bookstore.model} package contains
 * Plain-Old-Java-Objects (POJOs) representing the fundamental data:
 * </p>
 * <ul>
 *   <li>{@link com.ibrahim.bookstore.model.Book}
 *       – encapsulates ISBN, title, author, price, and quantity.</li>
 *   <li>{@link com.ibrahim.bookstore.model.Inventory}
 *       – in-memory list of {@code Book} with CRUD operations.</li>
 *   <li>{@link com.ibrahim.bookstore.model.Sale}
 *       – records a sale transaction with timestamp.</li>
 * </ul>
 *
 * <p>All public APIs include full Javadoc on constructors, getters, and setters.</p>
 */
package com.ibrahim.bookstore.model;
