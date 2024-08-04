package com.treedevs.tahfidz

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            openscan()
        }
        val scanButton: Button = findViewById(R.id.scan_button)
        scanButton.setOnClickListener {
           openscan()
        }
    }
    private fun openscan(){
        val integrator = IntentIntegrator(this)
        integrator.setCaptureActivity(Scanner::class.java)
        integrator.initiateScan()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val scanResult = result.contents
                if(scanResult.contains("https://e-tahfidz.ajibkholiq.my.id/capaian/")){

                    val kode = scanResult.replace("https://e-tahfidz.ajibkholiq.my.id/capaian/","")
                    val intent = Intent(this, Ziyadah::class.java).apply {
                        putExtra("SCAN_RESULT", kode)
                    }
                    startActivity(intent)
                }else
                Toast.makeText(this, "Gunakan QR Code Siswa Yang Terdaftar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val filters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                try {
                    addDataType("text/plain")
                } catch (e: IntentFilter.MalformedMimeTypeException) {
                    throw RuntimeException("Check your MIME type.")
                }
            },
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        )

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            tag?.let { readFromTag(it) }
        }
    }

    private fun readFromTag(tag: Tag) {
        val ndef = Ndef.get(tag) ?: return
        val ndefMessage = ndef.cachedNdefMessage
        val records = ndefMessage.records
        Log.d("test", records.joinToString(" , "))
        for (record in records) {
            val payload = record.payload
            val text = String(payload)
            if(text.contains("https://e-tahfidz.ajibkholiq.my.id/capaian/"))
            {
                val kode = text.replace("https://e-tahfidz.ajibkholiq.my.id/capaian/","")
                val intent = Intent(this, Ziyadah::class.java).apply {
                    putExtra("SCAN_RESULT", kode)
                }
                startActivity(intent)
            }else
            Toast.makeText(this, "Gunakan Kartu Siswa Yang Terdaftar", Toast.LENGTH_SHORT).show()
        }
    }
}