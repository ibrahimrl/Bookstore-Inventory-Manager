/**
 * Provides the core business-logic layer for the Bookstore Inventory Manager.
 *
 * <p>The {@code com.ibrahim.bookstore.service} package contains classes responsible
 * for orchestrating operations on the underlying domain models (Inventory, Sale),
 * enforcing validation rules, managing persistence, and generating reports.
 * These services sit between the console-based UI (CLI) and the raw data models,
 * ensuring a clear separation of concerns.</p>
 *
 * <p>Key classes include:</p>
 * <ul>
 *   <li>{@link com.ibrahim.bookstore.service.InventoryManager} – wraps the
 *       Inventory model to provide add/update/remove/find operations and
 *       CSV persistence.</li>
 *   <li>{@link com.ibrahim.bookstore.service.SalesManager} – manages a history
 *       of {@link com.ibrahim.bookstore.model.Sale} records, including recording
 *       new sales and saving/loading from CSV.</li>
 *   <li>{@link com.ibrahim.bookstore.service.ReportGenerator} – produces
 *       formatted console reports such as total revenue, top-selling titles,
 *       low-stock alerts, and date-filtered sales.</li>
 * </ul>
 *
 * <p>All methods throw well-documented exceptions on invalid input or I/O failures,
 * and every public API is designed with comprehensive Javadoc and usage examples
 * to facilitate maintainability and extensibility.</p>
 */
package com.ibrahim.bookstore.service;