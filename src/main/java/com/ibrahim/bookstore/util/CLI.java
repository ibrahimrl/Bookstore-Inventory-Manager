package com.ibrahim.bookstore.util;

import com.ibrahim.bookstore.model.Book;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Command Line Interface (CLI) utility for interacting with the user.
 * <p>
 * Provides methods to display menus, prompt for input, validate data,
 * and show messages or errors in a console-based application.
 * <p>
 * All methods in this class are static and thread-safe for simple console use.
 * Use {@code CLI.prompt...} methods to gather user input with built-in
 * validation loops until valid data is provided.
 */
public class CLI {
    /** Prevent instantiation. */
    private CLI() { throw new AssertionError("Cannot instantiate CLI"); }

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu options to the user and returns the selected choice.
     *
     * @return the integer menu choice between 1 and 7 inclusive
     */
    public static int showMainMenu() {
        printMessage("\n=== Bookstore Inventory Manager ===");
        printMessage("1) List all books");
        printMessage("2) Add a new book");
        printMessage("3) Update an existing book");
        printMessage("4) Remove a book");
        printMessage("5) Record a sale");
        printMessage("6) Generate report");
        printMessage("7) Exit");
        return promptIntInRange("Enter your choice (1-7):", 1, 7);
    }

    /**
     * Prompts the user to enter an ISBN string until a non-empty value is provided.
     *
     * @return the entered ISBN, guaranteed non-empty
     */
    public static String promptIsbn() {
        String isbn;
        do {
            printMessage("Enter ISBN:");
            isbn = scanner.nextLine().trim();
            if (isbn.isEmpty()) {
                printError("ISBN cannot be empty. Please try again.");
            }
        } while (isbn.isEmpty());
        return isbn;
    }

    /**
     * Prompts the user to enter a book title until a non-empty value is provided.
     *
     * @return the entered title, guaranteed non-empty
     */
    public static String promptTitle() {
        String title;
        do {
            printMessage("Enter title:");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                printError("Title cannot be empty. Please try again.");
            }
        } while (title.isEmpty());
        return title;
    }

    /**
     * Prompts the user to enter an author name until a non-empty value is provided.
     *
     * @return the entered author name, guaranteed non-empty
     */
    public static String promptAuthor() {
        String author;
        do {
            printMessage("Enter author:");
            author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                printError("Author cannot be empty. Please try again.");
            }
        } while (author.isEmpty());
        return author;
    }

    /**
     * Prompts the user to enter a positive double value for price.
     * Accepts input until a valid non-negative double is entered.
     *
     * @return the entered price
     */
    public static double promptPrice() {
        double price = -1;
        while (price < 0) {
            printMessage("Enter price (e.g. 12.99):");
            try {
                String line = scanner.nextLine().trim();
                price = Double.parseDouble(line);
                if (price < 0) {
                    printError("Price cannot be negative. Try again.");
                }
            } catch (NumberFormatException e) {
                printError("Invalid price format. Please enter a number.");
            }
        }
        return price;
    }

    /**
     * Prompts the user to enter a non-negative integer for quantity.
     * Accepts input until a valid integer is entered.
     *
     * @return the entered quantity
     */
    public static int promptQuantity() {
        int qty = -1;
        while (qty < 0) {
            printMessage("Enter quantity (integer):");
            try {
                String line = scanner.nextLine().trim();
                qty = Integer.parseInt(line);
                if (qty < 0) {
                    printError("Quantity cannot be negative. Try again.");
                }
            } catch (NumberFormatException e) {
                printError("Invalid quantity format. Please enter an integer.");
            }
        }
        return qty;
    }

    /**
     * Prompts the user for an integer within a specified range.
     * Keeps prompting until a valid integer is entered.
     *
     * @param prompt the message to display
     * @param min    minimum acceptable value
     * @param max    maximum acceptable value
     * @return the entered integer between min and max inclusive
     */
    public static int promptIntInRange(String prompt, int min, int max) {
        int choice = min - 1;
        while (choice < min || choice > max) {
            printMessage(prompt);
            try {
                String line = scanner.nextLine().trim();
                choice = Integer.parseInt(line);
                if (choice < min || choice > max) {
                    printError("Choice must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                printError("Invalid number. Please enter an integer.");
            }
        }
        return choice;
    }

    /**
     * Prompts the user with a yes/no question and returns the result.
     * Accepts 'y', 'yes', 'n', 'no' (case-insensitive).
     *
     * @param prompt the confirmation message
     * @return true if user answers yes, false otherwise
     */
    public static boolean promptYesNo(String prompt) {
        String input;
        while (true) {
            printMessage(prompt + " (y/n):");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            printError("Please enter 'y' or 'n'.");
        }
    }

    /**
     * Prompts the user to enter a file path.
     *
     * @param description a description of what the file path is for
     * @return the entered file path string
     */
    public static String promptFilePath(String description) {
        printMessage("Enter file path for " + description + ":");
        return scanner.nextLine().trim();
    }

    /**
     * Prints an informational message to the console.
     *
     * @param message the message to display
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints an error message to the console.
     *
     * @param error the error message to display
     */
    public static void printError(String error) {
        System.err.println("Error: " + error);
    }

    /**
     * Prompts the user to press Enter to continue.
     */
    public static void pressEnterToContinue() {
        printMessage("Press Enter to continue...");
        try {
            scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            // ignore
        }
    }

    /**
     * Prompts the user to input full book details and constructs a Book object.
     * <p>
     * This method calls individual prompts for ISBN, title, author, price,
     * and quantity in order, then returns the newly created Book.
     * </p>
     *
     * @return a Book instantiated with user-provided details
     */
    public static Book promptBookDetails() {
        printMessage("\n-- Enter New Book Details --");
        String isbn = promptIsbn();
        String title = promptTitle();
        String author = promptAuthor();
        double price = promptPrice();
        int quantity = promptQuantity();
        return new Book(isbn, title, author, price, quantity);
    }
}
