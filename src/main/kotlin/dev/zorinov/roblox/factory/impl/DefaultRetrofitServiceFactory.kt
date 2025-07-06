package dev.zorinov.roblox.factory.impl

import dev.zorinov.roblox.factory.RetrofitServiceFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DefaultRetrofitServiceFactory(
    val converterFactories: List<Converter.Factory> = listOf(GsonConverterFactory.create())
) : RetrofitServiceFactory {

    override fun <T> createService(
        serviceClass: Class<T>,
        baseUrl: String,
        client: OkHttpClient
    ): T {
        return createRetrofit(baseUrl, client).create(serviceClass)
    }

    fun createRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .apply {
                converterFactories.forEach { addConverterFactory(it) }
            }
            .build()
    }

    class Builder {
        private val converterFactories = mutableListOf<Converter.Factory>()

        fun addConverterFactory(factory: Converter.Factory): Builder {
            converterFactories.add(factory)
            return this
        }

        fun build(): RetrofitServiceFactory {
            return DefaultRetrofitServiceFactory(
                converterFactories.ifEmpty { listOf(GsonConverterFactory.create()) }
            )
        }
    }

    companion object {
        fun builder(): Builder = Builder()
    }
}