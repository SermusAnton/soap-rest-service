package com.example.interview.models;

import javax.persistence.*;

@Entity
@Table(name = "product_ratings")
public class ProductRating {

    @EmbeddedId
    private ProductRatingId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name = "rating")
    private int rating;

    public ProductRatingId getId() {
        return id;
    }

    public void setId(ProductRatingId id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
