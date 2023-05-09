package com.example.interview.endpoints;

import com.example.interview.models.Client;
import com.example.interview.services.ClientService;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.example.interview.endpoints.ClientSoapService")
public class ClientSoapServiceImpl implements ClientSoapService {

    private final ClientService clientService;

    public ClientSoapServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @Override
    public void setProductRating(long clientId, long productId, int rating) {
        clientService.setProductRating(clientId, productId, rating);
    }
}
