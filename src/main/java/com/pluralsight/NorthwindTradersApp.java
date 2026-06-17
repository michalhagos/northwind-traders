package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class NorthwindTradersApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup26";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products");
            while (results.next()) {
                System.out.println("Product Id: " + results.getInt("ProductID"));
                System.out.println("Name:       " + results.getString("ProductName"));
                System.out.println("Price:      " + results.getDouble("UnitPrice"));
                System.out.println("Stock:      " + results.getInt("UnitsInStock"));
                System.out.println("------------------");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}