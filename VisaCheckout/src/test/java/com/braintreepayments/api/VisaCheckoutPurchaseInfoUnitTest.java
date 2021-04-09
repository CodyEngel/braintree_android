package com.braintreepayments.api;

import com.visa.checkout.PurchaseInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

// TODO: Investigate how to test wrapper classes to avoid VerifyError
@RunWith(RobolectricTestRunner.class)
public class VisaCheckoutPurchaseInfoUnitTest {

    @Test
    public void getPurchaseInfo_buildsPurchaseInfo() {
        VisaCheckoutPurchaseInfo sut = new VisaCheckoutPurchaseInfo(new BigDecimal("1.00"), "USD");
        PurchaseInfo purchaseInfo = sut.getPurchaseInfo();
        assertEquals("1.00", purchaseInfo.getTotal());
    }
}
