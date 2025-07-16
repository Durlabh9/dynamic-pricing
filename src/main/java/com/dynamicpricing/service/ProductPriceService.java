package com.dynamicpricing.service;

import com.dynamicpricing.model.ProductPrice;
import com.dynamicpricing.repository.ProductPriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceService {

    private final ProductPriceRepository repository;

    public ProductPriceService(ProductPriceRepository repository) {
        this.repository = repository;
    }

    public List<ProductPrice> getAllPrices() {
        return repository.findAll();
    }

    public List<ProductPrice> getPricesByQuery(String query) {
        return repository.findByQueryIgnoreCase(query);
    }

    public List<ProductPrice> getLatest5Prices(String query) {
        return repository.findTop5ByQueryIgnoreCaseOrderByFetchedAtDesc(query);
    }

    public ProductPrice save(ProductPrice price) {
        return repository.save(price);
    }
}
