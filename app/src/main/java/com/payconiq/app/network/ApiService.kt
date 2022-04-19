package com.payconiq.app.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.payconiq.app.network.model.response.SearchUserResponse
import com.payconiq.app.network.model.response.UserListResponseModel
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @GET(RequestURL.SERVICE_USER_LIST)
    fun requestUserListAsync(): Deferred<ArrayList<UserListResponseModel>>

    @GET(RequestURL.SERVICE_USER_LIST+"/{userName}")
    fun requestUserDetailsAsync(@Path("userName") userName: String): Deferred<UserListResponseModel>

    @GET(RequestURL.SERVICE_SEARCH_USER)
    fun requestSearchUsersAsync(@Query("q") query: String): Deferred<SearchUserResponse>

    companion object {

        operator fun invoke(
            context: Context,
        ): ApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->

                    val request = chain.request()
                    val requestBuilder = request.newBuilder()
                    chain.proceed(requestBuilder.build())
                }
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }
}