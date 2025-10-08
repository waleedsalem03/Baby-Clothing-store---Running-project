package com.example.order.model;


public class PaymentRequest {
    private String orderId;
    private double amount;
    private String method;

    public PaymentRequest() {
    }

    public PaymentRequest(String orderId, double amount, String method) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}