package com.project.data.source.remote

import com.project.data.utils.TokenPref
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val api: AppApi) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization-Retry") == "true") {
            return null
        }

        val newAccessToken = refreshAccessToken()
        if (newAccessToken.isNotEmpty()) {
            return response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .header("Authorization-Retry", "true")
                .build()
        }

        return null
    }

    private fun refreshAccessToken(): String {
        val refreshToken = TokenPref.refreshToken
        return try {
            val tokenResponse = runBlocking {
                api.refreshToken(refreshToken)
            }

            TokenPref.accessToken = tokenResponse.accessToken
            TokenPref.refreshToken = tokenResponse.refreshToken

            tokenResponse.accessToken
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}