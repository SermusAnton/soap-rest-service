package com.example.interview.services;

import com.example.interview.models.*;
import com.example.interview.repositories.ClientRepository;
import com.example.interview.repositories.ProductRatingRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ProductRatingRepository productRatingRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ProductRatingRepository productRatingRepository) {
        this.clientRepository = clientRepository;
        this.productRatingRepository = productRatingRepository;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        clients.forEach(c -> Hibernate.initialize(c.getSales()));
        return clients;
    }

    @Transactional()
    @Override
    public void setProductRating(long clientId, long productId, int rating) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException(String.format("Client by id '%s' not found", clientId)));

        Hibernate.initialize(client.getSales());
        Product product = client.getSales().stream()
                .filter(s -> s.getStatus().equals(SaleStatus.FIX))
                .peek(s -> Hibernate.initialize(s.getOrders()))
                .map(Sale::getOrders)
                .flatMap(List::stream)
                .peek(o -> Hibernate.initialize(o.getProduct()))
                .map(Order::getProduct)
                .filter(p -> p.getId() == productId)
                .findFirst().orElseThrow(() ->
                        new RuntimeException(String.format(
                                "Product by id '%s' for client with id '%s' not found", productId, clientId)));
        ProductRatingId productRatingId = new ProductRatingId(clientId, productId);
        productRatingRepository.findById(productRatingId)
                .ifPresent(r -> {
                    throw new RuntimeException(String.format(
                            "Client by id '%s' for product by id '%s' cant set rating twice", clientId, productId));
                });
        ProductRating productRating = new ProductRating();
        productRating.setId(productRatingId);
        productRating.setClient(client);
        productRating.setProduct(product);
        productRating.setRating(rating);
        productRatingRepository.save(productRating);
    }
}
