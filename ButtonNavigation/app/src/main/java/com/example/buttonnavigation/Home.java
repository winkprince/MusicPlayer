package com.example.buttonnavigation;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {



    int [] images ={R.drawable.img6,R.drawable.img2,R.drawable.img3,R.drawable.img4};
    CarouselView carouselView;
    VideoView videoView,videoView1 ;


    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
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




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);
//        videoView=findViewById(R.id.video_view);
//        videoView1 = findViewById(R.id.video_view1);
//        MediaController mc = new MediaController(this);
//        MediaController mc1 = new MediaController(this);
//        videoView.setMediaController(mc);
//        videoView1.setMediaController(mc1);
        //videoView.setVideoPath("/sdcard/forTest.mp4");//sdcard上的视频
//        videoView.setVideoPath("android.resource://com.example.buttonnavigation/"+ R.raw.zzz);
//        videoView.requestFocus();
//        videoView.start();
//        videoView1.setVideoPath("android.resource://com.example.buttonnavigation/"+ R.raw.zq);
//        videoView1.requestFocus();
//        videoView1.start();






        //底部导航
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.love:
                        startActivity(new Intent(getApplicationContext(), Love.class));
                        overridePendingTransition(0, 0);
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

//轮播图
    ImageListener imageListener =new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
        }
    };


}