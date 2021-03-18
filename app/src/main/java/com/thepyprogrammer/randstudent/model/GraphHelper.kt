package com.thepyprogrammer.randstudent.model

import com.microsoft.graph.authentication.IAuthenticationProvider
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.http.IHttpRequest
import com.microsoft.graph.models.extensions.IGraphServiceClient
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient


// Singleton class - the app only needs a single instance
// of the Graph client
class GraphHelper private constructor() : IAuthenticationProvider {
    private var mClient: IGraphServiceClient? = null
    private var mAccessToken: String? = null

    // Part of the Graph IAuthenticationProvider interface
    // This method is called before sending the HTTP request
    override fun authenticateRequest(request: IHttpRequest) {
        // Add the access token in the Authorization header
        request.addHeader("Authorization", "Bearer $mAccessToken")
    }

    fun getUser(accessToken: String?, callback: ICallback<User>) {
        mAccessToken = accessToken

        // GET /me (logged in user)
        mClient!!.me().buildRequest()
            .select("displayName,mail,mailboxSettings,userPrincipalName")[callback]
    }

    companion object {
        private var INSTANCE: GraphHelper? = null

        @get:Synchronized
        val instance: GraphHelper
            get() {
                if (INSTANCE == null) {
                    INSTANCE = GraphHelper()
                }
                return INSTANCE!!
            }
    }

    init {
        mClient = GraphServiceClient.builder()
            .authenticationProvider(this).buildClient()
    }
}