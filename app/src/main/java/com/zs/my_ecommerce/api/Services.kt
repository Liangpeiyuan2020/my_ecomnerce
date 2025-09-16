package com.zs.my_ecommerce.api

import com.zs.my_ecommerce.bean.MyResponse
import com.zs.my_ecommerce.bean.Product
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Services {
    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): Product

    @GET("products?limit=200")
    suspend fun getProducts(): MyResponse

    @GET("products/category/{category}")
    suspend fun getCategorizedProduct(@Path("category") category: String): MyResponse

    @GET("products/category-list")
    suspend fun getCategories(): List<String>

    @GET("products/search")
    suspend fun searchProduct(@Query("q") query: String): MyResponse

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        fun create(): Services {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Services::class.java)
        }
    }
}