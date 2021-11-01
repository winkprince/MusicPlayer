package com.example.buttonnavigation;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Love extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4;
    private TextView text_app;



    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "皮卡丘：再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            //彻底关闭整个APP
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {// android2.1
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        text_app = findViewById(R.id.text_app);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/xingka.ttf");
        text_app.setTypeface(typeface);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);


        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.love);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.love:

                        return true;
                    case R.id.listen:
                        startActivity(new Intent(getApplicationContext(), Listen.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
    }



    public void gotoPersonActivity(View view) {
        Intent intent = new Intent(Love.this,PersonActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton1:
                Intent intent = new Intent(Love.this,MeActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButton2:
                Intent intent1 = new Intent(Love.this,DownloadActivity.class);
                startActivity(intent1);
                break;
            case R.id.imageButton3:
                Intent intent2 = new Intent(Love.this,RecentActivity.class);
                startActivity(intent2);
                break;
            case R.id.imageButton4:
                Intent intent3 = new Intent(Love.this,MyLoveActivity.class);
                startActivity(intent3);
                break;
        }

    }
}