package id.whynot.submission3.retrofitAndAPI

import id.whynot.submission3.request.FileUploadResponse
import id.whynot.submission3.request.RequestLogin
import id.whynot.submission3.request.RequestSignup
import id.whynot.submission3.response.PostResponse
import id.whynot.submission3.response.loginResponse
import id.whynot.submission3.response.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface API {

    @POST("login")
    @Headers("Authorization: token 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")
    fun login(
        @Body userRequest: RequestLogin
    ): Call<loginResponse>

    @POST("register")
    @Headers("Authorization: token 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")
    fun register(
        @Body userRequest: RequestSignup
    ): Call<SignupResponse>


    @GET("stories")
    fun getpost(
        @Header("AUTHORIZATION") value: String
    ): Call<PostResponse>

    @Multipart
    @POST("/v1/stories")
    fun uploadImage(
        @Header("AUTHORIZATION") value: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}