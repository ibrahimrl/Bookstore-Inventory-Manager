# Bookstore Inventory Manager

A Maven-based Java 17 console application for managing a bookstore’s inventory and sales.  
Everything is stored in memory at runtime, with optional CSV import/export for persistence.

---

## Description

The **Bookstore Inventory Manager** lets you:

- Maintain a catalog of books (ISBN, title, author, price, quantity)
- Record sale transactions (tracking timestamped sales)
- Persist inventory and sales data to/from CSV files
- Generate console reports: total revenue, top-selling books, low-stock alerts, and sales by date range

All business logic is encapsulated in service classes; the user interacts via a robust CLI utility.

---

## Features

- **CRUD** on books: list, add, update, remove
- **Record sales** and automatically adjust stock
- **CSV import/export** for inventory & sales
- **Reports**
    - Total revenue
    - Top-N selling titles
    - Low-stock alerts
    - Sales within a date range
- Fully tested (JUnit 5), with 100% green build and generated Javadoc

---

## Dependencies

- **Java 17**
- **Maven 3.6+**
- **JUnit 5.9.2** (test scope)

All dependencies are managed in `pom.xml`.

---

## Installation

1. **Clone** the repo
```bash
git clone https://github.com/ibrahimrl/Bookstore-Inventory-Manager.git
cd Bookstore-Inventory-Manager
```

2. **Build** with **Maven**
```bash
mvn clean package
```

## Menus

On startup you’ll see:

```bash
=== Bookstore Inventory Manager ===
1) List all books
2) Add a new book
3) Update an existing book
4) Remove a book
5) Record a sale
6) Generate report
7) Exit
```

Reports submenu (Option 6):

```bash
1) Total revenue
2) Top-selling books
3) Low-stock alert
4) Sales by date range
```

## Directory Layout

`src/main/java` – production code

`src/test/java` – unit tests

`target/` – build output (classes, JAR, Javadoc, test reports)

## License

This project is licensed under the [MIT License](LICENSE).