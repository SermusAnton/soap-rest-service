package com.example.interview.services;

import com.example.interview.models.Sale;

import javax.jws.WebService;
import java.math.BigDecimal;

@WebService(endpointInterface = "com.example.interview.endpoints.SaleService")
public interface SaleService {

    Sale getSale(long saleId, long clientId);
    long createSale(long clientId);

    void addProductToSale(long saleId, long clientId, long productId);

    void removeProductFromSale(long saleId, long clientId, long productId);

    BigDecimal getEndTotalPrice(long saleId, long clientId);

    void fixSale(long saleId, long clientId);

    void removeSale(long saleId, long clientId);
}
