package com.example.thequeen.features.choose_partner

import io.reactivex.Single
import retrofit2.Response

class ChoosePartnerApi(val endpoint: ChoosePartnerEndPoint): IChoosePartnerApi{
    override fun fetchDiscoveries(): Single<Response<ChoosePartnerResponse>> {
        return endpoint.fetchDiscoveries()
    }
}