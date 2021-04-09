package com.braintreepayments.api;

import com.visa.checkout.PurchaseInfo;

import java.math.BigDecimal;

public class VisaCheckoutPurchaseInfo {

    private PurchaseInfo.PurchaseInfoBuilder purchaseInfoBuilder;

    public VisaCheckoutPurchaseInfo(String amount, String currency) {
        purchaseInfoBuilder = new PurchaseInfo.PurchaseInfoBuilder(new BigDecimal(amount), PurchaseInfo.Currency.USD);
    }

    public PurchaseInfo getPurchaseInfo() {
        return purchaseInfoBuilder.build();
    }
}
