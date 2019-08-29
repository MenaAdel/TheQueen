package com.example.thequeen.features.login

import io.reactivex.Single
import retrofit2.Response

interface ILoginRepository {
    fun getDiscoveries(body: LoginBody): Single<Response<LoginResponse>>
}