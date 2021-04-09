package com.braintreepayments.api;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.visa.checkout.CheckoutButton;
import com.visa.checkout.Environment;
import com.visa.checkout.VisaCheckoutSdk;

import java.util.List;

/**
 * Used to create and tokenize Visa Checkout. For more information see the
 * <a href="https://developers.braintreepayments.com/guides/visa-checkout/overview">documentation</a>
 */
public class VisaCheckoutClient {

    private final BraintreeClient braintreeClient;
    private final TokenizationClient tokenizationClient;
    private ClassHelper classHelper;

    public VisaCheckoutClient(BraintreeClient braintreeClient) {
        this(braintreeClient, new TokenizationClient(braintreeClient), new ClassHelper());
    }

    @VisibleForTesting
    VisaCheckoutClient(BraintreeClient braintreeClient, TokenizationClient tokenizationClient, ClassHelper classHelper) {
        this.braintreeClient = braintreeClient;
        this.tokenizationClient = tokenizationClient;
        this.classHelper = classHelper;
    }

    /**
     * Creates a {@link VisaCheckoutProfile} with the merchant API key, environment, and other properties to be used with
     * Visa Checkout.
     *
     * @param callback {@link VisaCheckoutCreateProfileBuilderCallback}
     */
    public void createProfileBuilder(final VisaCheckoutCreateProfileBuilderCallback callback) {
        braintreeClient.getConfiguration(new ConfigurationCallback() {
            @Override
            public void onResult(@Nullable Configuration configuration, @Nullable Exception e) {
                boolean enabledAndSdkAvailable =
                        classHelper.isClassAvailable("com.visa.checkout.VisaCheckoutSdk") && configuration.isVisaCheckoutEnabled();

                if (!enabledAndSdkAvailable) {
                    callback.onResult(null, new ConfigurationException("Visa Checkout is not enabled."));
                    return;
                }

                String merchantApiKey = configuration.getVisaCheckoutApiKey();
                List<String> acceptedCardBrands = configuration.getVisaCheckoutSupportedNetworks();

                String environment = "sandbox";
                if ("production".equals(configuration.getEnvironment())) {
                    environment = "production";
                }
                VisaCheckoutProfile profile = new VisaCheckoutProfile(merchantApiKey, environment, acceptedCardBrands, configuration.getVisaCheckoutExternalClientId());

                callback.onResult(profile, null);
            }
        });
    }

    /**
     * Tokenizes the payment summary of the Visa Checkout flow.
     *
     * @param visaPaymentSummary {@link VisaCheckoutPaymentSummary} The Visa payment to tokenize.
     * @param callback {@link VisaCheckoutTokenizeCallback}
     */
    public void tokenize(VisaCheckoutPaymentSummary visaPaymentSummary, final VisaCheckoutTokenizeCallback callback) {
        tokenizationClient.tokenize(new VisaCheckoutAccount(visaPaymentSummary), new PaymentMethodNonceCallback() {
            @Override
            public void success(PaymentMethodNonce paymentMethodNonce) {
                callback.onResult(paymentMethodNonce, null);
                braintreeClient.sendAnalyticsEvent("visacheckout.tokenize.succeeded");
            }

            @Override
            public void failure(Exception e) {
                callback.onResult(null, e);
                braintreeClient.sendAnalyticsEvent("visacheckout.tokenize.failed");
            }
        });
    }
}
