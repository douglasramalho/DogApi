package br.com.douglasmotta.dogapichallenge.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String
) : Interceptor {

    @Suppress("MagicNumber")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header(HEADER_PARAMETER_API_KEY, apiKey)

        return chain.proceed(
            newRequest.build()
        )
    }

    companion object {
        private const val HEADER_PARAMETER_API_KEY = "x-api-key"
    }
}