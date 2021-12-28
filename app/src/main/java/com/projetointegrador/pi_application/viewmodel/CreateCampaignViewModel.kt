package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.models.Campaign
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class CreateCampaignViewModel : ViewModel() {

    var fireStoreDataBase = FirebaseFirestore.getInstance()
    private lateinit var documentReference: DocumentReference

    fun createCampaign(campaign: Campaign) {
//        if ()
//        documentReference = fireStoreDataBase.collection()
    }

}