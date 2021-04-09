package com.braintreepayments.api;

import com.visa.checkout.PurchaseInfo;

import java.math.BigDecimal;
import java.util.HashMap;

public class VisaCheckoutPurchaseInfo {

    private final PurchaseInfo.PurchaseInfoBuilder purchaseInfoBuilder;

    public VisaCheckoutPurchaseInfo(BigDecimal amount, String currency) {
        purchaseInfoBuilder = new PurchaseInfo.PurchaseInfoBuilder(amount, currency);
    }

    public PurchaseInfo getPurchaseInfo() {
        return purchaseInfoBuilder.build();
    }

    public void setSubtotal(BigDecimal subtotal) {
        purchaseInfoBuilder.setSubTotal(subtotal);
    }

    public void setShippingHandling(BigDecimal shippingHandling) {
        purchaseInfoBuilder.setShippingHandling(shippingHandling);
    }

    public void setTax(BigDecimal tax) {
        purchaseInfoBuilder.setTax(tax);
    }

    public void setDiscount(BigDecimal discount) {
        purchaseInfoBuilder.setDiscount(discount);
    }

    public void setGiftWrap(BigDecimal giftWrap) {
        purchaseInfoBuilder.setGiftWrap(giftWrap);
    }

    public void setMiscellaneous(BigDecimal miscellaneous) {
        purchaseInfoBuilder.setMisc(miscellaneous);
    }

    public void setDescription(String description) {
        purchaseInfoBuilder.setDescription(description);
    }

    public void setOrderId(String orderId) {
        purchaseInfoBuilder.setOrderId(orderId);
    }

    public void setReviewMessage(String reviewMessage) {
        purchaseInfoBuilder.setReviewMessage(reviewMessage);
    }

    public void setMerchantRequestId(String merchantRequestId) {
        purchaseInfoBuilder.setMerchantRequestId(merchantRequestId);
    }

    public void setSourceId(String sourceId) {
        purchaseInfoBuilder.setSourceId(sourceId);
    }

    public void setPromoCode(String promoCode) {
        purchaseInfoBuilder.setPromoCode(promoCode);
    }

    public void setShippingAddressRequired(boolean shippingAddressRequired) {
        purchaseInfoBuilder.setShippingAddressRequired(shippingAddressRequired);
    }

    public void setUserReviewAction(String userReviewAction) {
        purchaseInfoBuilder.setUserReviewAction(userReviewAction);
    }

    public void setCustomData(HashMap<String, String> customData) {
        purchaseInfoBuilder.setCustomData(customData);
    }

    public void setReferenceCallId(String referenceCallId) {
        purchaseInfoBuilder.setReferenceCallId(referenceCallId);
    }

    public void setEnableUserDataPrefill(boolean enableUserDataPrefill) {
        purchaseInfoBuilder.setEnableUserDataPrefill(enableUserDataPrefill);
    }
    // TODO: determine which purchase info methods are needed
}
