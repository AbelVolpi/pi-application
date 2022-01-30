package com.projetointegrador.pi_application.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.Constants.FirebaseAttributes.CAMPAIGNS_COLLECTION
import com.projetointegrador.pi_application.utils.Constants.FirebaseAttributes.CAMPAIGN_CATEGORY
import com.projetointegrador.pi_application.utils.Constants.FirebaseAttributes.USER_ID
import com.projetointegrador.pi_application.utils.FirebaseResponse

class CampaignRepository {

    private var fireStoreDataBase = FirebaseFirestore.getInstance()
    private lateinit var documentReference: DocumentReference

    private lateinit var storageReference: StorageReference
    private lateinit var storage: FirebaseStorage

    fun createCampaign(campaign: Campaign, imageUri: Uri?): MutableLiveData<FirebaseResponse<Boolean>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<Boolean>>()

        documentReference = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION).document()
        campaign.campaignId = documentReference.id

        imageUri?.let { imageUriNotNull ->

            storage = FirebaseStorage.getInstance()
            storageReference = storage.getReference("images/${campaign.campaignId}")

            storageReference.putFile(imageUriNotNull)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener {
                        campaign.campaignImageUrl = it.toString()
                        documentReference.set(campaign)
                            .addOnSuccessListener {
                                mutableLiveData.value = FirebaseResponse.Success(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Firestore", "Error adding document", exception)
                                mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Storage", "Error adding photo", exception)
                    mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
                }

        } ?: run {
            documentReference.set(campaign)
                .addOnSuccessListener {
                    mutableLiveData.value = FirebaseResponse.Success(true)
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error adding document", exception)
                    mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
                }
        }

        return mutableLiveData
    }

    fun deleteCampaign(campaignId: String): MutableLiveData<FirebaseResponse<Any>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<Any>>()

        try {
            val campaignsRef = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION)
            campaignsRef
                .document(campaignId)
                .delete()
                .addOnSuccessListener {
                    mutableLiveData.value = FirebaseResponse.Success(Any())
                }
                .addOnFailureListener { exception ->
                    mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
                }
        } catch (throwable: Throwable) {
            mutableLiveData.value = FirebaseResponse.Failure(throwable.message.toString())
        }

        return mutableLiveData
    }

    fun getCampaignsByUser(userId: String): MutableLiveData<FirebaseResponse<List<Campaign>>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<List<Campaign>>>()
        val campaignsList = arrayListOf<Campaign>()

        val campaignsRef = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION)
        campaignsRef
            .whereEqualTo(USER_ID, userId)
            .get()
            .addOnSuccessListener { documents ->
                try {
                    for (document in documents) {
                        campaignsList.add(document.toObject())
                    }
                    mutableLiveData.value = FirebaseResponse.Success(campaignsList)

                } catch (throwable: Throwable) {
                    mutableLiveData.value = FirebaseResponse.Failure(throwable.message.toString())
                }
            }
            .addOnFailureListener { exception ->
                mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
            }

        return mutableLiveData
    }

    fun getAllCampaigns(): MutableLiveData<FirebaseResponse<List<Campaign>>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<List<Campaign>>>()
        val campaignsList = arrayListOf<Campaign>()

        val campaignsRef = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION)
        campaignsRef
            .get()
            .addOnSuccessListener { documents ->
                try {
                    for (document in documents) {
                        campaignsList.add(document.toObject())
                    }
                    mutableLiveData.value = FirebaseResponse.Success(campaignsList)
                } catch (throwable: Throwable) {
                    mutableLiveData.value = FirebaseResponse.Failure(throwable.message.toString())
                }
            }
            .addOnFailureListener { exception ->
                mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
            }

        return mutableLiveData
    }

    fun getCampaignsByCategory(category: String): MutableLiveData<FirebaseResponse<List<Campaign>>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<List<Campaign>>>()
        val campaignsList = arrayListOf<Campaign>()

        val campaignsRef = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION)
        campaignsRef
            .whereEqualTo(CAMPAIGN_CATEGORY, category)
            .get()
            .addOnSuccessListener { documents ->
                try {
                    for (document in documents) {
                        campaignsList.add(document.toObject())
                    }
                    mutableLiveData.value = FirebaseResponse.Success(campaignsList)
                } catch (throwable: Throwable) {
                    mutableLiveData.value = FirebaseResponse.Failure(throwable.message.toString())
                }
            }
            .addOnFailureListener { exception ->
                mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
            }

        return mutableLiveData
    }

}