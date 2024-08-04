package com.treedevs.tahfidz.networks

import com.treedevs.tahfidz.models.responseApi
import com.treedevs.tahfidz.models.responseEvaluasi
import com.treedevs.tahfidz.models.responseSurat
import com.treedevs.tahfidz.models.surat
import com.treedevs.tahfidz.models.transkrip
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @GET("surat")
    fun getSurat(): Call<responseSurat<surat>>

    @FormUrlEncoded
    @POST("hafalan")
    fun postHafalan(
        @Field("siswa") nama: String,
        @Field("surat") surat: String,
        @Field("kefasihan") kefasihan: String,
        @Field("tajwid") tajwid: String,
        @Field("kelancaran") kelancaran : String,
        @Field("audio") audio : String?,
        @Field("remark") remark : String,
    ) : Call<responseApi>

    @Headers("Authorization: Bearer sk-proj-J3tlzCnqqPmlI3LCNaYtT3BlbkFJNvEBsBmrHHDrnJV54PZK")
    @Multipart
    @POST("transcriptions")
    fun transkripAudio(
        @Part file: MultipartBody.Part,
        @Part model : MultipartBody.Part,
        @Part bahasa: MultipartBody.Part
    ) : Call<transkrip>

    @Multipart
    @POST("evaluasi")
    fun evaluasi(
        @Part file: MultipartBody.Part,
        @Part siswa: MultipartBody.Part,
        @Part nosurat: MultipartBody.Part,
        @Part text: MultipartBody.Part,
    ) : Call<responseEvaluasi>

}
