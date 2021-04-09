package com.braintreepayments.api;

import com.visa.checkout.Environment;
import com.visa.checkout.Profile;

import java.util.List;

public class VisaCheckoutProfile {

    private Profile.ProfileBuilder profileBuilder;
    private String merchantApiKey;
    private String environment;
    private List<String> acceptedCardBrands;
    private String externalClientId;

    public VisaCheckoutProfile(String merchantApiKey, String environment, List<String> acceptedCardBrands, String externalClientId) {
        this.environment = environment;
        this.merchantApiKey = merchantApiKey;
        this.acceptedCardBrands = acceptedCardBrands;
        this.externalClientId = externalClientId;
    }

    public Profile getProfile() {
        profileBuilder = new Profile.ProfileBuilder(merchantApiKey, getEnvironment());
        profileBuilder.setCardBrands(acceptedCardBrands.toArray(new String[acceptedCardBrands.size()]));
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
