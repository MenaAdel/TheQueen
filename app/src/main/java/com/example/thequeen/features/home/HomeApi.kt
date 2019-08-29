package com.example.thequeen.features.home

import com.example.thequeen.data.base_response.BaseResponse
import com.example.thequeen.features.home.colors.ColorsBody
import com.example.thequeen.features.home.colors.ColorsResponse
import com.example.thequeen.features.home.products.ProductsBody
import com.example.thequeen.features.home.products.ProductsResponse
import com.example.thequeen.features.home.secondStepActivity.AddProductBody
import io.reactivex.Single
import retrofit2.Response

class HomeApi(val endpoint: HomeEndPoint): IHomeApi {

    override fun fetchColor(body: ColorsBody): Single<Response<ColorsResponse>> {
        return endpoint.fetchProduct(body)
    }

    override fun fetchProduct(body: ProductsBody): Single<Response<ProductsResponse>> {
        return endpoint.fetchProduct(body)
    }

    override fun fetchHome(): Single<Response<HomeResponse>> {
        return endpoint.fetchHome()
    }

    override fun postOrder(body: AddProductBody): Single<Response<BaseResponse>> {
        return endpoint.postOrder(body)
    }

}