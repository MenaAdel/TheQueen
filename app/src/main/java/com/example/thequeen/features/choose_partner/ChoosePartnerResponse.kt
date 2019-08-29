package com.example.thequeen.features.choose_partner

import java.io.Serializable

class ChoosePartnerResponse : Serializable {
    val status: Int = 0
    val client: List<Client> = listOf()

}

data class Client(
    var id: Int = 0,
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val orders: Orders? = Orders()
) : Serializable

data class Orders(
    var total_length: Float = 0f, var skirt_lenth: Float = 0f, var size_of_Chest: Float = 0f, var chest_rotation: Float = 0f, val waist_rotation: Float = 0f,
    var size_of_waist: Float = 0f, var display_above_the_front_chest: Float = 0f, var display_over_the_bac_chest: Float = 0f, var between_nahdeen: Float = 0f,
    var length_of_the_penis: Float = 0f, var shoulder_width: Float = 0f, var display_back: Float = 0f, var length_of_the_back: Float = 0f,
    var size_of_back: Float = 0f, var back_rotation: Float = 0f,
    var rotation_of_the_pit: Float = 0f, var front_pit: Float = 0f, var back_pit: Float = 0f,
    var wrist_rotation: Float = 0f, var display_the_Zend: Float = 0f, var sleeve_length: Float = 0f
) : Serializable