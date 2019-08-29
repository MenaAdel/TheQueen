package com.example.thequeen.features.choose_partner

import io.reactivex.Single
import retrofit2.Response

interface IChoosePartnerApi {
    fun fetchDiscoveries(): Single<Response<ChoosePartnerResponse>>

}