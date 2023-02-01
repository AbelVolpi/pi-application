package com.projetointegrador.pi_application.core.di

import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.GeocoderRepository
import com.projetointegrador.pi_application.data.repository.UserRepository
import com.projetointegrador.pi_application.domain.campaign.CreateCampaignUseCase
import com.projetointegrador.pi_application.domain.campaign.DeleteCampaignUseCase
import com.projetointegrador.pi_application.domain.campaign.GetCampaignUseCase
import com.projetointegrador.pi_application.domain.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideCreateCampaignUseCase(
        campaignRepository: CampaignRepository,
        geocoderRepository: GeocoderRepository
    ) = CreateCampaignUseCase(campaignRepository, geocoderRepository)

    @Singleton
    @Provides
    fun provideDeleteCampaignUseCase(campaignRepository: CampaignRepository) = DeleteCampaignUseCase(campaignRepository)

    @Singleton
    @Provides
    fun provideGetCampaignUseCase(campaignRepository: CampaignRepository) = GetCampaignUseCase(campaignRepository)

    @Singleton
    @Provides
    fun provideDeleteAccountUseCase(userRepository: UserRepository) = DeleteAccountUseCase(userRepository)

    @Singleton
    @Provides
    fun provideForgotPasswordUseCase(userRepository: UserRepository) = ForgotPasswordUseCase(userRepository)

    @Singleton
    @Provides
    fun provideLoginUseCase(userRepository: UserRepository) = LoginUseCase(userRepository)

    @Singleton
    @Provides
    fun provideLogoutUseCase(userRepository: UserRepository) = LogoutUseCase(userRepository)

    @Singleton
    @Provides
    fun provideSignUpUseCase(userRepository: UserRepository) = SignUpUseCase(userRepository)
}
