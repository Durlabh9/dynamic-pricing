//package com.dynamicpricing.scheduler;
//
//import com.dynamicpricing.service.ProductPriceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PriceUpdateScheduler {
//
//    @Autowired
//    private ProductPriceService productPriceService;
//
//    // Scheduled job to periodically update prices every 5 minutes
//    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
//    public void updatePrices() {
//        // Assuming we update prices for multiple products
//        // In a real scenario, you'd iterate over all products and update their prices
//        String[] productNames = {"Product1", "Product2", "Product3"};
//        for (String productName : productNames) {
//            productPriceService.calculateOptimalPrice(productName);
//        }
//    }
//}
