package com.projetointegrador.pi_application.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.Constants.FIREBASE_ATTRIBUTES.CAMPAIGNS_COLLECTION
import com.projetointegrador.pi_application.utils.FirebaseResponse

class CampaignRepository {

    private var fireStoreDataBase = FirebaseFirestore.getInstance()
    private lateinit var documentReference: DocumentReference

    fun createCampaign(campaign: Campaign): MutableLiveData<FirebaseResponse<Boolean>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<Boolean>>()

        documentReference = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION).document()
        campaign.campaignId = documentReference.id

        documentReference.set(campaign)
            .addOnSuccessListener {
                mutableLiveData.value = FirebaseResponse.Success(true)
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error adding document", exception)
                mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
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
            mutableLiveData.value = FirebaseResponse.Failure("Ocorreu um erro, tente novamente mais tarde")
        }

        return mutableLiveData
    }

    fun getCampaignsByUser(userId: String): MutableLiveData<FirebaseResponse<List<Campaign>>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<List<Campaign>>>()
        val campaignsList = arrayListOf<Campaign>()

        val campaignsRef = fireStoreDataBase.collection(CAMPAIGNS_COLLECTION)
        campaignsRef
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    campaignsList.add(document.toObject())
                }
                mutableLiveData.value = FirebaseResponse.Success(campaignsList)
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
                for (document in documents) {
                    campaignsList.add(document.toObject())
                }
                mutableLiveData.value = FirebaseResponse.Success(campaignsList)
            }
            .addOnFailureListener { exception ->
                mutableLiveData.value = FirebaseResponse.Failure(exception.message.toString())
            }

        return mutableLiveData
    }

}