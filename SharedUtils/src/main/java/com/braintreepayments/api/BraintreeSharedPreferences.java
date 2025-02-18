package com.braintreepayments.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

class BraintreeSharedPreferences {

    static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("BraintreeApi", Context.MODE_PRIVATE);
    }

    static void putParcelable(Context context, String key, Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        getSharedPreferences(context).edit()
                .putString(key, Base64.encodeToString(parcel.marshall(), 0))
                .apply();
    }

    static Parcel getParcelable(Context context, String key) {
        try {
            byte[] requestBytes = Base64.decode(getSharedPreferences(context).getString(key, ""), 0);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(requestBytes, 0, requestBytes.length);
            parcel.setDataPosition(0);

            return parcel;
        } catch (Exception ignored) {}

        return null;
    }

    static void remove(Context context, String key) {
        getSharedPreferences(context)
                .edit()
                .remove(key)
                .apply();
    }

    static void putString(Context context, String key, String value) {
        getSharedPreferences(context)
                .edit()
                .putString(key, value)
                .apply();
    }

    static String getString(Context context, String key) {
        return getSharedPreferences(context)
                .getString(key, "");
    }
}
