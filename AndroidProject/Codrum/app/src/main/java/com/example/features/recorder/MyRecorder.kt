package com.example.features.recorder

import android.media.MediaRecorder
import android.os.Environment
import java.io.File

class MyRecorder : MediaRecorder() {
    private val sdcard = Environment.getExternalStorageDirectory()
    private val file = File(sdcard, "codrum_record.mp4")

    fun initRecorder() {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
    }

    fun startRecording() {
        initRecorder()
        setOutputFile(file.absolutePath)
        prepare()
        start()
    }

    fun stopRecording(flag: Boolean) {
        if (flag) {
            stop()
            release()
        } else {
            throw Exception("녹음중이 아님")
        }
    }

    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}