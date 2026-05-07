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
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGeocoderRepository(@ApplicationContext appContext: Context) = GeocoderRepository(appContext)

    @Singleton
    @Provides
    fun provideUserRepository(supabaseClient: SupabaseClient) = UserRepository(supabaseClient)

    @Singleton
    @Provides
    fun provideCampaignRepository(
        supabaseClient: SupabaseClient,
        @ApplicationContext appContext: Context
    ) = CampaignRepository(supabaseClient, appContext)
}
