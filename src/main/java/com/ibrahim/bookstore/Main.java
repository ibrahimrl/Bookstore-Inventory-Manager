package com.ibrahim.bookstore;

import com.ibrahim.bookstore.model.Book;
import com.ibrahim.bookstore.service.InventoryManager;
import com.ibrahim.bookstore.service.ReportGenerator;
import com.ibrahim.bookstore.service.SalesManager;
import com.ibrahim.bookstore.util.CLI;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Entry point for the Bookstore Inventory Manager console application.
 * <p>
 * Initializes services and runs the main CLI loop until the user exits.
 * </p>
 */
public class Main {

    public static void main(String[] args) {
        InventoryManager invMgr = new InventoryManager();
        SalesManager salesMgr = new SalesManager();
        ReportGenerator reportGen = new ReportGenerator(invMgr, salesMgr);

        CLI.printMessage("Welcome to the Bookstore Inventory Manager!");

        while (true) {
            int choice = CLI.showMainMenu();
            switch (choice) {
                case 1:
                    // List all books
                    CLI.printMessage("\n-- All Books --");
                    for (Book b : invMgr.listAllBooks()) {
                        CLI.printMessage(String.format(
                                "%s | %s | %s | €%.2f | Qty: %d",
                                b.getIsbn(), b.getTitle(), b.getAuthor(),
                                b.getPrice(), b.getQuantity()
                        ));
                    }
                    CLI.pressEnterToContinue();
                    break;

                case 2:
                    // Add a new book
                    Book newBook = CLI.promptBookDetails();
                    invMgr.addBook(newBook);
                    CLI.printMessage("Book added successfully.");
                    CLI.pressEnterToContinue();
                    break;

                case 3:
                    // Update an existing book
                    String updIsbn = CLI.promptIsbn();
                    Book existing = invMgr.findBook(updIsbn);
                    if (existing == null) {
                        CLI.printError("No book found with ISBN " + updIsbn);
                    } else {
                        CLI.printMessage("Enter new details for this book:");
                        String newTitle = CLI.promptTitle();
                        String newAuthor = CLI.promptAuthor();
                        double newPrice = CLI.promptPrice();
                        int newQty = CLI.promptQuantity();
                        Book updated = new Book(updIsbn, newTitle, newAuthor, newPrice, newQty);
                        invMgr.updateBook(updated);
                        CLI.printMessage("Book updated successfully.");
                    }
                    CLI.pressEnterToContinue();
                    break;

                case 4:
                    // Remove a book
                    String remIsbn = CLI.promptIsbn();
                    if (invMgr.removeBook(remIsbn)) {
                        CLI.printMessage("Book removed.");
                    } else {
                        CLI.printError("No book found with ISBN " + remIsbn);
                    }
                    CLI.pressEnterToContinue();
                    break;

                case 5:
                    // Record a sale
                    String saleIsbn = CLI.promptIsbn();
                    Book saleBook = invMgr.findBook(saleIsbn);
                    if (saleBook == null) {
                        CLI.printError("No book found with ISBN " + saleIsbn);
                    } else {
                        int saleQty = CLI.promptQuantity();
                        if (saleQty > saleBook.getQuantity()) {
                            CLI.printError("Not enough stock. Available: " + saleBook.getQuantity());
                        } else {
                            salesMgr.recordSale(saleBook, saleQty);
                            // decrement inventory
                            Book postSale = new Book(
                                    saleBook.getIsbn(),
                                    saleBook.getTitle(),
                                    saleBook.getAuthor(),
                                    saleBook.getPrice(),
                                    saleBook.getQuantity() - saleQty
                            );
                            invMgr.updateBook(postSale);
                            CLI.printMessage("Sale recorded.");
                        }
                    }
                    CLI.pressEnterToContinue();
                    break;

                case 6:
                    // Generate report submenu
                    CLI.printMessage("\n-- Reports --");
                    CLI.printMessage("1) Total revenue");
                    CLI.printMessage("2) Top-selling books");
                    CLI.printMessage("3) Low-stock alert");
                    CLI.printMessage("4) Sales by date range");
                    int rptChoice = CLI.promptIntInRange("Select report (1-4):", 1, 4);
                    switch (rptChoice) {
                        case 1:
                            reportGen.printTotalSales();
                            break;
                        case 2:
                            int topN = CLI.promptQuantity();
                            reportGen.printTopSellingBooks(topN);
                            break;
                        case 3:
                            int threshold = CLI.promptQuantity();
                            reportGen.printLowStock(threshold);
                            break;
                        case 4:
                            String from = CLI.promptFilePath("start date (YYYY-MM-DD)");
                            String to   = CLI.promptFilePath("end date (YYYY-MM-DD)");
                            reportGen.printSalesByDateRange(
                                    LocalDate.parse(from),
                                    LocalDate.parse(to)
                            );
                            break;
                    }
                    CLI.pressEnterToContinue();
                    break;

                case 7:
                    // Exit application
                    CLI.printMessage("Goodbye!");
                    try {
                        String invPath = CLI.promptFilePath("inventory CSV to save");
                        invMgr.saveInventory(invPath);
                        String salesPath = CLI.promptFilePath("sales CSV to save");
                        salesMgr.saveSales(salesPath);
                        CLI.printMessage("Data saved successfully.");
                    } catch (IOException e) {
                        CLI.printError("Error saving data: " + e.getMessage());
                    }
                    System.exit(0);
                    break;
            }
        }
    }
}
