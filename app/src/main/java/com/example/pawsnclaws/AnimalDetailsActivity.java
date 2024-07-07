package com.example.pawsnclaws;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class AnimalDetailsActivity extends AppCompatActivity {

    private ImageView animalImageView;
    private TextView infoTextView;
    private Button playSoundButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);

        animalImageView = findViewById(R.id.animalImageView);
        infoTextView = findViewById(R.id.infoTextView);
        playSoundButton = findViewById(R.id.playSoundButton);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("imageUrl");
        String info = intent.getStringExtra("info");
        String soundUrl = intent.getStringExtra("soundUrl");

        // Log the received data to verify
        Log.d("AnimalDetailsActivity", "Name: " + name);
        Log.d("AnimalDetailsActivity", "ImageUrl: " + imageUrl);
        Log.d("AnimalDetailsActivity", "Info: " + info);
        Log.d("AnimalDetailsActivity", "SoundUrl: " + soundUrl);

        new ImageLoadTask(imageUrl, animalImageView).execute();
        infoTextView.setText(info);

        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(soundUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    private static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
