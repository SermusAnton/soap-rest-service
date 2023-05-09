package com.example.interview.endpoints;

import com.example.interview.models.Sale;
import com.example.interview.services.SaleService;

import java.math.BigDecimal;

public class SaleSoapServiceImpl implements SaleSoapService {

    private final SaleService saleService;

    public SaleSoapServiceImpl(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    public Sale getSale(long saleId, long clientId) {
        return saleService.getSale(saleId, clientId);
    }

    @Override
    public long createSale(long clientId) {
        return saleService.createSale(clientId);
    }

    @Override
    public void addProductToSale(long saleId, long clientId, long productId) {
        saleService.addProductToSale(saleId, clientId, productId);
    }

    @Override
    public void removeProductFromSale(long saleId, long clientId, long productId) {
        saleService.removeProductFromSale(saleId, clientId, productId);
    }

    @Override
    public BigDecimal getEndTotalPrice(long saleId, long clientId) {
        return saleService.getEndTotalPrice(saleId, clientId);
    }

    @Override
    public void fixSale(long saleId, long clientId) {
        saleService.fixSale(saleId, clientId);
    }

    @Override
    public void removeSale(long saleId, long clientId) {
        saleService.removeSale(saleId, clientId);
    }
}
