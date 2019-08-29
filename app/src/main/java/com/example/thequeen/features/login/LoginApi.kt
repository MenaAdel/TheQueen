package com.example.thequeen.features.login

import io.reactivex.Single
import retrofit2.Response

class LoginApi(val endPoint: LoginEndPoint): ILoginApi {
    override fun getDiscoveries(body: LoginBody): Single<Response<LoginResponse>> {
        return endPoint.getDiscoveries(body)
    }
}