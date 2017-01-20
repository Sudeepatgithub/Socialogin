package com.naukri.sociallogin.google;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
/**
 * Created by sudeep.srivastava on 17/1/17.
 */

/**
 * Interface to handle the Google Callbacks,
 * implement or create an inner class of this interface int he Activity initiating Google login.
 */

public interface GoogleInteractor {
    void loginSuccess(GoogleSignInResult result);

    void connectionFailed();

    void onError(int statusCode);
}
