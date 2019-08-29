package com.example.thequeen.features.home

import com.example.thequeen.data.base_response.BaseResponse
import com.example.thequeen.features.home.colors.ColorsBody
import com.example.thequeen.features.home.colors.ColorsResponse
import com.example.thequeen.features.home.products.ProductsBody
import com.example.thequeen.features.home.products.ProductsResponse
import com.example.thequeen.features.home.secondStepActivity.AddProductBody
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeEndPoint {
    @POST("home")
    fun fetchHome(): Single<Response<HomeResponse>>

    @POST("product")
    fun fetchProduct(@Body body: ProductsBody): Single<Response<ProductsResponse>>

    @POST("color")
    fun fetchProduct(@Body body: ColorsBody): Single<Response<ColorsResponse>>

    @POST("addOrder")
    fun postOrder(@Body body: AddProductBody): Single<Response<BaseResponse>>
}