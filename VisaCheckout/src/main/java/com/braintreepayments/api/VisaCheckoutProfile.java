package com.braintreepayments.api;

import com.visa.checkout.Profile;

import java.util.List;

public class VisaCheckoutProfile {

    private Profile.ProfileBuilder profileBuilder;

    public VisaCheckoutProfile(String merchantApiKey, String environment, List<String> acceptedCardBrands, String externalClientId) {
        profileBuilder = new Profile.ProfileBuilder(merchantApiKey, environment);
        profileBuilder.setCardBrands(acceptedCardBrands.toArray(new String[acceptedCardBrands.size()]));
        profileBuilder.setDataLevel(Profile.DataLevel.FULL);
        profileBuilder.setExternalClientId(externalClientId);
    }

    public Profile getProfile() {
        return profileBuilder.build();
    }
}
