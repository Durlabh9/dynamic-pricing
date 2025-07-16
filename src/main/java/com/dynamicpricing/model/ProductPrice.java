package com.dynamicpricing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_prices")  // exact table name
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String query;

    @Column(name = "product_title", nullable = false)
    private String productTitle;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String source;

    @Column(name = "fetched_at")
    private LocalDateTime fetchedAt;
    @Column(name = "predicted_price")
    private Integer predictedPrice;


    // Getters and Setters

    public Integer getPredictedPrice() {
        return predictedPrice;
    }

    public void setPredictedPrice(Integer predictedPrice) {
        this.predictedPrice = predictedPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDateTime getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(LocalDateTime fetchedAt) { this.fetchedAt = fetchedAt; }
}
