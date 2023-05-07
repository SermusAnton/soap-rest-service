package com.example.interview.configurations;

import javax.xml.ws.Endpoint;

import com.example.interview.endpoints.HelloServiceImpl;
import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfiguration {

    @Configuration
    public class WSConfig {

        @Autowired
        private Bus bus;

        @Bean
        public Endpoint helloEndpoint() {
            EndpointImpl endpoint = new EndpointImpl(bus, new HelloServiceImpl());
            endpoint.publish("/ServiceHello");

            return endpoint;
        }
    }
}
