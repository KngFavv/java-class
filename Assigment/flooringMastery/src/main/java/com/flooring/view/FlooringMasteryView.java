package com.flooring.view;

import com.flooring.model.Order;
import com.flooring.model.Product;
import com.flooring.model.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringMasteryView {
    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    public int printMenuAndGetSelection() {

        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.");
    }
    public LocalDate getOrderDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        while (true) {
            String date = io.readString("Enter order date (MM/dd/yyyy):");

            try {
                LocalDate orderDate = LocalDate.parse(date, formatter);

                if (!orderDate.isAfter(LocalDate.now())) {
                    io.print("Order date must be in the future.");
                } else {
                    return orderDate;
                }
            } catch (Exception e) {
                io.print("Invalid date. Please try again.");
            }
        }
    }
    public void displayBanner(){
        System.out.println("=================================================================");
    }
    public void displayErrorMessage(String message) {
        io.print(message);
    }
    public void displayOrders(List<Order> orders) {

        displayBanner();
        for (Order order : orders) {
            io.print("Order Number: " + order.getOrderNumber());
            io.print("Customer Name: " + order.getCustomerName());
            io.print("State: " + order.getState());
            io.print("Tax Rate: " + order.getTaxRate());
            io.print("Product Type: " + order.getProductType());
            io.print("Area: " + order.getArea());
            io.print("Cost Per Square Foot: " + order.getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP));
            io.print("Labor Cost Per Square Foot: " + order.getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP));
            io.print("Material Cost: " + order.getMaterialCost().setScale(2, RoundingMode.HALF_UP));
            io.print("Labor Cost: " + order.getLaborCost().setScale(2, RoundingMode.HALF_UP));
            io.print("Tax: " + order.getTax().setScale(2, RoundingMode.HALF_UP));
            io.print("Total: " + order.getTotal().setScale(2, RoundingMode.HALF_UP));
            displayBanner();
        }
    }
    public void displayOrderSummary(Order order) {
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Material Cost: " + order.getMaterialCost().setScale(2, RoundingMode.HALF_UP));
        io.print("Labor Cost: " + order.getLaborCost().setScale(2, RoundingMode.HALF_UP));
        io.print("Tax: " + order.getTax().setScale(2, RoundingMode.HALF_UP));
        io.print("Total: " + order.getTotal().setScale(2, RoundingMode.HALF_UP));
    }
    public void displaySuccessMessage() {
        io.print("Order has been saved successfully.");
    }
    public String getCustomerName() {
        while (true) {
            String customerName = io.readString("Enter customer name:");

            if (customerName.trim().isEmpty()) {
                io.print("Customer name cannot be blank.");
            } else if (!customerName.matches("[a-zA-Z0-9., ]+")) {
                io.print("Customer name contains invalid characters.");
            } else {
                return customerName;
            }
        }
    }

    public String getState(List<Tax> taxes) {
        while (true) {
            String state = io.readString("Enter state abbreviation:");

            for (Tax tax : taxes) {
                if (tax.getStateAb().equalsIgnoreCase(state)) {
                    return state;
                }
            }

            io.print("Invalid state. Please try again.");
        }
    }

    public String getProductType(List<Product> products) {
        while (true) {
            String productType = io.readString("Enter product type:");

            for (Product product : products) {
                if (product.getProductType().equalsIgnoreCase(productType)) {
                    return productType;
                }
            }

            io.print("Invalid product type. Please try again.");
        }
    }
    public BigDecimal getArea() {
        while (true) {
            String area = io.readString("Enter area in square feet:");

            try {
                BigDecimal value = new BigDecimal(area);

                if (value.compareTo(new BigDecimal("100")) < 0) {
                    io.print("Area must be at least 100 square feet.");
                } else {
                    return value;
                }

            } catch (Exception e) {
                io.print("Invalid area. Please enter a number.");
            }
        }
    }
    public String getConfirmation() {
        while (true) {
            String confirmation =
                    io.readString("Would you like to save this order? (Y/N):");

            if (confirmation.equalsIgnoreCase("Y")
                    || confirmation.equalsIgnoreCase("N")) {
                return confirmation;
            }

            io.print("Please enter Y or N.");
        }

    }

    public int getOrderNumber() {
        return io.readInt("Enter order number:");
    }

    public String getEditCustomerName(String currentName) {
       while (true) {
            String customerName = io.readString(
                    "Enter customer name (" + currentName + ") or press Enter to keep:");

            if (customerName.trim().isEmpty()) {
                return "";
            }

            if (!customerName.matches("[a-zA-Z0-9., ]+")) {
                io.print("Customer name contains invalid characters.");
            } else {
                return customerName;
            }
        }
    }
    public String getEditState(String currentState, List<Tax> taxes) {
        while (true) {
            String state = io.readString(
                    "Enter state (" + currentState + ") or press Enter to keep:");

            if (state.trim().isEmpty()) {
                return "";
            }

            for (Tax tax : taxes) {
                if (tax.getStateAb().equalsIgnoreCase(state)) {
                    return state;
                }
            }

            io.print("Invalid state. Please try again.");
        }
    }
    public String getEditProductType(String currentProduct, List<Product> products) {
        while (true) {
            String productType = io.readString(
                    "Enter product type (" + currentProduct + ") or press Enter to keep:");

            if (productType.trim().isEmpty()) {
                return "";
            }

            for (Product product : products) {
                if (product.getProductType().equalsIgnoreCase(productType)) {
                    return productType;
                }
            }

            io.print("Invalid product type. Please try again.");
        }
    }
    public String getEditArea(String currentArea) {
        while (true) {
            String area = io.readString(
                    "Enter area (" + currentArea + ") or press Enter to keep:");

            if (area.trim().isEmpty()) {
                return "";
            }

            try {
                BigDecimal value = new BigDecimal(area);

                if (value.compareTo(new BigDecimal("100")) < 0) {
                    io.print("Area must be at least 100 square feet.");
                } else {
                    return area;
                }

            } catch (Exception e) {
                io.print("Invalid area. Please enter a number.");
            }
        }
    }
    public String getRemoveConfirmation() {
        while (true) {
            String confirmation =
                    io.readString("Are you sure you want to remove this order? (Y/N):");

            if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("N")) {
                return confirmation;
            }

            io.print("Please enter Y or N.");
        }
    }
}
