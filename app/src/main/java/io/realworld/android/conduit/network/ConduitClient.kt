package io.realworld.android.conduit.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realworld.android.conduit.BuildConfig
import io.realworld.android.conduit.data.UserPreferences
import io.realworld.android.conduit.data.UserPreferences.Companion.ACCESS_TOKEN
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ConduitClient @Inject constructor(@ApplicationContext context: Context) {

    @Inject
    lateinit var preferences: UserPreferences


    companion object {
        private const val BASE_URL = "https://conduit.productionready.io/api/"
    }


    private val authInterceptor = Interceptor { chain ->
        var req = chain.request()
        runBlocking {
            preferences.getAccessToken()[ACCESS_TOKEN]?.let {
                req = req.newBuilder()
                    .header(
                        "Authorization",
                        "Token $it"
                    )
                    .build()
            }
        }
        chain.proceed(req)
    }


    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(api)
    }

    private fun getRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .also { client ->
                if (BuildConfig.DEBUG) {
                    client.addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                }
            }.build()
    }
}


