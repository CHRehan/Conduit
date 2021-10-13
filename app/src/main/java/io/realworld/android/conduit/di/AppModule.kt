package io.realworld.android.conduit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realworld.android.conduit.network.ConduitClient
import io.realworld.android.conduit.network.ConduitAPI
import io.realworld.android.conduit.network.ConduitAuthAPI
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideAuthApi(
        conduitClient: ConduitClient,
    ): ConduitAuthAPI {
        return conduitClient.buildApi(ConduitAuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun providePublicApi(
        conduitClient: ConduitClient
    ): ConduitAPI {
        return conduitClient.buildApi(ConduitAPI::class.java)
    }
}