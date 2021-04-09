package com.braintreepayments.api;

import com.visa.checkout.VisaPaymentSummary;

public interface VisaCheckoutButtonInitializeCallback {

    void onResult(VisaPaymentSummary paymentSummary, Exception error);
}
