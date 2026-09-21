package com.flooring.service;

import com.flooring.dao.FlooringMasteryPersistenceException;
import com.flooring.model.Order;
import com.flooring.model.Product;
import com.flooring.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryServiceLayer {
    List<Product> getAllProducts();

    List<Tax> getAllTaxes();

    List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException;

    int getNextOrderNumber() throws FlooringMasteryPersistenceException;

    void addOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException;

    void editOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException;

    void removeOrder(Order order) throws FlooringMasteryPersistenceException;

    void exportAllData() throws FlooringMasteryPersistenceException;

}
