package com.example.thequeen.features.home

class HomeResponse {
    val status: Int = 0
    val categories: List<Categories> = listOf()
}

data class Color(val id: Int ,val name: String ,val code: String ,val created_at: String)

data class Cloth(var id: Int, val price: String, val name: String, val created_at: String)

data class Product(val id: Int ,val name: String ,val category_id: Int ,val created_at: String ,
                   val color: List<Color> ,val cloth: List<Cloth> ,val image: List<Image>)

data class Categories(val id: Int ,val name: String ,val created_at: String ,val products: List<Product>)

data class Image(val id: Int ,val image: String ,val product_id: Int ,val created_at: String)