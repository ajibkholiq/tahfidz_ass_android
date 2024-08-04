package com.treedevs.tahfidz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class Scanner : AppCompatActivity() {
    private lateinit var barcodeView: DecoratedBarcodeView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        barcodeView = findViewById(R.id.zxing_barcode_scanner)

        barcodeView.decodeContinuous(callback)
        val btnCancel: Button = findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            result?.let {
                val intent = Intent().apply {
                    putExtra("SCAN_RESULT", it.text)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}