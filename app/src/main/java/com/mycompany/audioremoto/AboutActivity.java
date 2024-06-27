package com.mycompany.audioremoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    TextView textAboutApp, txDescriptionAbout;
    Button buttonInitialize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textAboutApp = findViewById(R.id.textAboutApp);
        txDescriptionAbout = findViewById(R.id.txDescriptionAbout);
        buttonInitialize = findViewById(R.id.buttonInitialize);

        buttonInitialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, InitializeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
