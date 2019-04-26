package br.com.freeleft.agenda.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor



class RetrofitAPI<T> {

    fun getClient(c: Class<T>) : T {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-teste-agenda.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkhttpClient())
            .build()

        return retrofit.create(c)
    }
}

fun getOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()
}

fun getAgendaAPI(): AgendaAPI {
    return RetrofitAPI<AgendaAPI>()
        .getClient(AgendaAPI::class.java)
}