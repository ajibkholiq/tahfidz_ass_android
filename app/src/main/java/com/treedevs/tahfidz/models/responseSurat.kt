package com.treedevs.tahfidz.models

data class responseSurat<T>(
    val status: String,
    val data: List<T>? = null
)
