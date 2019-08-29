package com.example.thequeen.features.add_client

import io.reactivex.Single
import retrofit2.Response

interface IAddClientRepository {
    fun postCLient(body: AddClientBody): Single<Response<AddClientResponse>>
}