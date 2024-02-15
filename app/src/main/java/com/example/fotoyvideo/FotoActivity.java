package com.example.fotoyvideo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FotoActivity extends AppCompatActivity {

    static final int peticion_camara = 100;
    static final int  peticion_foto = 102;
    String FotoPath;
    ImageView imageView;
    Button btn_capturarFoto, btn_enviarFoto;

    Uri uriFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        imageView = (ImageView) findViewById(R.id.imageView);
        btn_capturarFoto = (Button)  findViewById(R.id.btnCapturarFoto);
        btn_enviarFoto = (Button)  findViewById(R.id.btnEnviarFoto);

        //Evento para capturar la foto
        btn_capturarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permisos();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }

        });

        //Evento para compartir la foto
        btn_enviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                shareImage(bitmap);


            };

        })
    ;}

    private void shareImage(Bitmap bitmap) {

        Uri uri = getImageToShare(bitmap);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private Uri getImageToShare(Bitmap bitmap) {

       boolean isAnImageInImageView = imageView.getDrawable() == null;
       if(isAnImageInImageView){
           Toast.makeText(getApplicationContext(),
                   "No image to share", Toast.LENGTH_SHORT)
                   .show();
           return null;
       }

       String path = MediaStore.Images.Media
               .insertImage(getContentResolver(), bitmap, "image", null);

       Uri sharableUri = Uri.parse(path);

       return sharableUri;
    }


    private void Permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    peticion_camara);
        }
        else
        {
            tomarfoto();
        }
    }

    private void tomarfoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, peticion_foto);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
    int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_camara)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                tomarfoto();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isOk = resultCode == RESULT_OK;
        if (isOk) {

            boolean fotoREquestIsOk = requestCode == peticion_foto;
            if (fotoREquestIsOk) {
                Bundle extras = data.getExtras();
                Bitmap imagen = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imagen);
                imageView.setImageURI(data.getData());
            }


        }

    }
}