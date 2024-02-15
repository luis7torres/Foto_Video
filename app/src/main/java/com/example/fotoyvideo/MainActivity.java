package com.example.fotoyvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_tomarFoto, btn_tomarVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_tomarFoto = (Button) findViewById(R.id.btnTomarFoto);
        btn_tomarVideo = (Button) findViewById(R.id.btnTomarVideo);

        //Evento para boton de tomar foto
        btn_tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FotoActivity.class);
                startActivity(intent);

            }
        });

        //Evento para boton de tomar video
        btn_tomarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                startActivity(intent);

            }
        });

    }
}