package raum.muchbeer.remotemediatorpagektx.data.remote.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import raum.muchbeer.remotemediatorpagektx.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DataInstance {

    companion object {

        // val BASE_URL = "https://api.themoviedb.org/3/"

        val httpLogger = HttpLoggingInterceptor().apply {
            this.level =   if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                                else HttpLoggingInterceptor.Level.NONE}

        val client = OkHttpClient.Builder().apply {

            this.addInterceptor(httpLogger)
                .retryOnConnectionFailure(true)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
        }.build()
    }


    fun dataInstance() : DataService {
        Log.i("RetrofitInstance", "The application has access the Mteja")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }


}