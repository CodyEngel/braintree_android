package com.braintreepayments.api;

public class VisaCheckoutPaymentSummary {

    private final String callId;
    private final String encryptedKey;
    private final String encryptedPaymentData;

    public VisaCheckoutPaymentSummary(String callId, String encryptedKey, String encryptedPaymentData) {
        this.callId = callId;
        this.encryptedKey = encryptedKey;
        this.encryptedPaymentData = encryptedPaymentData;
    }

    String getCallId() {
        return callId;
    }

    String getEncryptedKey() {
        return encryptedKey;
    }

    String getEncryptedPaymentData() {
        return encryptedPaymentData;
    }
}
