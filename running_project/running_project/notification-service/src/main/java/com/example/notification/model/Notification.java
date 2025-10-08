package com.example.notification.model;

public class Notification {
    private String id;
    private String type;
    private String recipient;
    private String message;
    private String timestamp;

    public Notification() {
    }

    public Notification(String id, String type, String recipient, String message, String timestamp) {
        this.id = id;
        this.type = type;
        this.recipient = recipient;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}