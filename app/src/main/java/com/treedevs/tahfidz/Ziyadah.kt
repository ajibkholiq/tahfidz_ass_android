 package com.treedevs.tahfidz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.treedevs.tahfidz.adapters.suratAdapter
import com.treedevs.tahfidz.callback.NoSuratCallback
import com.treedevs.tahfidz.callback.evaluasiCallback
import com.treedevs.tahfidz.models.Nilai
import com.treedevs.tahfidz.networks.Surat

 class Ziyadah : AppCompatActivity() , NoSuratCallback,evaluasiCallback{

     private var spiner : Spinner? = null
     lateinit var EdtNama : EditText
     lateinit var EdtKefasihan : EditText
     lateinit var EdtKelancaran : EditText
     lateinit var EdtTajwid : EditText
     lateinit var EdtRemark : EditText
     lateinit var nama : String
     lateinit var noSurat : String
     lateinit var kefasihan : String
     lateinit var kelancaran : String
     lateinit var tajwid : String
     lateinit var remark : String
     private lateinit var txRekam : TextView
     private lateinit var audioName : String
     private lateinit var audioRecorder: AudioRecorder

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_ziyadah)
         audioRecorder = AudioRecorder()
         //EditText
         EdtNama = findViewById(R.id.EdtNama)
         EdtKefasihan = findViewById(R.id.EdtKefasihan)
         EdtKelancaran = findViewById(R.id.EdtKelancaran)
         EdtTajwid = findViewById(R.id.EdtTajwid)
         EdtRemark= findViewById(R.id.EdtCatatan)
         txRekam = findViewById(R.id.txRekam)
//         button
         val btnSimpan : Button = findViewById(R.id.btnSimpan)
         val btnStart : Button = findViewById(R.id.btnRekam)
         val btnStop: Button = findViewById(R.id.btnStop)
         btnStart.visibility = View.VISIBLE
         btnStop.visibility = View.GONE
         txRekam.visibility = View.GONE
         val nfcOrScanResult = intent.getStringExtra("SCAN_RESULT")
         spiner = findViewById(R.id.spinner)
         nama =nfcOrScanResult.toString()
         EdtNama.setText(nama)
         // ambil data surat
         Surat.getListSurat(this,this)

         btnSimpan.setOnClickListener{
             kefasihan = EdtKefasihan.text.toString()
             kelancaran = EdtKelancaran.text.toString()
             tajwid = EdtTajwid.text.toString()
             remark = EdtRemark.text.toString()
             Surat.postHafalan(nama,noSurat,kefasihan,tajwid,kelancaran,audioName,remark,this)
         }

         btnStart.setOnClickListener {
             audioRecorder.startRecording(this)
             btnStart.visibility = View.GONE
             btnStop.visibility = View.VISIBLE
             txRekam.visibility = View.VISIBLE

         }

         btnStop.setOnClickListener {
             audioRecorder.stopRecording()
             audioRecorder.getRecordingFilePath()?.let {
                 Surat.uploadAudioFile(it,this)
             }
             btnStart.visibility = View.VISIBLE
             btnStop.visibility = View.GONE
             txRekam.visibility = View.GONE

         }

    }

     override fun onFetchData(data: List<com.treedevs.tahfidz.models.surat>?) {
         val adapter = data?.let { suratAdapter(this, it) }
         spiner?.adapter = adapter
         spiner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                 val selectedDocument = parent.getItemAtPosition(position) as com.treedevs.tahfidz.models.surat
                 noSurat = selectedDocument.no_surat
             }
             override fun onNothingSelected(parent: AdapterView<*>) {
             }
         }
     }

     override fun onResponseResult(message: String?) {
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
         finish()
     }

     override fun onGetTranscript(ayat: String?) {
         val text = ayat
         audioRecorder.getRecordingFilePath()?.let {
             val namaa = EdtNama.text.toString()
             Surat.evaluasi(it,noSurat,namaa,text,this)

             }
         }
     override fun setEdt(data: Nilai?, audioName : String?) {
         this.audioName = audioName.toString()
         EdtKefasihan.setText(data?.kefasihan)
         EdtKelancaran.setText(data?.kelancaran)
         EdtTajwid.setText(data?.tajwid)
     }
     }


