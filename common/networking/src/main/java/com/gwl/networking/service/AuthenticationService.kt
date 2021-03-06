package com.gwl.networking.service

import com.gwl.model.ArticlesItem
import com.gwl.model.FeedResponse
import com.gwl.model.InstaFeed
import com.gwl.model.ResponseData
import com.gwl.networking.response.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface AuthenticationService {

    @GET("glide.json")
    fun getList(): Call<ApiResponse<ResponseData>>

    //079dac74a5f94ebdb990ecf61c8854b7
    @GET("v2/everything?q=movies&apiKey=51e2488cb9744482ac40ab958a9bd4b3")
    fun getMediaFeeds(
        @Query("page") page: Int = 1,
        @Query("pageSize") count: Int = 10
    ): Call<ApiResponse<List<ArticlesItem>>>

    // 3113853757.1677ed0.035f3e26d40745b2957ea09af1429049
    // 5415976503.1677ed0.c1969a92489940fbac11608b34bf8129
    @GET
    fun getInstaFeeds(
        @Url url :String="https://api.instagram.com/v1/users/self/media/recent/?access_token=3113853757.1677ed0.035f3e26d40745b2957ea09af1429049"
    ): Call<ApiResponse<List<InstaFeed>>>

    @GET("/v2/everything")
    fun fetchFeedList(
        @retrofit2.http.Query("q") q: String?,
        @retrofit2.http.Query("apiKey") apiKey: String?,
        @retrofit2.http.Query("page") page: Long,
        @retrofit2.http.Query("pageSize") pageSize: Int
    ): Call<FeedResponse>
}