package com.braintreepayments.api;

/**
 * Callback for receiving result of
 * {@link VisaCheckoutClient#createProfileBuilder(VisaCheckoutCreateProfileBuilderCallback)}.
 */
public interface VisaCheckoutCreateProfileBuilderCallback {

    /**
     * @param profileBuilder Visa profile builder
     * @param error an exception that occurred while creating a Visa profile
     */
    void onResult(VisaCheckoutProfile profile, Exception error);
}
