package com.thepyprogrammer.randstudent.model

import android.app.Activity
import android.content.Context
import android.util.Log
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IPublicClientApplication.ISingleAccountApplicationCreatedListener
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication.SignOutCallback
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.exception.MsalException
import com.thepyprogrammer.randstudent.R


// Singleton class - the app only needs a single instance
// of PublicClientApplication
class AuthenticationHelper private constructor(
    ctx: Context,
    listener: IAuthenticationHelperCreatedListener
) {
    private var mPCA: ISingleAccountPublicClientApplication? = null
    private val mScopes = arrayOf("User.Read", "MailboxSettings.Read", "Calendars.ReadWrite")
    fun acquireTokenInteractively(activity: Activity?, callback: AuthenticationCallback?) {
        mPCA!!.signIn(activity!!, null, mScopes, callback!!)
    }

    fun acquireTokenSilently(callback: AuthenticationCallback?) {
        // Get the authority from MSAL config
        val authority = mPCA!!.configuration.defaultAuthority.authorityURL.toString()
        mPCA!!.acquireTokenSilentAsync(mScopes, authority, callback!!)
    }

    fun signOut() {
        mPCA!!.signOut(object : SignOutCallback {
            override fun onSignOut() {
                Log.d("AUTHHELPER", "Signed out")
            }

            override fun onError(exception: MsalException) {
                Log.d("AUTHHELPER", "MSAL error signing out", exception)
            }
        })
    }

    companion object {
        private var INSTANCE: AuthenticationHelper? = null
        @Synchronized
        fun getInstance(ctx: Context, listener: IAuthenticationHelperCreatedListener) {
            if (INSTANCE == null) {
                INSTANCE = AuthenticationHelper(ctx, listener)
            } else {
                listener.onCreated(INSTANCE)
            }
        }

        // Version called from fragments. Does not create an
        // instance if one doesn't exist
        @get:Synchronized
        val instance: AuthenticationHelper?
            get() {
                checkNotNull(INSTANCE) { "AuthenticationHelper has not been initialized from MainActivity" }
                return INSTANCE
            }
    }

    init {
        PublicClientApplication.createSingleAccountPublicClientApplication(ctx, R.raw.msal_config,
            object : ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    mPCA = application
                    listener.onCreated(INSTANCE)
                }

                override fun onError(exception: MsalException) {
                    Log.e("AUTHHELPER", "Error creating MSAL application", exception)
                    listener.onError(exception)
                }
            })
    }
}