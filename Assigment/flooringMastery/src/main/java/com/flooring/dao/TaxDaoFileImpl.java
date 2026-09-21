package com.flooring.dao;



import com.flooring.model.Tax;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class TaxDaoFileImpl implements TaxDao {

    private Map<String, Tax> allTaxes;
    private  String TAX_FILE = "Data/Data/Taxes.txt";
    private  String DELIMITER = ",";

    public TaxDaoFileImpl() {
        allTaxes = new HashMap<>();
    }

    private void loadFile() {
        try{
            Scanner scanner = new Scanner(new File(TAX_FILE));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(DELIMITER);

                Tax tax = new Tax();

                tax.setStateAb(parts[0]);
                tax.setState(parts[1]);
                tax.setTaxRate(new BigDecimal(parts[2]));

                allTaxes.put(tax.getStateAb(), tax);

            }

            scanner.close();

        } catch (FileNotFoundException e) {


        }

    }

    @Override
    public List<Tax> getAllTaxes() {
        loadFile();
        return new ArrayList<>(allTaxes.values());
    }
}