package com.dynamicpricing.dto;


import lombok.Data;

@Data
public class PricePredictionRequest {
    private double basePrice;
    private double margin;  // e.g., 0.2 for 20%
    private int inventoryLevel;
    private double competitorPrice;
    private double demandLevel;  // e.g., 1.0 (high), 0.5 (medium), 0.2 (low)

    // Getters and Setters

    public double getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(double demandLevel) {
        this.demandLevel = demandLevel;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public int getInventoryLevel() {
        return inventoryLevel;
    }

    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    public double getCompetitorPrice() {
        return competitorPrice;
    }

    public void setCompetitorPrice(double competitorPrice) {
        this.competitorPrice = competitorPrice;
    }
}
