package com.projetointegrador.pi_application.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.projetointegrador.pi_application.core.utils.Constants.SupabaseAttributes.CAMPAIGNS_TABLE
import com.projetointegrador.pi_application.core.utils.Constants.SupabaseAttributes.CAMPAIGN_CATEGORY
import com.projetointegrador.pi_application.core.utils.Constants.SupabaseAttributes.CAMPAIGN_ID
import com.projetointegrador.pi_application.core.utils.Constants.SupabaseAttributes.IMAGES_BUCKET
import com.projetointegrador.pi_application.core.utils.Constants.SupabaseAttributes.USER_ID
import com.projetointegrador.pi_application.core.utils.FirebaseResponse
import com.projetointegrador.pi_application.domain.models.Campaign
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class CampaignRepository(
    private val supabase: SupabaseClient,
    private val context: Context,
) {
    fun createCampaign(
        campaign: Campaign,
        imageUri: Uri?,
    ): LiveData<FirebaseResponse<Boolean>> =
        liveData {
            try {
                campaign.campaignId = UUID.randomUUID().toString()

                imageUri?.let { uri ->
                    val imageBytes =
                        context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                            ?: throw Exception("Could not read image")

                    supabase.storage.from(IMAGES_BUCKET).upload(campaign.campaignId, imageBytes, upsert = false)
                    campaign.campaignImageUrl = supabase.storage.from(IMAGES_BUCKET).publicUrl(campaign.campaignId)
                }

                supabase.from(CAMPAIGNS_TABLE).insert(campaign)
                emit(FirebaseResponse.Success(true))
            } catch (e: Exception) {
                Log.w("CampaignRepository", "Error creating campaign", e)
                emit(FirebaseResponse.Failure(e.message ?: "Error creating campaign"))
            }
        }

    fun deleteCampaign(campaignId: String): LiveData<FirebaseResponse<Any>> =
        liveData {
            try {
                supabase.from(CAMPAIGNS_TABLE).delete {
                    filter { eq(CAMPAIGN_ID, campaignId) }
                }
                emit(FirebaseResponse.Success(Any()))
            } catch (e: Exception) {
                emit(FirebaseResponse.Failure(e.message ?: "Error deleting campaign"))
            }
        }

    fun getCampaignsByUser(userId: String): LiveData<FirebaseResponse<List<Campaign>>> =
        liveData {
            try {
                val campaigns =
                    supabase.from(CAMPAIGNS_TABLE).select {
                        filter { eq(USER_ID, userId) }
                    }.decodeList<Campaign>()
                emit(FirebaseResponse.Success(campaigns))
            } catch (e: Exception) {
                emit(FirebaseResponse.Failure(e.message ?: "Error fetching campaigns"))
            }
        }

    fun getAllCampaigns(): LiveData<FirebaseResponse<List<Campaign>>> =
        liveData {
            try {
                val campaigns = supabase.from(CAMPAIGNS_TABLE).select().decodeList<Campaign>()
                emit(FirebaseResponse.Success(campaigns))
            } catch (e: Exception) {
                emit(FirebaseResponse.Failure(e.message ?: "Error fetching campaigns"))
            }
        }

    fun getCampaignsByCategory(category: String): LiveData<FirebaseResponse<List<Campaign>>> =
        liveData {
            try {
                val campaigns =
                    supabase.from(CAMPAIGNS_TABLE).select {
                        filter { eq(CAMPAIGN_CATEGORY, category) }
                    }.decodeList<Campaign>()
                emit(FirebaseResponse.Success(campaigns))
            } catch (e: Exception) {
                emit(FirebaseResponse.Failure(e.message ?: "Error fetching campaigns"))
            }
        }

    fun getCampaignById(campaignId: String): LiveData<FirebaseResponse<Campaign>> =
        liveData {
            try {
                val campaign =
                    supabase.from(CAMPAIGNS_TABLE).select {
                        filter { eq(CAMPAIGN_ID, campaignId) }
                    }.decodeSingle<Campaign>()
                emit(FirebaseResponse.Success(campaign))
            } catch (e: Exception) {
                emit(FirebaseResponse.Failure(e.message ?: "Error fetching campaign"))
            }
        }

    fun deleteAllCampaigns(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                supabase.from(CAMPAIGNS_TABLE).delete {
                    filter { eq(USER_ID, userId) }
                }
            }.onFailure { Log.e("CampaignRepository", "Error deleting all campaigns", it) }
        }
    }
}
