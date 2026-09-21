package com.flooring.dao;


import com.flooring.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao {

    private Map<String, Product> allProducts;
    private final String PRODUCT_FILE = "Data/Products.txt";
    private final String DELIMITER = ",";

    public ProductDaoFileImpl() {
        allProducts = new HashMap<>();
    }

    private void loadFile() {

        try {
            Scanner scanner = new Scanner(new File(PRODUCT_FILE));

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] parts = line.split(DELIMITER);

                Product product = new Product();

                product.setProductType(parts[0]);
                product.setCostPerSquareFoot(new BigDecimal(parts[1]));
                product.setLaborCostPerSquareFoot(new BigDecimal(parts[2]));

                allProducts.put(product.getProductType(), product);
            }

            scanner.close();

        } catch (FileNotFoundException e) {

        }
    }

    @Override
    public List<Product> getAllProducts() {
        loadFile();
        return new ArrayList<>(allProducts.values());    }
}
