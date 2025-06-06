package com.example.ticketsystem.model;

public class PaymentResponse {
    private boolean success;
    private String transactionId;
    private String errorMessage;


    public PaymentResponse() {}

    public PaymentResponse(boolean success, String transactionId) {
        this.success = success;
        this.transactionId = transactionId;
    }

    public PaymentResponse(boolean success, String transactionId, String errorMessage) {
        this.success = success;
        this.transactionId = transactionId;
        this.errorMessage = errorMessage;
    }


    public boolean isSuccess() {
        return success;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}