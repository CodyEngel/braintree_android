package com.braintreepayments.api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import androidx.fragment.app.FragmentActivity;

import com.visa.checkout.CheckoutButton;
import com.visa.checkout.VisaCheckoutSdk;
import com.visa.checkout.VisaPaymentSummary;

@RemoteViews.RemoteView
public class VisaCheckoutButtonContainer extends ViewGroup {

    private final CheckoutButton checkoutButton;

    public VisaCheckoutButtonContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        checkoutButton = new CheckoutButton(context, attrs);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        checkoutButton.setId(View.generateViewId());
        checkoutButton.setLayoutParams(buttonLayoutParams);
        addView(checkoutButton);
    }

    public void initialize(final FragmentActivity activity, VisaCheckoutProfile profile, final VisaCheckoutPurchaseInfo purchaseInfo, final VisaCheckoutButtonInitializeCallback callback) {
        checkoutButton.init(activity, profile.getProfile(), purchaseInfo.getPurchaseInfo(), new VisaCheckoutSdk.VisaCheckoutResultListener() {
            @Override
            public void onButtonClick(LaunchReadyHandler launchReadyHandler) {
                launchReadyHandler.launch();
            }

            @Override
            public void onResult(VisaPaymentSummary visaPaymentSummary) {
                VisaCheckoutPaymentSummary visaCheckoutPaymentSummary = new VisaCheckoutPaymentSummary(visaPaymentSummary.getCallId(), visaPaymentSummary.getEncKey(), visaPaymentSummary.getEncPaymentData());
                callback.onResult(visaCheckoutPaymentSummary, null);
            }
        });
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final View child = getChildAt(0);
        if (child.getVisibility() != GONE) {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();

        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        final View child = getChildAt(0);
        child.layout(leftPos, parentTop, rightPos, parentBottom);
    }
}