package com.example.stylish.utils.di

import android.app.Application
import android.content.Context
import com.example.stylish.data.service.AuthService
import com.example.stylish.data.service.MenuService
import com.example.stylish.data.service.MerchantService
import com.example.stylish.data.service.OrderService
import com.example.stylish.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    var BASE_URL = "https://57f4-114-122-209-191.ngrok-free.app/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(sharedPrefHelper: SharedPrefHelper) : AuthInterceptor {
        return AuthInterceptor(sharedPrefHelper)
    }

    @Provides
    @Singleton
    fun provideContext(application : Application) : Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL+"api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideSharedPrefHelper(context: Context) : SharedPrefHelper {
        return SharedPrefHelper(context)
    }


    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideMenuApiService(retrofit: Retrofit) : MenuService {
        return retrofit.create(MenuService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderApiService(retrofit: Retrofit) : OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    @Singleton
    fun provideMerchantApiService(retrofit: Retrofit) : MerchantService {
        return retrofit.create(MerchantService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}