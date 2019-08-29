package com.example.thequeen.features.choose_partner

import io.reactivex.Single
import retrofit2.Response

interface IChoosePartnerRepository {
    fun fetchDiscoveries(): Single<Response<ChoosePartnerResponse>>
}