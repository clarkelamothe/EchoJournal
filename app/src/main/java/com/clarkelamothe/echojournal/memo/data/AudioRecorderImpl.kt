package com.clarkelamothe.echojournal.memo.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.clarkelamothe.echojournal.memo.domain.AudioRecorder
import java.io.File

class AudioRecorderImpl(
    private val context: Context
) : AudioRecorder {
    private var recorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(fileName: String): String {
        val extension = ".mp3"
//        val file = File(context.filesDir, "$fileName$extension").absolutePath
        val filePath = File.createTempFile("$fileName-", extension).absolutePath
        recorder = createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)
            prepare()
            start()
        }
        return filePath
    }

    override fun maxAmp() = recorder?.maxAmplitude?.toFloat() ?: 0f

    override fun pause() {
        recorder?.pause()
    }

    override fun resume() {
        recorder?.resume()
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder?.release()
        recorder = null
    }
}