package com.clarkelamothe.echojournal.memo.data

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.clarkelamothe.echojournal.memo.domain.AudioPlayer
import com.clarkelamothe.echojournal.memo.domain.FileManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.milliseconds

class AudioPlayerImpl(
    private val context: Context,
    private val fileManager: FileManager
) : AudioPlayer {
    private var player: MediaPlayer? = null

    override fun init(filePath: String) {
        val file = fileManager.getFile(filePath)
        player = MediaPlayer.create(
            context,
            file?.toUri()
        )
    }

    override fun start(onComplete: () -> Unit) {
        player?.start()
        player?.setOnCompletionListener {
            onComplete()
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

    override fun duration(): Duration {
        return player?.duration?.milliseconds ?: ZERO
    }

    override fun observerPosition(
        start: Boolean,
    ) = flow {
        player?.let { player ->
            if (player.isPlaying) {
                while (start) {
                    emit(player.currentPosition.milliseconds)
                    delay(100.milliseconds)
                }
            } else {
                emit(ZERO)
            }
        }
    }
}