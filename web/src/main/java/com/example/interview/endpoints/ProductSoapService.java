package com.example.interview.endpoints;

import com.example.interview.models.Product;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ProductSoapService {
    List<Product> getAllProducts();
}
