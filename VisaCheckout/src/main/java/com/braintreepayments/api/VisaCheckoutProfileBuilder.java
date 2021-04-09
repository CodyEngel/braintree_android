package com.braintreepayments.api;

import com.visa.checkout.Environment;
import com.visa.checkout.Profile;

public class VisaCheckoutProfileBuilder {

    public VisaCheckoutProfileBuilder() {
    }

    public Profile getProfileBuilder() {
        Profile.ProfileBuilder profileBuilder = new Profile.ProfileBuilder("test", Environment.SANDBOX);
        profileBuilder.setDataLevel(Profile.DataLevel.FULL);
        return profileBuilder.build();
    }
}
