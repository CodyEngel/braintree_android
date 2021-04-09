package com.braintreepayments.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// TODO: determine the best way to wrap visa Currency
@Retention(RetentionPolicy.SOURCE)
public @interface VisaCheckoutCurrency {
    String USD = "USD";
    String AUD = "AUD";
    String BRL = "BRL";
    String CAD = "CAD";
    String CNY = "CNY";
    String CLP = "CLP";
    String COP = "COP";
    String HKD = "HKD";
    String MYR = "MYR";
    String MXN = "MXN";
    String NZD = "NZD";
    String PEN = "PEN";
    String SGD = "SGD";
    String ZAR = "ZAR";
    String AED = "AED";
    String ARS = "ARS";
    String GBP = "GBP";
    String EUR = "EUR";
    String PLN = "PLN";
    String INR = "INR";
    String UAH = "UAH";
    String SAR = "SAR";
    String KWD = "KWD";
    String QAR = "QAR";
}
