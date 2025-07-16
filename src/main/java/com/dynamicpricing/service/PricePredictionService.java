package com.dynamicpricing.service;

import org.springframework.stereotype.Service;

@Service
public class PricePredictionService {

    public double predictPrice(double basePrice, double margin, int inventory, double competitorPrice, double demandLevel) {
        double minimumAcceptablePrice = basePrice * 1.05;  // Always 5% profit minimum
        double targetPrice = basePrice + (basePrice * margin);  // Desired profit

        // Adjust based on inventory
        if (inventory > 200) {
            targetPrice *= 0.9;
        } else if (inventory > 100) {
            targetPrice *= 0.95;
        }



        if (demandLevel > 0.8) {
            targetPrice *= 1.05;  // High demand: increase price
        } else if (demandLevel < 0.4) {
            targetPrice *= 0.9;   // Low demand: decrease price
        }

        // Round price nicely
        targetPrice = Math.round(targetPrice / 5.0) * 5;

        // Calculate dynamic undercut amount
        double undercutAmount = getUndercutAmount(competitorPrice);
        double undercutPrice = competitorPrice - undercutAmount;

        // Decide final price
        if (targetPrice > competitorPrice) {
            if (undercutPrice >= minimumAcceptablePrice) {
                targetPrice = undercutPrice;  // Undercut but still profitable
            } else {
                targetPrice = minimumAcceptablePrice;  // Can't undercut, stick to min 5% profit
            }
        }

        // Ensure we never price below base cost
        if (targetPrice < basePrice) {
            targetPrice = minimumAcceptablePrice;
        }

        return targetPrice;
    }

    private double getUndercutAmount(double competitorPrice) {
        if (competitorPrice < 2000) return 50;
        else if (competitorPrice < 5000) return 100;
        else if (competitorPrice < 20000) return 200;
        else if (competitorPrice < 50000) return 500;
        else return 1000;
    }
}
