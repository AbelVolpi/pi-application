package com.example.testingfirebase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testingfirebase.models.Campaign
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