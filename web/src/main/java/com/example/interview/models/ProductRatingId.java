package com.example.interview.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductRatingId implements Serializable {
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "product_id")
    private Long productId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ProductRatingId() {
    }

    public ProductRatingId(Long clientId, Long productId) {
        this.clientId = clientId;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRatingId productRatingId = (ProductRatingId) o;
        return Objects.equals(clientId, productRatingId.clientId) && Objects.equals(productId, productRatingId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, productId);
    }
}
