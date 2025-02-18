package com.braintreepayments.api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Contains the remote Visa Checkout configuration for the Braintree SDK.
 */
class VisaCheckoutConfiguration {

    private boolean mIsEnabled;
    private String mApiKey;
    private String mExternalClientId;
    private List<String> mCardBrands;

    static VisaCheckoutConfiguration fromJson(JSONObject json) {
        VisaCheckoutConfiguration visaCheckoutConfiguration = new VisaCheckoutConfiguration();

        if (json == null) {
            json = new JSONObject();
        }

        visaCheckoutConfiguration.mApiKey = Json.optString(json, "apikey", "");
        visaCheckoutConfiguration.mIsEnabled = !visaCheckoutConfiguration.mApiKey.equals("");
        visaCheckoutConfiguration.mExternalClientId = Json.optString(json, "externalClientId", "");
        visaCheckoutConfiguration.mCardBrands = supportedCardTypesToAcceptedCardBrands(
                CardConfiguration.fromJson(json).getSupportedCardTypes());

        return visaCheckoutConfiguration;
    }

    /**
     * Determines if the Visa Checkout flow is available to be used. This can be used to determine
     * if UI components should be shown or hidden.
     *
     * @return boolean if Visa Checkout SDK is available, and configuration is enabled.
     */
    boolean isEnabled() {
        return mIsEnabled;
    }

    /**
     * @return The Visa Checkout External Client Id associated with this merchant's Visa Checkout configuration.
     */
    String getExternalClientId() {
        return mExternalClientId;
    }

    /**
     * @return The Visa Checkout API Key associated with this merchant's Visa Checkout configuration.
     */
    String getApiKey() {
        return mApiKey;
    }

    /**
     * @return The accepted card brands for Visa Checkout.
     */
    List<String> getAcceptedCardBrands() {
        return mCardBrands;
    }

    private static List<String> supportedCardTypesToAcceptedCardBrands(List<String> supportedCardTypes) {
        List<String> acceptedCardBrands = new ArrayList<>();

        for (String supportedCardType : supportedCardTypes) {
            switch (supportedCardType.toLowerCase(Locale.ROOT)) {
                case "visa":
                    acceptedCardBrands.add("VISA");
                    break;
                case "mastercard":
                    acceptedCardBrands.add("MASTERCARD");
                    break;
                case "discover":
                    acceptedCardBrands.add("DISCOVER");
                    break;
                case "american express":
                    acceptedCardBrands.add("AMEX");
                    break;
            }
        }

        return acceptedCardBrands;
    }
}
