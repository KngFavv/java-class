package com.flooring.controller;

import com.flooring.dao.FlooringMasteryPersistenceException;
import com.flooring.model.Order;
import com.flooring.model.Product;
import com.flooring.model.Tax;
import com.flooring.service.FlooringMasteryDataValidationException;
import com.flooring.service.FlooringMasteryServiceLayer;
import com.flooring.view.FlooringMasteryView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryController {

    private FlooringMasteryServiceLayer service;
    private FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        int menuSelection = 0;

        while (menuSelection != 6) {




        menuSelection = view.printMenuAndGetSelection();

        switch (menuSelection) {

            case 1:
                // Display Orders
                displayOrders();
                break;

            case 2:
                // Add an Order
                addOrders();
                break;

            case 3:
                // Edit an Order
                editOrder();
                break;

            case 4:
                // Remove an Order
                removeOrder();
                break;

            case 5:
                // Export All Data
                exportAllData();
                break;

            case 6:
                // Quit
                break;

            default:

                break;
        }
    }
        }

    private void displayOrders() {
        view.displayBanner();
        //get order date
        LocalDate date = view.getOrderDate();
        try{
        //get it  from service
        List<Order> orders = service.getOrdersByDate(date);

        //if order isnt there
        if (orders.isEmpty()) {
            view.displayErrorMessage("No orders found for that date.");
            return;
        }

        //display order

        view.displayOrders(orders);
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.displayBanner();
    }

    private void addOrders(){
        view.displayBanner();
        //date
        LocalDate date = view.getOrderDate();

        //name
        String customerName = view.getCustomerName();


        //state tax

        List<Tax> taxes = service.getAllTaxes();
        String state = view.getState(taxes);

        Tax selectedTax = null;

        for (Tax tax : taxes) {
            if (tax.getStateAb().equalsIgnoreCase(state)) {
                selectedTax = tax;
                break;
            }
        }
        BigDecimal taxRate = selectedTax.getTaxRate();

        //product
        List<Product> products = service.getAllProducts();
        String productType = view.getProductType(products);


        Product selectedProduct = null;

        for (Product product : products) {
            if (product.getProductType().equalsIgnoreCase(productType)) {
                selectedProduct = product;
                break;
            }
        }


        //area
        BigDecimal costPerSquareFoot = selectedProduct.getCostPerSquareFoot();

        BigDecimal laborCostPerSquareFoot =
                selectedProduct.getLaborCostPerSquareFoot();

        BigDecimal area = view.getArea();

        //area x costsquare
        BigDecimal materialCost = area.multiply(costPerSquareFoot);
        //area x labourcost
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
        //(material + labour) x (tax/100)
        BigDecimal tax = materialCost.add(laborCost).multiply(taxRate.divide(new BigDecimal("100")));
        //material + labour + tax
        BigDecimal total = materialCost.add(laborCost).add(tax);

        //set values
        Order order = new Order();

        order.setOrderDate(date);
        order.setCustomerName(customerName);
        order.setState(state);
        order.setTaxRate(taxRate);
        order.setProductType(productType);
        order.setArea(area);
        order.setCostPerSquareFoot(costPerSquareFoot);
        order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

        int orderNumber;

        try {
            service.getOrdersByDate(date);
            orderNumber = service.getNextOrderNumber();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
        order.setOrderNumber(orderNumber);

        //saving orders
        boolean keepGoing = true;

        do {
            view.displayOrderSummary(order);

            String confirmation = view.getConfirmation();

            if (confirmation.equalsIgnoreCase("Y")) {
                try {
                    service.addOrder(order);
                    view.displaySuccessMessage();
                    keepGoing = false;

                } catch (FlooringMasteryDataValidationException e) {
                    view.displayErrorMessage(e.getMessage());

                } catch (FlooringMasteryPersistenceException e) {
                    view.displayErrorMessage(e.getMessage());
                    keepGoing = false;
                }

            } else {
                view.displayErrorMessage("Order was not saved.");
                keepGoing = false;
            }

        } while (keepGoing);
    }
    private void editOrder() {
        view.displayBanner();
        //get date and order number
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();

        try{
        //ask service for orders on date
        List<Order> orders = service.getOrdersByDate(date);

        //search through list we got from the service
        Order selectedOrder = null;

        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                selectedOrder = order;
                break;
            }
        }
        //if order doesnt exist
        if (selectedOrder == null) {
            view.displayErrorMessage("No order found with that order number.");
            return;
        }

        //display the order picked
        view.displayOrderSummary(selectedOrder);

        //edit customer name
        String newCustomerName = view.getEditCustomerName(selectedOrder.getCustomerName());
        if (!newCustomerName.trim().isEmpty()) {
            selectedOrder.setCustomerName(newCustomerName);
        }
        //edit state
        List<Tax> taxes = service.getAllTaxes();

        String newState = view.getEditState(selectedOrder.getState(), taxes);

        if (!newState.trim().isEmpty()) {

            for (Tax tax : taxes) {
                if (tax.getStateAb().equalsIgnoreCase(newState)) {
                    selectedOrder.setState(newState);
                    selectedOrder.setTaxRate(tax.getTaxRate());
                    break;
                }
            }
        }

        //edit product type
        List<Product> products = service.getAllProducts();
        String newProductType = view.getEditProductType(selectedOrder.getProductType(), products);



        if (!newProductType.trim().isEmpty()) {

            for (Product product : products) {
                if (product.getProductType().equalsIgnoreCase(newProductType)) {
                    selectedOrder.setProductType(newProductType);
                    selectedOrder.setCostPerSquareFoot(
                            product.getCostPerSquareFoot());
                    selectedOrder.setLaborCostPerSquareFoot(
                            product.getLaborCostPerSquareFoot());
                    break;
                }
            }
        }

        //edit area
        String newArea = view.getEditArea(selectedOrder.getArea().toString());

        if (!newArea.trim().isEmpty()) {
            selectedOrder.setArea(new BigDecimal(newArea));
        }
        // Recalculate costs
        BigDecimal materialCost = selectedOrder.getArea().multiply(selectedOrder.getCostPerSquareFoot());

        BigDecimal laborCost = selectedOrder.getArea().multiply(selectedOrder.getLaborCostPerSquareFoot());

        BigDecimal tax = materialCost.add(laborCost).multiply(selectedOrder.getTaxRate().divide(new BigDecimal("100")));

        BigDecimal total = materialCost.add(laborCost).add(tax);

        selectedOrder.setMaterialCost(materialCost);
        selectedOrder.setLaborCost(laborCost);
        selectedOrder.setTax(tax);
        selectedOrder.setTotal(total);

        //show updated order
        view.displayOrderSummary(selectedOrder);

        //ask to save
        String confirmation = view.getConfirmation();

        if (confirmation.equalsIgnoreCase("Y")) {
            selectedOrder.setOrderDate(date);

            try {
                service.editOrder(selectedOrder);
                view.displaySuccessMessage();

            } catch (FlooringMasteryDataValidationException e) {
                view.displayErrorMessage(e.getMessage());

            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }

        } else {
            view.displayErrorMessage("Order was not saved.");
        }
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.displayBanner();
    }

    private void removeOrder() {
        view.displayBanner();
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();

        try {

        List<Order> orders = service.getOrdersByDate(date);

        Order selectedOrder = null;

        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                selectedOrder = order;
                break;
            }
        }

        if (selectedOrder == null) {
            view.displayErrorMessage("No order found with that order number.");
            return;
        }

        view.displayOrderSummary(selectedOrder);

        String confirmation = view.getRemoveConfirmation();

        if (confirmation.equalsIgnoreCase("Y")) {
            selectedOrder.setOrderDate(date);

            try {
                service.removeOrder(selectedOrder);
                view.displaySuccessMessage();

            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }

        } else {
            view.displayErrorMessage("Order was not removed.");
        }

        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.displayBanner();


    }

    private void exportAllData() {
        view.displayBanner();
        try {
            service.exportAllData();
            view.displaySuccessMessage();

        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

        view.displayBanner();
    }

}



