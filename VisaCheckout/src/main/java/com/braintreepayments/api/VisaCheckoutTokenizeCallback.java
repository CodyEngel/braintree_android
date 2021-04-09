package com.braintreepayments.api;

/**
 * Callback for receiving result of
 * {@link VisaCheckoutClient#tokenize(VisaCheckoutPaymentSummary, VisaCheckoutTokenizeCallback)}.
 */
public interface VisaCheckoutTokenizeCallback {

    /**
     * @param paymentMethodNonce {@link PaymentMethodNonce}
     * @param error an exception that occurred while tokenizing a Visa payment method
     */
    void onResult(PaymentMethodNonce paymentMethodNonce, Exception error);
}
