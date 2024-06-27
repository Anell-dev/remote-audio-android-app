package com.mycompany.audioremoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class InitializeActivity extends AppCompatActivity {

    Button buttonEscuchar;
    EditText txtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        buttonEscuchar = findViewById(R.id.buttonEscuchar);
        txtUrl = findViewById(R.id.txtUrl);

        buttonEscuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitializeActivity.this, ResultActivity.class);
                intent.putExtra("audioUrl", txtUrl.getText().toString());
                startActivity(intent);
            }
        });
    }
}