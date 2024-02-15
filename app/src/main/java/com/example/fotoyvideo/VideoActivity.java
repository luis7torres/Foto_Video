package com.example.fotoyvideo;


import static com.example.fotoyvideo.R.id.btnCapturarVideo2;
import static com.example.fotoyvideo.R.id.btnEnviarVideo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import android.widget.Button;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        init();

        findViewById(btnCapturarVideo2).setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
            }
        });

        findViewById(btnEnviarVideo)
                .setOnClickListener(v -> shareVideo());

    }

    private void shareVideo() {


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_STREAM, videoUri);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void init() {
        this.videoView = findViewById(R.id.videoView);
    }


    private  Uri videoUri;
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
             videoUri = intent.getData();
            if (videoUri != null) {
                videoView.setVideoURI(videoUri);
                videoView.start();
            }

        }
    }
}