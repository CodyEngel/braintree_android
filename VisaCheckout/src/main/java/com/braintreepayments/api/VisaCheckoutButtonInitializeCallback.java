package com.braintreepayments.api;

public interface VisaCheckoutButtonInitializeCallback {

    void onResult(VisaCheckoutPaymentSummary paymentSummary, Exception error);
}
