package com.braintreepayments.api;

import com.visa.checkout.Environment;
import com.visa.checkout.Profile;

import java.util.List;

public class VisaCheckoutProfile {

    private Profile.ProfileBuilder profileBuilder;

    public VisaCheckoutProfile(String merchantApiKey, String environment, List<String> acceptedCardBrands, String externalClientId) {
        String visaCheckoutEnvironment = Environment.SANDBOX;

        if ("production".equals(environment)) {
            visaCheckoutEnvironment = Environment.PRODUCTION;
        }
        profileBuilder = new Profile.ProfileBuilder(merchantApiKey, visaCheckoutEnvironment);
        profileBuilder.setCardBrands(acceptedCardBrands.toArray(new String[acceptedCardBrands.size()]));
        profileBuilder.setDataLevel(Profile.DataLevel.FULL);
        profileBuilder.setExternalClientId(externalClientId);
    }

    public Profile getProfile() {
        return profileBuilder.build();
    }
}
