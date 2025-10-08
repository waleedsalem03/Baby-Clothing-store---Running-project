package com.example.payment.controller;

import com.example.payment.model.Payment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final List<Payment> payments = new ArrayList<>();

   
    @PostMapping
    public Payment processPayment(@RequestBody Payment p) {
        Payment newPayment = new Payment();
        newPayment.setTransactionId(UUID.randomUUID().toString());
        newPayment.setOrderId(p.getOrderId());
        newPayment.setAmount(p.getAmount());
        newPayment.setMethod(p.getMethod());
        // Always succeed for this example
        newPayment.setStatus("SUCCESS");
        newPayment.setFailureReason(null);
        payments.add(newPayment);
        return newPayment;
    }

   
    @GetMapping
    public List<Payment> getAll() {
        return payments;
    }
}