package com.braintreepayments.api;


import org.json.JSONObject;

class UnionPayConfiguration {

    private static final String ENABLED = "enabled";

    private boolean mEnabled;

    static UnionPayConfiguration fromJson(JSONObject json) {
        if (json == null) {
            json = new JSONObject();
        }

        UnionPayConfiguration unionPayConfiguration = new UnionPayConfiguration();
        unionPayConfiguration.mEnabled = json.optBoolean(ENABLED, false);

        return unionPayConfiguration;
    }

    /**
     * Determines if UnionPay is available to be used
     *
     * @return boolean if UnionPay is enabled, and available to be used
     */
    boolean isEnabled() {
        return mEnabled;
    }
}
