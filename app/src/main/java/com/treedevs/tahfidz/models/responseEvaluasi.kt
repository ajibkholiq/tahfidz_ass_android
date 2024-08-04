package com.treedevs.tahfidz.models

class responseEvaluasi(
    val status : String,
    val data : Data
)
data class Data(
    val nilai: Nilai,
    val surat: Surat,
    val audio: String
)
data class Nilai(
    val kefasihan : String,
    val tajwid : String,
    val kelancaran : String
)

data class Surat(
    val pembanding: String,
    val input: String
)

