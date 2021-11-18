package br.com.spaceflight.di

import android.app.Application
import br.com.spaceflight.data.remote.SpaceService
import br.com.spaceflight.data.repository.SpaceRepositoryImpl
import br.com.spaceflight.domain.repository.SpaceRepository
import br.com.spaceflight.domain.use_case.list.ListArticlesUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideListArticlesUseCase(repository: SpaceRepository): ListArticlesUseCase {
        return ListArticlesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSpaceRepositoryImp(
        service: SpaceService,
        context: Application
    ): SpaceRepository {
        return SpaceRepositoryImpl(service, context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    @ExperimentalSerializationApi
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SpaceService.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideSpaceService(retrofit: Retrofit): SpaceService {
        return retrofit.create(SpaceService::class.java)
    }
}