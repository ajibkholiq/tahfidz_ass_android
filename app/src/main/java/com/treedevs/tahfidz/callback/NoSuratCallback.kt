package com.treedevs.tahfidz.callback

import com.treedevs.tahfidz.models.surat

interface NoSuratCallback {
    fun onFetchData(data: List<surat>?)
    fun onResponseResult(message: String?)
    fun onGetTranscript(ayat: String?)
}