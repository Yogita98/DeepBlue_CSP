package com.example.android.potholedetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class shoe_size extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoesize);

        Button b1 = (Button) findViewById(R.id.checkstatus);
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int shoesize;
                EditText edit = (EditText)findViewById(R.id.entersize);
                String shoesize1 = edit.getText().toString(); //gets you the contents of edit text
                if(shoesize1.matches(""))
                    shoesize = 0;
                else
                    shoesize = Integer.parseInt(shoesize1);
                Intent in = new Intent(shoe_size.this, edittopimage.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Shoe_size", shoesize);
                in.putExtras(bundle);
                startActivity(in);
                //startActivity(new Intent(shoe_size.this, edittopimage.class));

            }
        });

    }
}
