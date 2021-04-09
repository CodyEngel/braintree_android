package com.braintreepayments.api;

import com.visa.checkout.PurchaseInfo;

import java.math.BigDecimal;

public class VisaCheckoutPurchaseInfo {

    public VisaCheckoutPurchaseInfo() {}

    public PurchaseInfo getPurchaseInfo() {
        PurchaseInfo purchaseInfo = new PurchaseInfo.PurchaseInfoBuilder(new BigDecimal("1.00"), PurchaseInfo.Currency.USD)
                .setDescription("Description")
                .build();
        return purchaseInfo;
    }
}
