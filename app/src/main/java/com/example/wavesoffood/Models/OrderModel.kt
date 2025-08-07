package com.example.wavesoffood.Models

data class OrderModel(
    var id: String ? = null,
    var userId: String ? = null,
    var resId: String ? = null,
    var foodId: String ? = null,
    var orderStatus : String?=null,

)
