package com.example.buttonnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonActivity extends AppCompatActivity {
    private TextView textView1,textView2,textView3,textView4,textView5;
    private Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        Typeface  typeface = Typeface.createFromAsset(getAssets(),"font/xingka.ttf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);


        button1.setTypeface(typeface);
        button2.setTypeface(typeface);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(PersonActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}