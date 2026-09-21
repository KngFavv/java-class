package com.flooring.dao;

import com.flooring.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException;

    int getNextOrderNumber() throws FlooringMasteryPersistenceException;

    void addOrder(Order order) throws FlooringMasteryPersistenceException;

    void editOrder(Order order) throws FlooringMasteryPersistenceException;

    void removeOrder(Order order) throws FlooringMasteryPersistenceException;

    void exportAllData() throws FlooringMasteryPersistenceException;
}
