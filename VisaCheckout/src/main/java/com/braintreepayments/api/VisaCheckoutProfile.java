package com.braintreepayments.api;

import com.visa.checkout.Environment;
import com.visa.checkout.Profile;

import java.util.List;

public class VisaCheckoutProfile {

    private final String merchantApiKey;
    private final String environment;
    private final List<String> acceptedCardBrands;
    private final String externalClientId;

    public VisaCheckoutProfile(String merchantApiKey, String environment, List<String> acceptedCardBrands, String externalClientId) {
        this.environment = environment;
        this.merchantApiKey = merchantApiKey;
        this.acceptedCardBrands = acceptedCardBrands;
        this.externalClientId = externalClientId;
    }

    public Profile getProfile() {
        Profile.ProfileBuilder profileBuilder = new Profile.ProfileBuilder(merchantApiKey, getEnvironment());
        profileBuilder.setCardBrands(acceptedCardBrands.toArray(new String[0]));
        profileBuilder.setDataLevel(Profile.DataLevel.FULL);
        profileBuilder.setExternalClientId(externalClientId);
        return profileBuilder.build();
    }

    String getEnvironment() {
        if (environment.equals("production")) {
            return Environment.PRODUCTION;
        } else {
            return Environment.SANDBOX;
        }
    }

    String getMerchantApiKey() {
        return merchantApiKey;
    }

    List<String> getAcceptedCardBrands() {
        return acceptedCardBrands;
    }

    String getExternalClientId() {
        return externalClientId;
    }
}
