package com.example.interview.endpoints;

import com.example.interview.models.Product;
import com.example.interview.services.ProductService;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.example.interview.endpoints.ProductSoapService")
public class ProductSoapServiceImpl implements ProductSoapService{

    private final ProductService productService;

    public ProductSoapServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
