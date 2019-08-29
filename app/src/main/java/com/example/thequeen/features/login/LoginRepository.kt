package com.example.thequeen.features.login

import io.reactivex.Single
import retrofit2.Response

class LoginRepository(val api: ILoginApi): ILoginRepository {
    override fun getDiscoveries(body: LoginBody): Single<Response<LoginResponse>> {
        return api.getDiscoveries(body)
    }
}