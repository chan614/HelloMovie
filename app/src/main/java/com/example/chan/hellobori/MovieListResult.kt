package com.example.chan.hellobori

import com.google.gson.annotations.SerializedName

data class MovieListResult (
    var message : String,
    var code : Int,
    var resultType : String,
    var result : ArrayList<Movie>
)

data class MovieDetailResult (
    var message : String,
    var code : Int,
    var resultType : String,
    var result : ArrayList<MovieDetail>
)

data class MovieCommentResult (
    var message : String,
    var code : Int,
    var resultType : String,
    var result : ArrayList<UserComment>
)

data class CreateComment (
    var message : String,
    var code : Int,
    var resultType : String,
    var result : String
)

data class Movie (
    var id : String,
    var title : String,
    var title_eng : String,
    var date : String,
    var user_rating : String,
    var audience_rating : String,
    var reviewer_rating : String,
    var reservation_rate : String,
    var reservation_grade : String,
    var grade : String,
    var thumb : String,
    var image : String
)

data class MovieDetail (
    var title : String,
    var id :Int,
    var date : String,
    var user_rating : String,
    var audience_rating : String,
    var reviewer_rating : String,
    var reservation_rate : String,
    var reservation_grade : String,
    var grade : String,
    var thumb : String,
    var image : String,
    var photos : String,
    var videos : String,
    var outlinks :String,
    var genre :String,
    var duration :String,
    var audience : String,
    var synopsis : String,
    var director :String,
    var actor : String,
    var like : String,
    var dislike : String
)

data class UserComment(

    @SerializedName("writer")
    var writer : String,

    @SerializedName("time")
    var time: String,

    @SerializedName("rating")
    var rating : Float,

    @SerializedName("contents")
    var contents : String,

    @SerializedName("recommend")
    var recommend : Int,

    @SerializedName("writer_image")
    var writer_image : String,

    @SerializedName("total")
    var total : String
)

data class GalleryImage(
    var url : String,
    var yn : String?
)