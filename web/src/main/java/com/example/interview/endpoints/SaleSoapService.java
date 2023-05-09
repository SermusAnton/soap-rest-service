package com.example.interview.endpoints;

import com.example.interview.models.Sale;

import javax.jws.WebService;
import java.math.BigDecimal;

@WebService
public interface SaleSoapService {

    Sale getSale(long saleId, long clientId);
    long createSale(long clientId);

    void addProductToSale(long saleId, long clientId, long productId);

    void removeProductFromSale(long saleId, long clientId, long productId);

    BigDecimal getEndTotalPrice(long saleId, long clientId);

    void fixSale(long saleId, long clientId);

    void removeSale(long saleId, long clientId);
}
