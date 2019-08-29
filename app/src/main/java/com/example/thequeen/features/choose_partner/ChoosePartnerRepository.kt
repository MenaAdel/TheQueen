package com.example.thequeen.features.choose_partner

import io.reactivex.Single
import retrofit2.Response

class ChoosePartnerRepository(val api: IChoosePartnerApi): IChoosePartnerRepository {
    override fun fetchDiscoveries(): Single<Response<ChoosePartnerResponse>> {
        return api.fetchDiscoveries()
    }
}