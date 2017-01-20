package com.naukri.sociallogin.facebook;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sudeep on 12/1/17.
 */

public class FacebookLoginHandler {

    private static CallbackManager callbackManager;

    FacebookLoginHandler(){

    }

    private static List PERMISSIONS_LIST = Arrays.asList("email", "public_profile");

    /**
     * Function will start the Facebook Login process
     *
     * @param activity     - Instance of activity that is initiating the Login Process
     * @param fbInteractor - Instance of  class implementing this interface
     */
    public static void initiateLogin(Activity activity, FbInteractor fbInteractor) {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestForLogin(activity, fbInteractor);
    }

    private static void requestForLogin(Activity activity, final FbInteractor fbInteractor) {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(activity, PERMISSIONS_LIST);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbInteractor.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                fbInteractor.onCancel();
            }

            @Override
            public void onError(FacebookException error) {
                fbInteractor.onError(error);
            }
        });
    }

    /**
     * Call this function from the {@link Activity#onActivityResult(int, int, Intent)} method
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (FacebookSdk.isFacebookRequestCode(requestCode)) {           //To check if the resultl is from Facebook,as it would be called in everycase otherwise
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * Check weather the user is logged in or out
     *
     * @return
     */
    public static boolean isLogedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * logout the current user
     */
    public static void logout() {
        LoginManager.getInstance().logOut();
    }

    /**
     * return the facebook user profile data
     *
     * @return
     */
    public static Profile getProfile() {
        return Profile.getCurrentProfile();
    }


}
