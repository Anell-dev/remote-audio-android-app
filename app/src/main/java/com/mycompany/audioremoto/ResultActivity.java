package com.mycompany.audioremoto;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mycompany.audioremoto.Adapradores.AudioAdapter;
import com.mycompany.audioremoto.Clases.AudioItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    private MediaPlayer mediaPlayer;
    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    private List<AudioItem> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String hostingUrl = getIntent().getStringExtra("audioUrl");

        recyclerView = findViewById(R.id.recyclerViewAudios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        audioList = new ArrayList<>();
        audioAdapter = new AudioAdapter(audioList, this::playAudio);
        recyclerView.setAdapter(audioAdapter);

        mediaPlayer = new MediaPlayer();
        executorService = Executors.newSingleThreadExecutor();

        fetchAudioUrls(hostingUrl);
    }

    private void fetchAudioUrls(String url) {
        executorService.execute(() -> {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements audioElements = doc.select("a[href$=.mp3]");
                for (Element element : audioElements) {
                    String audioUrl = element.attr("href");
                    String audioTitle = element.text();
                    audioList.add(new AudioItem(audioTitle, audioUrl));
                }
            } catch (IOException e) {
                Log.e(TAG, "Error fetching audio URLs", e);
            }

            runOnUiThread(() -> audioAdapter.notifyDataSetChanged());
        });
    }

    private void playAudio(AudioItem audioItem) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            mediaPlayer.setDataSource(audioItem.getUrl());
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                Toast.makeText(ResultActivity.this, "Playing: " + audioItem.getTitle(), Toast.LENGTH_SHORT).show();
            });
            mediaPlayer.setOnCompletionListener(mp -> Toast.makeText(ResultActivity.this, "Audio Completed", Toast.LENGTH_SHORT).show());
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(ResultActivity.this, "Error playing audio: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
                return true;
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG, "Error playing audio", e);
            Toast.makeText(this, "Error playing audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}