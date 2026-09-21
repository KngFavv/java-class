package com.flooring.dao;

import com.flooring.model.Order;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDaoFileImpl implements OrderDao {
    private Map<Integer, Order> orders;

    private String ORDER_FILE;
    private final String DELIMITER = ",";

    public OrderDaoFileImpl() {
        orders = new HashMap<>();
    }
    private String getOrderFileName(LocalDate date) {
        return "Data/Orders/Orders_" + date.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
    }
    private void writeFile() throws FlooringMasteryPersistenceException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(ORDER_FILE));

            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

            for (Order order : orders.values()) {
                out.println(
                        order.getOrderNumber() + DELIMITER
                                + order.getCustomerName() + DELIMITER
                                + order.getState() + DELIMITER
                                + order.getTaxRate() + DELIMITER
                                + order.getProductType() + DELIMITER
                                + order.getArea() + DELIMITER
                                + order.getCostPerSquareFoot() + DELIMITER
                                + order.getLaborCostPerSquareFoot() + DELIMITER
                                + order.getMaterialCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getLaborCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getTax().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getTotal().setScale(2, RoundingMode.HALF_UP)
                );
            }

            out.flush();
            out.close();

        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not write order file.", e);
        }
    }

    private void loadFile(LocalDate date) throws FlooringMasteryPersistenceException {
        orders.clear();

        ORDER_FILE = getOrderFileName(date);

        File file = new File(ORDER_FILE);

        if (file.exists()) {
            loadOrdersFromFile(file);
        }

        for (Order order : orders.values()) {
            order.setOrderDate(date);
        }
    }
    private void exportFile()
            throws FlooringMasteryPersistenceException {
        try {
            PrintWriter out = new PrintWriter(
                    new FileWriter("Data/Backup/Backup.txt"));

            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            for (Order order : orders.values()) {
                out.println(
                        order.getOrderNumber() + DELIMITER
                                + order.getCustomerName() + DELIMITER
                                + order.getState() + DELIMITER
                                + order.getTaxRate() + DELIMITER
                                + order.getProductType() + DELIMITER
                                + order.getArea() + DELIMITER
                                + order.getCostPerSquareFoot() + DELIMITER
                                + order.getLaborCostPerSquareFoot() + DELIMITER
                                + order.getMaterialCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getLaborCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getTax().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getTotal().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                                + order.getOrderDate().format(formatter)
                );
            }

            out.flush();
            out.close();

        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not export data.", e);
        }

    }
    private void loadAllFiles() throws FlooringMasteryPersistenceException {
        File folder = new File("Data/Orders");

        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith("Orders_")
                        && file.getName().endsWith(".txt")) {
                    loadOrdersFromFile(file);

                    String fileName = file.getName();

                    String dateText = fileName.substring(7, 15);

                    LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("MMddyyyy"));

                    for (Order order : orders.values()) {
                        order.setOrderDate(date);
                    }
                }
            }
        }
    }
    private void loadOrdersFromFile(File file) throws FlooringMasteryPersistenceException {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("OrderNumber")) {
                    continue;
                }

                String[] parts = line.split(DELIMITER);

                Order order = new Order();

                order.setOrderNumber(Integer.parseInt(parts[0]));
                order.setCustomerName(parts[1]);
                order.setState(parts[2]);
                order.setTaxRate(new BigDecimal(parts[3]));
                order.setProductType(parts[4]);
                order.setArea(new BigDecimal(parts[5]));
                order.setCostPerSquareFoot(new BigDecimal(parts[6]));
                order.setLaborCostPerSquareFoot(new BigDecimal(parts[7]));
                order.setMaterialCost(new BigDecimal(parts[8]));
                order.setLaborCost(new BigDecimal(parts[9]));
                order.setTax(new BigDecimal(parts[10]));
                order.setTotal(new BigDecimal(parts[11]));

                orders.put(order.getOrderNumber(), order);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load order file.", e);
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringMasteryPersistenceException {
        loadFile(date);
        return new ArrayList<>(orders.values());
    }

    @Override
    public int getNextOrderNumber()
            throws FlooringMasteryPersistenceException {
        int nextNumber = 1;

        for (Integer orderNumber : orders.keySet()) {
            if (orderNumber >= nextNumber) {
                nextNumber = orderNumber + 1;
            }
        }

        return nextNumber;
    }

    @Override
    public void addOrder(Order order) throws FlooringMasteryPersistenceException {
        ORDER_FILE = getOrderFileName(order.getOrderDate());
        orders.put(order.getOrderNumber(), order);
        writeFile();
    }

    @Override
    public void editOrder(Order order) throws FlooringMasteryPersistenceException {
        orders.put(order.getOrderNumber(), order);
        ORDER_FILE = getOrderFileName(order.getOrderDate());
        writeFile();
    }

    @Override
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException {
        orders.remove(order.getOrderNumber());
        ORDER_FILE = getOrderFileName(order.getOrderDate());
        writeFile();
    }
    @Override
    public void exportAllData() throws FlooringMasteryPersistenceException {
        orders.clear();
        loadAllFiles();
        exportFile();
    }
}

