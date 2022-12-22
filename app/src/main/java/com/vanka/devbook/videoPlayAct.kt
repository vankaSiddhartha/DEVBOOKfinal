package com.vanka.devbook

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class videoPlayAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        var video = findViewById<VideoView>(R.id.videoView)
        var k = intent.getStringExtra("link")

        val uri: Uri = Uri.parse(k)
        video.setVideoURI(uri);
        val mediaController = MediaController(this)
        mediaController.setMediaPlayer(video);
        video.setMediaController(mediaController);
        video.start();
    }
}