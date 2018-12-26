package com.example.chan.hellobori

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/movie/readMovieList")
    fun getRequest(): Call<MovieListResult>

    @GET("/movie/readMovie")
    fun getRequest(@Query("id") id : Int?) : Call<MovieDetailResult>

    @GET("/movie/readCommentList")
    fun getRequest(@Query("id") id : Int?, @Query("limit") limit : Int?) : Call<MovieCommentResult>

    @GET("/movie/createComment")
    fun getRequest(@Query("id") id : Int?,
                   @Query("writer") writer : String?,
                   @Query("rating") rating : Float?,
                   @Query("contents") contents : String?) : Call<CreateComment>

}