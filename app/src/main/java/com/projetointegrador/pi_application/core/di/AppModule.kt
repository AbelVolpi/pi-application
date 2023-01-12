package com.projetointegrador.pi_application.core.di

import android.content.Context
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.GeocoderRepository
import com.projetointegrador.pi_application.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository() = UserRepository()

    @Singleton
    @Provides
    fun provideGeocoderRepository(@ApplicationContext appContext: Context) = GeocoderRepository(appContext)

    @Singleton
    @Provides
    fun provideCampaignRepository() = CampaignRepository()

}