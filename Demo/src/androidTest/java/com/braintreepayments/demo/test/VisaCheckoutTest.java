package com.braintreepayments.demo.test;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.braintreepayments.UiObjectMatcher;
import com.braintreepayments.demo.test.utilities.TestHelper;
import com.visa.checkout.CheckoutButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.braintreepayments.AutomatorAction.click;
import static com.braintreepayments.DeviceAutomator.onDevice;
import static com.braintreepayments.UiObjectMatcher.withClass;
import static com.braintreepayments.UiObjectMatcher.withResourceId;
import static com.braintreepayments.UiObjectMatcher.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class VisaCheckoutTest extends TestHelper {

    @Before
    public void setup() {
        super.setup();
        onDevice(withText("Visa Checkout")).waitForEnabled().perform(click());
    }

    @Test(timeout = 40000)
    public void visaCheckout_tokenizes() {
        String buttonId = "com.braintreepayments.demo:id/visa_checkout_button";
        onDevice(withResourceId(buttonId)).waitForEnabled();
        UiObjectMatcher uiObjectMatcher = withResourceId(buttonId);
        UiObjectMatcher childMatcher = uiObjectMatcher.childMatcher(withClass(CheckoutButton.class));
        UiObjectMatcher classMatcher = withClass(CheckoutButton.class);
        onDevice(uiObjectMatcher).waitForExists();
//        onDevice(childMatcher).waitForExists().perform(click());
        onDevice(classMatcher).waitForExists().perform(click());
    }
}
