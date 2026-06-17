package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class NorthwindTradersApp{
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup26";
        // try-with-resources automatically closes the connection when done
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected!");
            Scanner scanner = new Scanner(System.in);
            int choice = -1;
            // Keep showing the menu until user selects 0 to exit
            while (choice != 0) {
                // Display the home screen menu
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                choice = scanner.nextInt();
                if (choice == 1) {
                    // Query the products table
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products");
                    // Print each product in stacked format
                    while (results.next()) {
                        System.out.println("Product Id: " + results.getInt("ProductID"));
                        System.out.println("Name:       " + results.getString("ProductName"));
                        System.out.println("Price:      " + results.getDouble("UnitPrice"));
                        System.out.println("Stock:      " + results.getInt("UnitsInStock"));
                        System.out.println("------------------");
                    }
                } else if (choice == 2) {
                    // Query the customers table, ordered by country
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SELECT ContactName, CompanyName, City, Country, Phone FROM customers ORDER BY Country");
                    // Print each customer in stacked format
                    while (results.next()) {
                        System.out.println("Contact:  " + results.getString("ContactName"));
                        System.out.println("Company:  " + results.getString("CompanyName"));
                        System.out.println("City:     " + results.getString("City"));
                        System.out.println("Country:  " + results.getString("Country"));
                        System.out.println("Phone:    " + results.getString("Phone"));
                        System.out.println("------------------");
                    }
                } else if (choice == 3) {

                    // No user input in this query, so regular Statement is safe here
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(
                            "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID"
                    );

                    System.out.println("\n--- All Categories ---");
                    while (results.next()) {
                        System.out.println("Category ID:   " + results.getInt("CategoryID"));
                        System.out.println("Category Name: " + results.getString("CategoryName"));
                        System.out.println("------------------");
                        // Ask the user which category they want to filter by
                        System.out.print("\nEnter a Category ID to see its products: ");
                        int categoryId = scanner.nextInt();
                    }
                } else if (choice == 0) {
                    // User chose to exit
                    System.out.println("Goodbye!");
                }
            }
        } catch (SQLException e) {
            // Print any database errors
            e.printStackTrace();
        }
    }
}