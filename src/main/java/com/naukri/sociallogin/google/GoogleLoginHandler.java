package com.naukri.sociallogin.google;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

/**
 * Created by sudeep on 17/1/17.
 */

public class GoogleLoginHandler implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    private FragmentActivity fragmentActivity;
    private GoogleInteractor googleInteractor;
    private final int RC_SIGN_IN = 6006;         //Google Login activity request code
    public final int NULL_ERROR_STATUS_CODE = -33;

    /**
     * Inits the  Google class
     *
     * @param activity         - The fragment activity initiating the login process
     * @param googleInteractor - Instance of  class implementing GoogleInteractor interface
     */
    public GoogleLoginHandler(FragmentActivity activity, GoogleInteractor googleInteractor) {
        this.fragmentActivity = activity;
        this.googleInteractor = googleInteractor;
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    /**
     * Initiates the login process
     */
    public void initiateLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        fragmentActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Call this function from the {@link Activity#onActivityResult(int, int, Intent)} method
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                if (data != null) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                } else {
                    loginFailed(NULL_ERROR_STATUS_CODE);
                }
            }
        }
    }

    private void loginFailed(int statusCode) {
        googleInteractor.onError(statusCode);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result == null) {
            loginFailed(NULL_ERROR_STATUS_CODE);
        } else if (result.isSuccess()) {
            googleInteractor.loginSuccess(result);
        } else {
            Status status = result.getStatus();
            int statusCode = status.getStatusCode();
            loginFailed(statusCode);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        googleInteractor.connectionFailed();
    }
}
