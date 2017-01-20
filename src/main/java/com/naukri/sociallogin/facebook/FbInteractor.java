package com.naukri.sociallogin.facebook;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by sudeep on 13/1/17.
 */

/**
 * Interface to handle the Facebook Callbacks,
 * implement or create an inner class of this interface int he Activity initiating Facebook login.
 */
public interface FbInteractor {
     void onSuccess(LoginResult loginResult);
     void onCancel();
     void onError(FacebookException facebookException);
}
