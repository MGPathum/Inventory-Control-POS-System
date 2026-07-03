# Inventory Control System — Desktop POS

A desktop-based **Point of Sale (POS) and Inventory Management System** built with Java Swing. Designed as a university Systems Analysis & Design (SAD) assignment, it covers the full lifecycle of a retail business: user authentication, product & category management, supplier tracking, sales invoicing, purchase orders, and reporting.

---

## Screenshots

| Login | Dashboard | POS Counter |
|-------|-----------|-------------|
| ![Login](Images/Login%20Screen.png) | ![Dashboard](Images/Dashboard.png) | ![POS](Images/POS%20Counter.png) |

| CRUD Operations | Reports | Database Tables |
|-----------------|---------|-----------------|
| ![CRUD](Images/CRUD%20Operations.png) | ![Reports](Images/Reports.png) | ![DB](Images/Database%20tables.png) |

---

## Features

- **Secure Login** — bcrypt-hashed passwords, role-based access
- **Dashboard** — at-a-glance KPIs and low-stock alerts
- **Product & Category Management** — full CRUD with barcode generation (ZXing)
- **Supplier Management** — track suppliers linked to purchase orders
- **POS Counter** — fast sales entry, real-time stock deduction
- **Purchase Orders** — record stock replenishment from suppliers
- **Reports** — sales, inventory, and purchase reports via JasperReports (PDF & Excel export)
- **Logging** — application-wide logging with Apache Log4j 2

---

## Technology Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 25 |
| UI Framework | Java Swing (Nimbus L&F) |
| Build Tool | Apache Maven 3 |
| Database | MySQL 8 |
| DB Connectivity | JDBC — MySQL Connector/J 8.3.0 |
| Reporting | JasperReports 6.21.3 |
| Excel Export | Apache POI 5.2.5 |
| PDF Generation | Apache PDFBox 3.0.3 |
| Barcode | ZXing (Google) 3.5.3 |
| Password Hashing | jBCrypt 0.4 |
| Logging | Apache Log4j 2.23.1 |
| Testing | JUnit 5.10.2 |
| IDE | Apache NetBeans |

---

## Project Structure

```
InventoryControlSystem/
├── src/main/java/com/mycompany/inventorycontrolsystem/
│   ├── InventoryControlSystem.java   # Application entry point
│   ├── controller/                   # Business logic controllers
│   ├── dao/                          # Data Access Object interfaces
│   │   └── impl/                     # DAO implementations (JDBC)
│   ├── db/                           # Database connection & DAO factory
│   ├── model/                        # Plain Java model/entity classes
│   └── view/                         # Swing UI frames and panels
├── Images/                           # Screenshots
├── pom.xml                           # Maven dependencies & build config
└── README.md
```

---

## Prerequisites

Before running the project, make sure you have:

- **JDK 25** or later — [Download here](https://jdk.java.net/)
- **Apache Maven 3.9+** — [Download here](https://maven.apache.org/download.cgi)
- **MySQL Server 8.0+** — [Download here](https://dev.mysql.com/downloads/mysql/)
- **Apache NetBeans 22+** (recommended IDE) — [Download here](https://netbeans.apache.org/)

---

## Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/Inventory-Control-System.git
cd Inventory-Control-System
```

### 2. Create the MySQL database

Open MySQL Workbench or the MySQL command line and run:

```sql
CREATE DATABASE inventory_db;
USE inventory_db;
-- Then import your schema SQL file if you have one, e.g.:
-- SOURCE schema.sql;
```

### 3. Configure the database connection

Open the file:

```
src/main/java/com/mycompany/inventorycontrolsystem/db/DatabaseConnection.java
```

Update the constants with your local MySQL credentials:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/inventory_db";
private static final String USER     = "root";       // your MySQL username
private static final String PASSWORD = "yourpassword"; // your MySQL password
```

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn exec:java
```

Or open the project in **NetBeans** and press the **Run** button (F6).

---

## Default Login

> Update these credentials in your database after first run.

| Field | Value |
|-------|-------|
| Username | `admin` |
| Password | `admin123` |

---

## License

This project was created for academic purposes as part of a university Systems Analysis & Design (SAD) assignment.

---

## Author

**[Your Name]**  
University SAD Assignment — 2025
