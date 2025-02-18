package com.braintreepayments.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.BrowserSwitchResult;
import com.braintreepayments.api.InvalidArgumentException;
import com.braintreepayments.api.SignatureVerificationOverrides;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DemoActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, ActionBar.OnNavigationListener {

    private static final String EXTRA_AUTHORIZATION = "com.braintreepayments.demo.EXTRA_AUTHORIZATION";
    private static final String EXTRA_CUSTOMER_ID = "com.braintreepayments.demo.EXTRA_CUSTOMER_ID";

    private BraintreeClient braintreeClient;
    private DemoAuthorizationProvider authProvider;

    private AppBarConfiguration appBarConfiguration;

    protected String mAuthorization;
    protected String mCustomerId;
    private DemoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_demo);

        authProvider = new DemoAuthorizationProvider();
        if (savedInstanceState != null) {
            mAuthorization = savedInstanceState.getString(EXTRA_AUTHORIZATION);
            mCustomerId = savedInstanceState.getString(EXTRA_CUSTOMER_ID);
        }

        setupActionBar();
        setProgressBarIndeterminateVisibility(true);

        viewModel = new ViewModelProvider(this).get(DemoViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SignatureVerificationOverrides.disableAppSwitchSignatureVerification(
                Settings.isPayPalSignatureVerificationDisabled(this));

        if (BuildConfig.DEBUG && ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            handleAuthorizationState();
        }

        handleBrowserSwitchResultIfNecessary();
    }

    public void getBraintreeClient(BraintreeClientCallback callback) {
        if (braintreeClient != null) {
            callback.onResult(braintreeClient);
            return;
        }
        if (mAuthorization != null) {
            try {
                braintreeClient = new BraintreeClient(this, mAuthorization);
                callback.onResult(braintreeClient);
            } catch (InvalidArgumentException e) {
                showDialog(e.getMessage());
            }
            return;
        }

        authProvider.fetchAuthorization(this, (authorization, error) -> {
            if (authorization != null) {
                mAuthorization = authorization;
                try {
                    braintreeClient = new BraintreeClient(DemoActivity.this, mAuthorization);
                    callback.onResult(braintreeClient);
                } catch (InvalidArgumentException e) {
                    showDialog(e.getMessage());
                }
            } else if (error != null) {
                showDialog(error.getMessage());
            }
        });
    }

    private void handleBrowserSwitchResultIfNecessary() {
        if (braintreeClient != null) {
            BrowserSwitchResult result = braintreeClient.deliverBrowserSwitchResult(this);
            if (result != null) {
                viewModel.onBrowserSwitchResult(result);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAuthorization != null) {
            outState.putString(EXTRA_AUTHORIZATION, mAuthorization);
            outState.putString(EXTRA_CUSTOMER_ID, mCustomerId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handleAuthorizationState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onActivityResult(requestCode, resultCode, data);
    }

    private void handleAuthorizationState() {
        if (mAuthorization == null ||
                (Settings.useTokenizationKey(this) && !mAuthorization.equals(Settings.getTokenizationKey(this))) ||
                !TextUtils.equals(mCustomerId, Settings.getCustomerId(this))) {
            performReset();
        }
    }

    private NavController getNavController() {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return NavHostFragment.findNavController(navHostFragment);
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavController navController = getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.environments, android.R.layout.simple_spinner_dropdown_item);
            actionBar.setListNavigationCallbacks(adapter, this);

            List<String> envs = Arrays.asList(getResources().getStringArray(R.array.environments));
            actionBar.setSelectedNavigationItem(envs.indexOf(Settings.getEnvironment(this)));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = getNavController();
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void performReset() {
        setProgressBarIndeterminateVisibility(true);

        mAuthorization = null;
        braintreeClient = null;
        mCustomerId = Settings.getCustomerId(this);
    }

    public void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                performReset();
                return true;
            case R.id.settings:
                NavController navController = getNavController();
                navController.navigate(R.id.open_settings_fragment);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        String env = getResources().getStringArray(R.array.environments)[itemPosition];
        if (!Settings.getEnvironment(this).equals(env)) {
            Settings.setEnvironment(this, env);
            performReset();
        }
        return true;
    }
}