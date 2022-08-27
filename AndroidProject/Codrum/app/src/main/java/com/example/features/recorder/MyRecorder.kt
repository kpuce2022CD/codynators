package com.example.features.recorder

import android.media.MediaRecorder
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log

class MyRecorder : MediaRecorder() {

    lateinit var filePath: String

    fun initRecorder() {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
    }

    fun startRecording() {
        initRecorder()
        setOutputFile(filePath)
        runCatching {
            prepare()
        }.onFailure {
            Log.e("record", "startRecording: $it")
        }
        start()
    }

    fun stopRecording(flag: Boolean) {
        if (flag) {
            stop()
        } else {
            throw Exception("녹음중이 아님")
        }
    }

    fun setRecordNmae(name: String) {
        filePath =
            "${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/" + name + ".mp4"
    }


    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}