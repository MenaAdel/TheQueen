package com.example.thequeen.features.add_client

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddClientEndPoint {
    @POST("addClient")
    fun postCLient(@Body body: AddClientBody): Single<Response<AddClientResponse>>
}