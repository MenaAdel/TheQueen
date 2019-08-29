package com.example.thequeen.features.login

import io.reactivex.Single
import retrofit2.Response

interface ILoginApi {
    fun getDiscoveries(body: LoginBody): Single<Response<LoginResponse>>
}