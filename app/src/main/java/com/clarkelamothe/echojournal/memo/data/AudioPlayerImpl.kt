package com.clarkelamothe.echojournal.memo.data

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.clarkelamothe.echojournal.memo.domain.AudioPlayer
import java.io.File

class AudioPlayerImpl(
    private val context: Context
) : AudioPlayer {
    private var player: MediaPlayer? = null

    override fun playFile(filePath: String, onComplete: () -> Unit) {
        val file = File(filePath)
        MediaPlayer.create(
            context,
            file.toUri()
        ).apply {
            setOnCompletionListener {
                onComplete()
            }

            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    override fun pause() {
        player?.pause()
    }

    override fun resume() {
        player?.start()
    }
}