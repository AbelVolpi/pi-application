package com.projetointegrador.pi_application.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.Constants
import com.projetointegrador.pi_application.utils.FirebaseResponse

class CampaignRepository {

    private var fireStoreDataBase = FirebaseFirestore.getInstance()
    private lateinit var documentReference: DocumentReference

    fun createCampaign(campaign: Campaign): MutableLiveData<FirebaseResponse<Boolean>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<Boolean>>()

        documentReference = fireStoreDataBase.collection(Constants.FIREBASE_ATTRIBUTES.CAMPAIGNS_COLLECTION).document()
        campaign.id = documentReference.id

        documentReference.set(campaign)
            .addOnSuccessListener {
                mutableLiveData.value = FirebaseResponse.Success(true)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding document", e)
                mutableLiveData.value = FirebaseResponse.Failure(e.message.toString())
            }

        return mutableLiveData
    }

}