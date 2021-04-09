package com.braintreepayments.api;

import com.visa.checkout.VisaPaymentSummary;

public class VisaCheckoutPaymentSummary {

    private VisaPaymentSummary visaPaymentSummary;

    public VisaCheckoutPaymentSummary(VisaPaymentSummary visaPaymentSummary) {
        this.visaPaymentSummary = visaPaymentSummary;
    }

    String getCallId() {
        return visaPaymentSummary.getCallId();
    }

    String getEncryptedKey() {
        return visaPaymentSummary.getEncKey();
    }

    String getEncryptedPaymentData() {
        return visaPaymentSummary.getEncPaymentData();
    }
}
