/**
 * Utility and helper classes for the Bookstore Inventory Manager console app.
 * <p>
 * The {@code com.ibrahim.bookstore.util} package provides stateless, reusable
 * utilities that support both user interaction and data persistence:
 * <ul>
 *   <li>{@link com.ibrahim.bookstore.util.CSVUtil} — reads and writes
 *       inventory and sales data in CSV format, handling headers, parsing,
 *       formatting, and I/O errors.</li>
 *   <li>{@link com.ibrahim.bookstore.util.CLI} — offers a rich set of static
 *       methods for console–based prompts, input validation loops, menus,
 *       yes/no confirmations, and formatted output messages.</li>
 * </ul>
 * <p>
 * All methods in these classes include extensive Javadoc, describe error
 * conditions via {@code @throws} tags, and are designed for easy extension
 * (e.g. plugging in a different I/O format or UI layer).
 */
package com.ibrahim.bookstore.util;