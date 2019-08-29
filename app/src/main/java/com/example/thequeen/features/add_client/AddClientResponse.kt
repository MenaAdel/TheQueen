package com.example.thequeen.features.add_client

data class AddClientResponse(val status: Int ,val client_id: Int = 1 ,val client_name: String ,val client_phone: String ,val client_address: String)