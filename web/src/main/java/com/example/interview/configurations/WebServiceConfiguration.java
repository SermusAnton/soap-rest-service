package com.example.interview.configurations;

import javax.xml.ws.Endpoint;

import com.example.interview.endpoints.ClientSoapServiceImpl;
import com.example.interview.endpoints.HelloServiceImpl;
import com.example.interview.endpoints.ProductSoapServiceImpl;
import com.example.interview.services.ClientService;
import com.example.interview.services.ProductService;
import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfiguration {
    private final Bus bus;
    private final ClientService clientService;
    private final ProductService productService;

    public WebServiceConfiguration(Bus bus, ClientService clientService, ProductService productService) {
        this.bus = bus;
        this.clientService = clientService;
        this.productService = productService;
    }

    @Bean
    public Endpoint helloEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloServiceImpl());
        endpoint.publish("/ServiceHello");
        return endpoint;
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
}
