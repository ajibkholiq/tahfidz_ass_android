package com.treedevs.tahfidz.networks

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.treedevs.tahfidz.callback.NoSuratCallback
import com.treedevs.tahfidz.callback.evaluasiCallback
import com.treedevs.tahfidz.models.responseApi
import com.treedevs.tahfidz.models.responseEvaluasi
import com.treedevs.tahfidz.models.responseSurat
import com.treedevs.tahfidz.models.surat
import com.treedevs.tahfidz.models.transkrip
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Surat {
    companion object {
        fun getListSurat( contex : Context,callback : NoSuratCallback){
            ApiConfig.getApiService().getSurat().enqueue(object : Callback<responseSurat<surat>> {
                override fun onResponse(call: Call<responseSurat<surat>>, response: Response<responseSurat<surat>>) {
                    if (response.isSuccessful) {
                        val documents = response.body()?.data
                        callback.onFetchData(documents)
                    } else {
                        Toast.makeText(contex, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<responseSurat<surat>>, t: Throwable) {
                    Toast.makeText(contex, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
        }
        fun postHafalan(nama :String, noSurat: String,kefasihan:String,tajwid : String, lancar : String, audio :String? , catatan : String , callback: NoSuratCallback){
            ApiConfig.getApiService().postHafalan(nama,noSurat,kefasihan,tajwid,lancar,audio,catatan).enqueue(object : Callback<responseApi> {
                override fun onResponse(call: Call<responseApi>, response: Response<responseApi>) {
                    if (response.isSuccessful) {
                        val documents = response.body()?.message
                        callback.onResponseResult(documents)
                    }
                }

                override fun onFailure(call: Call<responseApi>, t: Throwable) {
                }
            })

        }

        fun uploadAudioFile(filePath: String,callback: NoSuratCallback) {
            val file = File(filePath)
            val requestFile = RequestBody.create("audio/mp4".toMediaTypeOrNull(), file)
            Log.d("http", file.name)
            val body = MultipartBody.Part.createFormData("file",file.name,requestFile)
            val model = MultipartBody.Part.createFormData("model","whisper-1")
            val laguage = MultipartBody.Part.createFormData("language","ar")

            val call = ApiConfig.getApiOpenAI().transkripAudio(body,model,laguage)
            call.enqueue(object : Callback<transkrip> {
                override fun onResponse(call: Call<transkrip>, response: Response<transkrip>) {
                    if (response.isSuccessful) {
                        val document = response.body()?.text
                        callback.onGetTranscript(document)
                        println("Upload successful")
                    } else {
                        println("Upload failed")
                    }
                }

                override fun onFailure(call: Call<transkrip>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }

        fun evaluasi(filePath: String, noSurat: String, siswa:String, text: String?, callback: evaluasiCallback){
            val file = File(filePath)
            val requestFile = RequestBody.create("audio/mp4".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("audio", file.name, requestFile)
            val s = MultipartBody.Part.createFormData("siswa", siswa)
            val ns = MultipartBody.Part.createFormData("no_surat", noSurat)
            val t = MultipartBody.Part.createFormData("text", text.toString())

            val call = ApiConfig.getApiService().evaluasi(body,ns,s,t)
            call.enqueue(object : Callback <responseEvaluasi> {
                override fun onResponse(
                    call: Call<responseEvaluasi>, response : Response <responseEvaluasi>) {
                    if (response.isSuccessful) {
                        val nilai = response.body()?.data?.nilai
                        val audioName = response.body()?.data?.audio
                        callback.setEdt(nilai,audioName)

                    } else {
                        println("Upload failed")
                    }
                }

                override fun onFailure(call: Call<responseEvaluasi>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }
    }


}


