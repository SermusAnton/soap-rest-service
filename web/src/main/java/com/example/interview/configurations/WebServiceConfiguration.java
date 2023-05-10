package com.example.interview.configurations;

import com.example.interview.endpoints.ClientSoapServiceImpl;
import com.example.interview.endpoints.ProductSoapServiceImpl;
import com.example.interview.endpoints.SaleSoapServiceImpl;
import com.example.interview.services.ClientService;
import com.example.interview.services.ProductService;
import com.example.interview.services.SaleService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfiguration {
    private final Bus bus;
    private final ClientService clientService;
    private final ProductService productService;

    private final SaleService saleService;

    public WebServiceConfiguration(Bus bus, ClientService clientService, ProductService productService, SaleService saleService) {
        this.bus = bus;
        this.clientService = clientService;
        this.productService = productService;
        this.saleService = saleService;
    }

    @Bean
    public Endpoint clientEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new ClientSoapServiceImpl(clientService));
        endpoint.publish("/clients");
        return endpoint;
    }

    @Bean
    public Endpoint productEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new ProductSoapServiceImpl(productService));
        endpoint.publish("/products");
        return endpoint;
    }

    @Bean
    public Endpoint saleEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new SaleSoapServiceImpl(saleService));
        endpoint.publish("/sales");
        return endpoint;
    }
}
