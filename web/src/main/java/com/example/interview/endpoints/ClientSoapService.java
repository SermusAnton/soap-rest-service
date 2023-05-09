package com.example.interview.endpoints;

import com.example.interview.models.Client;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ClientSoapService {

    List<Client> getAllClients();

    void setProductRating(long clientId, long productId, int rating);
}
