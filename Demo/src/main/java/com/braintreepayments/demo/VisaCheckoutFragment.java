package com.braintreepayments.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.VisaCheckoutButtonContainer;
import com.braintreepayments.api.VisaCheckoutButtonInitializeCallback;
import com.braintreepayments.api.VisaCheckoutClient;
import com.braintreepayments.api.VisaCheckoutProfile;
import com.braintreepayments.api.VisaCheckoutPurchaseInfo;
import com.visa.checkout.VisaPaymentSummary;

public class VisaCheckoutFragment extends BaseFragment {

    private VisaCheckoutButtonContainer visaCheckoutButton;
    private VisaCheckoutClient visaCheckoutClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visa_checkout, container, false);
        visaCheckoutButton = view.findViewById(R.id.visa_checkout_button);

        getBraintreeClient(braintreeClient -> {
            visaCheckoutClient = new VisaCheckoutClient(braintreeClient);
            visaCheckoutClient.createProfileBuilder((profileBuilder, error) -> {
                if (profileBuilder != null) {
                    setupVisaCheckoutButton(profileBuilder);
                } else {
                    handleError(error);
                }
            });
        });
        return view;
    }

    private void setupVisaCheckoutButton(VisaCheckoutProfile profile) {
        VisaCheckoutPurchaseInfo purchaseInfo = new VisaCheckoutPurchaseInfo("1.00", "USD");

        visaCheckoutButton.initialize(getActivity(), profile, purchaseInfo, new VisaCheckoutButtonInitializeCallback() {
            @Override
            public void onResult(VisaPaymentSummary paymentSummary, Exception error) {
                visaCheckoutClient.tokenize(paymentSummary, (paymentMethodNonce, exception) -> {
                    if (paymentMethodNonce != null) {
                        handlePaymentMethodNonceCreated(paymentMethodNonce);
                    } else {
                        handleError(exception);
                    }
                });
            }
        });
    }

    private void handlePaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        super.onPaymentMethodNonceCreated(paymentMethodNonce);

        VisaCheckoutFragmentDirections.ActionVisaCheckoutFragmentToDisplayNonceFragment action =
            VisaCheckoutFragmentDirections.actionVisaCheckoutFragmentToDisplayNonceFragment(paymentMethodNonce);

        NavHostFragment.findNavController(this).navigate(action);
    }
}
