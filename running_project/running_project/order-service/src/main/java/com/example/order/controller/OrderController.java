package com.example.order.controller;
import com.example.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final List<Order> orders = new ArrayList<>();
    private final RestTemplate restTemplate;

    @Autowired
    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order orderRequest) {
        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must contain items");
        }
        double total = 0.0;
        // Validate each item by calling catalog service
        for (OrderItem item : orderRequest.getItems()) {
            try {
                Item product = restTemplate.getForObject(
                        "http://localhost:3001/api/items/" + item.getProductId(),
                        Item.class);
                if (product == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item not found: " + item.getProductId());
                }
                if (product.getQuantity() < item.getQuantity()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient quantity for item " + item.getProductId());
                }
                total += product.getPrice() * item.getQuantity();
            } catch (ResponseStatusException rse) {
                throw rse;
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to validate item " + item.getProductId());
            }
        }
        
        String orderId = "ORD-" + UUID.randomUUID().toString();
       
        PaymentRequest paymentRequest = new PaymentRequest(orderId, total, "credit_card");
        PaymentResponse paymentResponse = restTemplate.postForObject(
                "http://localhost:3003/api/payments",
                paymentRequest,
                PaymentResponse.class);
        if (paymentResponse == null || !"SUCCESS".equals(paymentResponse.getStatus())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Payment failed");
        }
       
        Order newOrder = new Order();
        newOrder.setId(orderId);
        newOrder.setCustomerId(orderRequest.getCustomerId());
        newOrder.setItems(orderRequest.getItems());
        newOrder.setTotal(total);
        newOrder.setStatus("CONFIRMED");
        newOrder.setPaymentStatus(paymentResponse.getStatus());
        newOrder.setTimestamp(Instant.now().toString());
        orders.add(newOrder);
        // Send notification
        NotificationRequest nr = new NotificationRequest(
                "OrderPlaced",
                orderRequest.getCustomerId(),
                "Your order " + orderId + " has been placed successfully.");
        restTemplate.postForObject(
                "http://localhost:3004/api/notify",
                nr,
                Object.class);
        return newOrder;
    }

    
    @GetMapping
    public List<Order> getAll() {
        return orders;
    }

    
    @GetMapping("/{id}")
    public Order getById(@PathVariable String id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}