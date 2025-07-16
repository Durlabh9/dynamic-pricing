package com.dynamicpricing.repository;

import com.dynamicpricing.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    // Custom query: fetch all records matching a query term
    List<ProductPrice> findByQueryIgnoreCase(String query);

    // Optional: fetch top 5 latest records for a product
    List<ProductPrice> findTop5ByQueryIgnoreCaseOrderByFetchedAtDesc(String query);
}
