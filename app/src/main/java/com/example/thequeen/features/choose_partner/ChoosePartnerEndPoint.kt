package com.example.thequeen.features.choose_partner

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST

interface ChoosePartnerEndPoint {
    @POST("clients")
    fun fetchDiscoveries(): Single<Response<ChoosePartnerResponse>>
}