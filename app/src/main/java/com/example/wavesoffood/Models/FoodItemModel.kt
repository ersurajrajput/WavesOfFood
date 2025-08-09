package com.example.wavesoffood.Models

data class FoodItemModel(
    var id : String ? = null,
    var foodName : String ? = null,
    var foodCat : String ? = null,
    var foodPrice : Int ? = null,
    var foodImg : String ? = null,
    var resId : String ? = null,
    var foodRating:Int? = null,
    var totalNoOfReviews: Int ? = null,

    )
