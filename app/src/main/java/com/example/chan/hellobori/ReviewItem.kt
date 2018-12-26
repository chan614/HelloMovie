package com.example.chan.hellobori

data class ReviewItem (
    var userId : String,
    var reviewDate : String,
    var rate : Float,
    var mainText : String,
    var good : Int,
    var userProfile : String
)