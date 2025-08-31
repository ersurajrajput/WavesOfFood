package com.ersurajrajput.wavesoffood.models

data class OrderModel(
    var orderId: String?=null,
    var orderQuantity: Int?=null,
    var resId: String?=null,
    var resName: String?=null,
    var userName: String?=null,
    var foodName: String?=null,
    var totalPrice: Int?=null,
    var orderStatus: String?=null,
    var foodImg: String?=null,
)
