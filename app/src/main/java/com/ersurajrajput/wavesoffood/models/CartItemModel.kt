package com.ersurajrajput.wavesoffood.models

data class CartItemModel(
    var id: String?=null,
    var foodName: String?=null,
    var foodImg: String?=null,
    var foodPrice: Int?=null,
    var foodQuantity: Int?=null,
    var userId: String?=null,
)
