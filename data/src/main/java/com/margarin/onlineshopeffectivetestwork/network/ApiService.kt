package com.margarin.onlineshopeffectivetestwork.network


import com.margarin.onlineshopeffectivetestwork.network.model.ProductsDto
import retrofit2.http.GET

interface ApiService {

    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProductList(): ProductsDto

}