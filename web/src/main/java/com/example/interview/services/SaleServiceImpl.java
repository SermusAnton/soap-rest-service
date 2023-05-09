package com.example.interview.services;

import com.example.interview.models.*;
import com.example.interview.repositories.BillRepository;
import com.example.interview.repositories.OrderRepository;
import com.example.interview.repositories.ProductRepository;
import com.example.interview.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SaleServiceImpl implements SaleService{

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;

    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository, OrderRepository orderRepository, BillRepository billRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.billRepository = billRepository;
    }

    @Override
    public Sale getSale(long saleId, long clientId) {
        return saleRepository.findByIdAndClientId(saleId, clientId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Sale by id '%s' and client id '%s' not found", saleId, clientId)));
    }

    @Transactional
    @Override
    public long createSale(long clientId) {
        Client client = new Client();
        client.setId(clientId);
        Sale newSale = new Sale();
        newSale.setClient(client);
        return saleRepository.save(newSale).getId();
    }

    @Transactional
    @Override
    public void addProductToSale(long saleId, long clientId, long productId) {
        Sale sale = saleRepository.findByIdAndClientId(saleId, clientId).orElseThrow(() -> new RuntimeException(
                String.format("Sale by id '%s' and client id '%s' not found", saleId, clientId)));
        if (sale.getStatus().equals(SaleStatus.FIX)) {
            throw new RuntimeException(
                    String.format("Sale by id '%s' fixed", saleId));
        }

        Optional<Order> order = sale.getOrders().stream()
                .filter(x -> x.getProduct().getId() == productId)
                .findAny();

        Order newOrder;
        if (order.isPresent()) {
            newOrder = order.get();
        } else {
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(
                    String.format("Product by id '%s' not found", productId)));
            newOrder = new Order();
            newOrder.setProduct(product);
            newOrder.setSale(sale);
        }

        newOrder.increaseCount();
        newOrder.calculateTotal();
        orderRepository.save(newOrder);
        saleRepository.save(sale);
    }

    @Transactional
    @Override
    public void removeProductFromSale(long saleId, long clientId, long productId) {
        Sale sale = saleRepository.findByIdAndClientId(saleId, clientId).orElseThrow(() -> new RuntimeException(
                String.format("Sale by id '%s' and client id '%s' not found", saleId, clientId)));
        if (sale.getStatus().equals(SaleStatus.FIX)) {
            throw new RuntimeException(
                    String.format("Sale by id '%s' fixed", saleId));
        }

        Optional<Order> orderOptional = sale.getOrders().stream()
                .filter(x -> x.getProduct().getId() == productId)
                .findAny();

        if (!orderOptional.isPresent()) {
            throw new RuntimeException(
                    String.format("Product for sale by id '%s' not found", saleId));
        }

        Order order = orderOptional.get();
        order.decreaseCount();
        if (order.getCount()!=0) {
            order.calculateTotal();
            orderRepository.save(order);
        } else {
            orderRepository.delete(order);
        }
        saleRepository.save(sale);
    }

    @Override
    public BigDecimal getEndTotalPrice(long saleId, long clientId) {
        Sale sale = saleRepository.findByIdAndClientId(saleId, clientId).orElseThrow(() -> new RuntimeException(
                String.format("Sale by id '%s' and client id '%s' not found", saleId, clientId)));
        return sale.getOrders().stream().map(Order::getEndTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    @Override
    public void fixSale(long saleId, long clientId) {
        Sale sale = saleRepository.findByIdAndClientId(saleId, clientId).orElseThrow(() -> new RuntimeException(
                String.format("Sale by id '%s' and client id '%s' not found", saleId, clientId)));
        LocalDate currentDate = LocalDate.now();
        Optional<Bill> billOptional = billRepository.findFirstByDateOfSaleOrderByNumberDesc(currentDate);
        int billNumber = 100;
        if (billOptional.isPresent()) {
            billNumber = billOptional.get().getNumber() + 1;
        }
        Bill newBill = new Bill();
        newBill.setDateOfSale(currentDate);
        newBill.setNumber(billNumber);
        sale.fixedSale(currentDate, billNumber);
        billRepository.save(newBill);
        saleRepository.save(sale);
    }

    @Transactional
    @Override
    public void removeSale(long saleId, long clientId) {
        orderRepository.deleteBySaleId(saleId);
        saleRepository.deleteByIdAndClientId(saleId, clientId);
    }
}
