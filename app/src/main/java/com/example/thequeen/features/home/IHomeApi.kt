package com.example.thequeen.features.home

import com.example.thequeen.data.base_response.BaseResponse
import com.example.thequeen.features.home.colors.ColorsBody
import com.example.thequeen.features.home.colors.ColorsResponse
import com.example.thequeen.features.home.products.ProductsBody
import com.example.thequeen.features.home.products.ProductsResponse
import com.example.thequeen.features.home.secondStepActivity.AddProductBody
import io.reactivex.Single
import retrofit2.Response

interface IHomeApi {
    fun fetchHome(): Single<Response<HomeResponse>>

    fun fetchProduct(body: ProductsBody): Single<Response<ProductsResponse>>

    fun fetchColor(body: ColorsBody): Single<Response<ColorsResponse>>

    fun postOrder(body: AddProductBody): Single<Response<BaseResponse>>
}