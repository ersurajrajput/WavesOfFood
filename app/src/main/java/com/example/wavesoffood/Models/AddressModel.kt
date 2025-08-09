package com.example.wavesoffood.Models

class AddressModel(
    var id: String ?= null,
    var street : String ? = null,
    var pincode : Int? = null,
    var apartmentNo : String?=null,
    var fullAdd : String?=null,
    var label : String?=null,
    var latitude : Double?=null,
    var longitude: Double?=null,
    var owner : String ? = null
)