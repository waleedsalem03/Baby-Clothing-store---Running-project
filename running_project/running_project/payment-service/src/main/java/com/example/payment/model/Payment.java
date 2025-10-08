package com.example.payment.model;

public class Payment {
    private String transactionId;
    private String orderId;
    private double amount;
    private String method;
    private String status;
    private String failureReason;

    public Payment() {
    }

    public Payment(String transactionId, String orderId, double amount, String method, String status, String failureReason) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.failureReason = failureReason;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}