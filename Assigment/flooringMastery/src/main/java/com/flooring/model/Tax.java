package com.flooring.model;

import java.math.BigDecimal;

public class Tax {
    private String state;
    private String stateAbr;
    private BigDecimal taxRate;

    public String getState(){
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAb(){
        return stateAbr;
    }

    public void setStateAb(String stateAbr) {
        this.stateAbr = stateAbr;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
