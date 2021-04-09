package com.braintreepayments.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class VisaCheckoutAccountUnitTest {

    @Test
    public void build_withNullVisaPaymentSummary_buildsEmptyPaymentMethod() throws JSONException {
        JSONObject base = new JSONObject();
        JSONObject paymentMethodNonceJson = new JSONObject();
        JSONObject expectedBase = new JSONObject("{\"visaCheckoutCard\":{}}");

        VisaCheckoutAccount visaCheckoutAccount = new VisaCheckoutAccount(null);
        visaCheckoutAccount.buildJSON(base, paymentMethodNonceJson);

        JSONAssert.assertEquals(expectedBase, base, JSONCompareMode.STRICT);
    }

    @Test
    public void build_withVisaPaymentSummary_buildsExpectedPaymentMethod() throws JSONException {
        VisaCheckoutPaymentSummary visaCheckoutPaymentSummary = new VisaCheckoutPaymentSummary("stubbedCallId", "stubbedEncKey", "stubbedEncPaymentData");

        JSONObject base = new JSONObject();
        JSONObject paymentMethodNonceJson = new JSONObject();

        VisaCheckoutAccount visaCheckoutAccount = new VisaCheckoutAccount(visaCheckoutPaymentSummary);
        visaCheckoutAccount.buildJSON(base, paymentMethodNonceJson);

        JSONObject expectedBase = new JSONObject();
        JSONObject expectedPaymentMethodNonce = new JSONObject();
        expectedPaymentMethodNonce.put("callId", "stubbedCallId");
        expectedPaymentMethodNonce.put("encryptedKey", "stubbedEncKey");
        expectedPaymentMethodNonce.put("encryptedPaymentData", "stubbedEncPaymentData");
        expectedBase.put("visaCheckoutCard", expectedPaymentMethodNonce);

        JSONAssert.assertEquals(expectedBase, base, JSONCompareMode.STRICT);
    }

    @Test
    public void getApiPath_returnsCorrectApiPath() {
        assertEquals("visa_checkout_cards", new VisaCheckoutAccount(null).getApiPath());
    }

    @Test
    public void getResponsePaymentMethodType_returnsCorrectPaymentMethodType() {
        assertEquals(VisaCheckoutNonce.TYPE,
                new VisaCheckoutAccount(null).getResponsePaymentMethodType());
    }
}
