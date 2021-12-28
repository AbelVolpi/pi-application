package com.projetointegrador.pi_application.models

data class Campaign(
    val id: String,
    val userId: String,
    val name: String,
    val campaignAddress: String,
    val campaignCategory: String
)
