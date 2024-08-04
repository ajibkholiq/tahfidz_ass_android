package com.treedevs.tahfidz

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import java.io.IOException

class AudioRecorder {
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String? = null

    fun startRecording(context: Context) {
        val appContext = context.applicationContext
        outputFile = "${appContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/recording.mp3"

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile)

            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
            } catch (e: RuntimeException) {
                // Handle case where stop is called prematurely
                e.printStackTrace()
            }
            release()
        }
        mediaRecorder = null
    }

    fun getRecordingFilePath(): String? {
        return outputFile
    }
}