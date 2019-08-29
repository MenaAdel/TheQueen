package com.example.thequeen.features.add_client

import com.example.thequeen.data.base_response.BaseResponse
import io.reactivex.Single
import retrofit2.Response

class AddClientApi(val endpoint: AddClientEndPoint): IAddClientApi {
    override fun postCLient(body: AddClientBody): Single<Response<AddClientResponse>> {
        return endpoint.postCLient(body)
    }

}