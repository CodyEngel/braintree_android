package com.braintreepayments.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class CardClientUnitTest {

    private Context context;
    private Card card;
    private CardTokenizeCallback cardTokenizeCallback;

    private BraintreeClient braintreeClient;
    private DataCollector dataCollector;
    private TokenizationClient tokenizationClient;

    @Before
    public void beforeEach() {
        context = mock(Context.class);
        card = mock(Card.class);
        cardTokenizeCallback = mock(CardTokenizeCallback.class);

        braintreeClient = mock(BraintreeClient.class);
        dataCollector = mock(DataCollector.class);
        tokenizationClient = mock(TokenizationClient.class);
    }

    @Test
    public void tokenize_callsListenerWithNonceOnSuccess() throws JSONException {
        CardClient sut = new CardClient(braintreeClient, tokenizationClient, dataCollector);
        sut.tokenize(context, card, cardTokenizeCallback);

        ArgumentCaptor<TokenizeCallback> callbackCaptor =
            ArgumentCaptor.forClass(TokenizeCallback.class);
        verify(tokenizationClient).tokenize(same(card), callbackCaptor.capture());

        TokenizeCallback callback = callbackCaptor.getValue();
        callback.onResult(new JSONObject(Fixtures.GRAPHQL_RESPONSE_CREDIT_CARD), null);

        verify(cardTokenizeCallback).onResult(any(CardNonce.class), (Exception) isNull());
    }

    @Test
    public void tokenize_sendsAnalyticsEventOnSuccess() throws JSONException {
        CardClient sut = new CardClient(braintreeClient, tokenizationClient, dataCollector);
        sut.tokenize(context, card, cardTokenizeCallback);

        ArgumentCaptor<TokenizeCallback> callbackCaptor =
                ArgumentCaptor.forClass(TokenizeCallback.class);
        verify(tokenizationClient).tokenize(same(card), callbackCaptor.capture());

        TokenizeCallback callback = callbackCaptor.getValue();
        callback.onResult(new JSONObject(Fixtures.GRAPHQL_RESPONSE_CREDIT_CARD), null);

        verify(braintreeClient).sendAnalyticsEvent("card.nonce-received");
    }

    @Test
    public void tokenize_callsListenerWithErrorOnFailure() {
        CardClient sut = new CardClient(braintreeClient, tokenizationClient, dataCollector);
        sut.tokenize(context, card, cardTokenizeCallback);

        ArgumentCaptor<TokenizeCallback> callbackCaptor =
                ArgumentCaptor.forClass(TokenizeCallback.class);
        verify(tokenizationClient).tokenize(same(card), callbackCaptor.capture());

        TokenizeCallback callback = callbackCaptor.getValue();
        Exception error = new Exception("error");
        callback.onResult(null, error);

        verify(cardTokenizeCallback).onResult(null, error);
    }

    @Test
    public void tokenize_sendsAnalyticsEventOnFailure() {
        CardClient sut = new CardClient(braintreeClient, tokenizationClient, dataCollector);
        sut.tokenize(context, card, cardTokenizeCallback);

        ArgumentCaptor<TokenizeCallback> callbackCaptor =
                ArgumentCaptor.forClass(TokenizeCallback.class);
        verify(tokenizationClient).tokenize(same(card), callbackCaptor.capture());

        TokenizeCallback callback = callbackCaptor.getValue();
        callback.onResult(null, new Exception("error"));

        verify(braintreeClient).sendAnalyticsEvent("card.nonce-failed");
    }
}