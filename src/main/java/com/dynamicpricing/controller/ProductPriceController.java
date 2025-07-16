package com.dynamicpricing.controller;

import com.dynamicpricing.model.ProductPrice;
import com.dynamicpricing.service.ProductPriceService;
import org.springframework.web.bind.annotation.*;
import com.dynamicpricing.dto.PricePredictionRequest;
import com.dynamicpricing.service.PricePredictionService;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class ProductPriceController {
    private final PricePredictionService predictionService;
    private final ProductPriceService service;

    public ProductPriceController(ProductPriceService service, PricePredictionService predictionService) {
        this.service = service;
        this.predictionService = predictionService;
    }

    @GetMapping
    public List<ProductPrice> getAll() {
        return service.getAllPrices();
    }

    @GetMapping("/{query}")
    public List<ProductPrice> getByQuery(@PathVariable String query) {
        return service.getPricesByQuery(query);
    }

    @PostMapping
    public ProductPrice save(@RequestBody ProductPrice price) {
        System.out.println("Received predicted price: " + price.getPredictedPrice());
        return service.save(price);
    }
    @PostMapping("/predict")
    public ResponseEntity<Double> predictPrice(@RequestBody PricePredictionRequest request) {
        double predicted = predictionService.predictPrice(
                request.getBasePrice(),
                request.getMargin(),
                request.getInventoryLevel(),
                request.getCompetitorPrice(),
                request.getDemandLevel()
        );
        return ResponseEntity.ok(predicted);
    }
}
