package com.example.interview.services;

import com.example.interview.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    void setProductRating(long clientId, long productId, int rating);
}
