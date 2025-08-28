package com.ersurajrajput.wavesoffood.models

data class NotificationModel(
    var notificationId: String ? = null,
    var notificationTitle: String?=null,
    var notificationImg: String?=null,
    var resId: String?=null,
    var notificationDate : String?=null,
)
