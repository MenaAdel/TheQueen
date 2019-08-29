package com.example.thequeen.features.login

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginEndPoint {
    @POST("login")
    fun getDiscoveries(@Body body: LoginBody): Single<Response<LoginResponse>>

}