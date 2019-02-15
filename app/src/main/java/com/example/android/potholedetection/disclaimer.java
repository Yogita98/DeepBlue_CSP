package com.example.android.potholedetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class disclaimer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer);


    Button b1 = (Button) findViewById(R.id.btn_ok);

b1.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        Intent in = new Intent(disclaimer.this, shoe_size.class);
        startActivity(in);


    }
});
    }
}