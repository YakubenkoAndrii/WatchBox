package com.sample.project.watchbox.utils.interceptors

import com.sample.project.watchbox.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("apiKey", Constants.API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
