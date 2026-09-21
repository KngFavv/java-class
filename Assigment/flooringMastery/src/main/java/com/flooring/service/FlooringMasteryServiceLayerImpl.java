package com.flooring.service;

import com.flooring.dao.FlooringMasteryPersistenceException;
import com.flooring.dao.OrderDao;
import com.flooring.dao.ProductDao;
import com.flooring.dao.TaxDao;
import com.flooring.model.Order;
import com.flooring.model.Product;
import com.flooring.model.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private OrderDao orderDao;
    private TaxDao taxDao;
    private ProductDao productDao;

    public FlooringMasteryServiceLayerImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }
    private void validateOrder(Order order) throws FlooringMasteryDataValidationException {

        if (order.getOrderDate() == null || !order.getOrderDate().isAfter(LocalDate.now())) {
            throw new FlooringMasteryDataValidationException("Order date must be in the future.");
        }
        if (order.getCustomerName() == null || order.getCustomerName().trim().isEmpty()) {
            throw new FlooringMasteryDataValidationException("Customer name cannot be blank.");
        }

        if (order.getArea().compareTo(new BigDecimal("100")) < 0) {
            throw new FlooringMasteryDataValidationException("Area must be at least 100 square feet.");
        }

        if (order.getState() == null || order.getState().trim().isEmpty()) {
            throw new FlooringMasteryDataValidationException("State cannot be blank.");
        }

        boolean validState = false;

        List<Tax> taxes = taxDao.getAllTaxes();

        for (Tax tax : taxes) {
            if (tax.getStateAb().equalsIgnoreCase(order.getState())) {
                validState = true;
                break;
            }
        }

        if (!validState) {
            throw new FlooringMasteryDataValidationException("Invalid state.");
        }

        boolean validProduct = false;

        List<Product> products = productDao.getAllProducts();

        for (Product product : products) {
            if (product.getProductType()
                    .equalsIgnoreCase(order.getProductType())) {
                validProduct = true;
                break;
            }
        }

        if (!validProduct) {
            throw new FlooringMasteryDataValidationException("Invalid product type.");
        }
    }
    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
    @Override
    public List<Tax> getAllTaxes() {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date)
            throws FlooringMasteryPersistenceException {
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public int getNextOrderNumber() throws FlooringMasteryPersistenceException {
        return orderDao.getNextOrderNumber();
    }

    @Override
    public void addOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {

        validateOrder(order);
        orderDao.addOrder(order);
    }

    @Override
    public void editOrder(Order order) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {

        validateOrder(order);
        orderDao.editOrder(order);
    }

    @Override
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException {
        orderDao.removeOrder(order);
    }
    @Override
    public void exportAllData() throws FlooringMasteryPersistenceException {
        orderDao.exportAllData();
    }
}
