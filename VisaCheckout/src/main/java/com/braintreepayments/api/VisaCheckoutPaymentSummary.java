package com.braintreepayments.api;

import com.visa.checkout.VisaPaymentSummary;

public class VisaCheckoutPaymentSummary {

    private String callId;
    private String encryptedKey;
    private String encryptedPaymentData;

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
