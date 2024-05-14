package ru.hse.smart_control.domain.connection.http.nest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://0a80-188-66-38-126.ngrok-free.app/"

interface NestApi {
    @POST("auth/register")
    suspend fun register(@Body user: RegisterModel): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body user: LoginModel): TokenModel
}

object RetrofitInstance {

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val nestApi: NestApi = retrofit.create(NestApi::class.java)
}