package com.example.buttonnavigation;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerActivity extends AppCompatActivity  {

        private TextView songName,singerName;
        private ImageView diskImage,iv_needle;
        private SeekBar musicProgress;
        private  TextView currentTime, totalTime;
        private ImageButton btn_Prev,btn_Play,btn_Next;
        private Animation mPlayNeedleAnim, mStopNeedleAnim;
        private boolean isPlaying=true;
        private ObjectAnimator animator;
        private LikeButton likeButton;

        private DBHelper helper;
         private SimpleCursorAdapter adapter;

    int[] imgId = {
            R.drawable.photo2,
            R.drawable.photo5,
            R.drawable.photo10,
            R.drawable.photo9,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo6,
            R.drawable.photo7,
            R.drawable.photo11,
            R.drawable.photo8,
            R.drawable.images4,
            R.drawable.photo,
            R.drawable.photo12,
            R.drawable.photo14
    };


    String [] name ={"反方向的钟","someThing just like this","爱的魔法","My soul","Melancholy","Monster-Remix","你的爱","哪里都是你","泡沫Remix",
            "起风了","等你下课","我的歌声里","与你","追光者"};
    String [] artist={"九三","Chainsmokers","冷雪儿","July","melancho","Katie sky","wink","队长","Swang多雷","买辣椒也用券",
            "赵悦栖","兰琦","程jiajia","Aka-诉阳"};


        private ArrayList<Integer> playList = new ArrayList<>();
        private  MediaPlayer player ;
        private  int current ,nextCurrent ,preCurrent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        songName = findViewById(R.id.tv_song_name);
        singerName =findViewById(R.id.tv_singerName);
        diskImage = findViewById(R.id.iv_disk);
        iv_needle = findViewById(R.id.iv_needle);
        musicProgress = findViewById(R.id.music_Progress);
        currentTime = findViewById(R.id.tv_progress_current);
        totalTime = findViewById(R.id.tv_progress_total);
        btn_Prev = findViewById(R.id.btn_Prev);
        btn_Play = findViewById(R.id.btn_Play);
        btn_Next = findViewById(R.id.btn_Next);

        likeButton = findViewById(R.id.like_button);

        if (likeButton.isLiked()){
            likeButton.setLiked(true);
        }




        helper = new DBHelper(this,"music.db",null,1);
        helper.getWritableDatabase();


        OnSeekBarChangeControl seekBarChangeControl =new OnSeekBarChangeControl();
        musicProgress.setOnSeekBarChangeListener(seekBarChangeControl);

        mPlayNeedleAnim = AnimationUtils.loadAnimation(this, R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(this, R.anim.stop_needle_anim);

        animator = ObjectAnimator.ofFloat(diskImage,"rotation",0,360.0F);
        animator.setDuration(20000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);//一直转动
        preparePlayList();

        //收藏按钮点击事件
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                //将歌名 歌手名写入Music 数据库
                String musicName = songName.getText().toString();
                String musicSingerName = singerName.getText().toString();


                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("songName",musicName);
                values.put("singerName",musicSingerName);
                db.insert("music",null,values);


        Toast.makeText(MusicPlayerActivity.this,"已添加到我的喜欢",Toast.LENGTH_SHORT).show();
           
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                    //取消收藏 将歌曲从music.db 中删除数据
                    String musicName = songName.getText().toString();
                    SQLiteDatabase db = helper.getWritableDatabase();
                    String sql = "delete from music where songName ='" + musicName + "'";
                    db.execSQL(sql);
                    Toast.makeText(MusicPlayerActivity.this, "已从我的喜欢移除", Toast.LENGTH_SHORT).show();



            }
        });
        //接受歌单页面传回的每一项的数据
        Intent intent =getIntent();
        String songname = intent.getStringExtra("songname");
        String singername = intent.getStringExtra("singername");
        int currentIndex = intent.getIntExtra("songIndex",0);


        current = currentIndex;
        songName.setText(songname);
        singerName.setText(singername);
        diskImage.setImageResource(imgId[current]);




        if (player !=null){
            player.stop();
            player.reset();
        }

        player = MediaPlayer.create(getApplicationContext(),playList.get(current));
        player.start();
        animator.start();
        iv_needle.startAnimation(mPlayNeedleAnim);
        setTotalTime();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (player.isPlaying()) {
                    runOnUiThread(()->{
                        int currentMs = player.getCurrentPosition();
                        int sec = currentMs /1000;
                        int min = sec /60;
                        sec -= min*60;
                        String time = String.format("%02d:%02d",min,sec);
                        musicProgress.setProgress(currentMs);
                        currentTime.setText(time);
                    });
                }
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 0, 500);

//当歌曲播放完成后按钮图标改变 转盘停止转动
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCompletion(MediaPlayer player) {
                animator.pause();
                iv_needle.startAnimation(mStopNeedleAnim);
                btn_Play.setImageResource(R.drawable.ic_play_arrow);
            }
        });


        // 点击播放按钮 判断是否为第一次点击 如果不是第一次点击 就显示点击了重播
    btn_Play.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
       if (player.isPlaying())
        {
            animator.pause();
            iv_needle.startAnimation(mStopNeedleAnim);
            player.pause();
            isPlaying = false;
            btn_Play.setImageResource(R.drawable.ic_play_arrow);
            Log.i("INFO","暂停按钮被点击了");
        }
        else if (isPlaying == false)
        {
            animator.resume();
            iv_needle.startAnimation(mPlayNeedleAnim);
            player.start();
            btn_Play.setImageResource(R.drawable.ic_pause);
            Log.i("INFO","重播按钮被点击了");
            isPlaying = true;
        }
    }
    });

    //上一首按钮点击后 切换上一首歌曲
        btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preCurrent = (--current) %14;
                songName.setText(name[preCurrent]);
                singerName.setText(artist[preCurrent]);
                diskImage.setImageResource(imgId[preCurrent]);

                    if (!player.isPlaying()){
                        player = MediaPlayer.create(getApplicationContext(),playList.get(preCurrent));
                        setTotalTime();
                        player.start();
                        btn_Play.setImageResource(R.drawable.ic_pause);
                        animator.start();
                        iv_needle.startAnimation(mPlayNeedleAnim);
                        isPlaying = false;

                   }

                    else if (player.isPlaying()){
                        player.stop();
                        player.reset();
                        player = MediaPlayer.create(getApplicationContext(),playList.get(preCurrent));
                        setTotalTime();
                        player.start();
                        btn_Play.setImageResource(R.drawable.ic_pause);
                        animator.start();
                        iv_needle.startAnimation(mPlayNeedleAnim);
                        isPlaying = false;
                    }

                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        animator.pause();
                        iv_needle.startAnimation(mStopNeedleAnim);
                        btn_Play.setImageResource(R.drawable.ic_play_arrow);


                    }
                });

                    Log.i("INFO","上一曲按钮被点击了");
            }
        });

//下一首按钮点击后切换下一首歌曲
    btn_Next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        nextCurrent = (++current) % 14;
        songName.setText(name[nextCurrent]);
        singerName.setText(artist[nextCurrent]);
        diskImage.setImageResource(imgId[nextCurrent]);
        if (nextCurrent == 14){
            Toast.makeText(MusicPlayerActivity.this, "已经是最后一首了", Toast.LENGTH_SHORT).show();
        }

        if ( !player.isPlaying()){
            player = MediaPlayer.create(getApplicationContext(),playList.get(nextCurrent));
            player.start();
            setTotalTime();
            btn_Play.setImageResource(R.drawable.ic_pause);
            animator.start();
            iv_needle.startAnimation(mPlayNeedleAnim);
            isPlaying = false;

        }
        else if(player.isPlaying()){
            player.stop();
            player.reset();
            player = MediaPlayer.create(getApplicationContext(),playList.get(current));
            setTotalTime();
            player.start();
            btn_Play.setImageResource(R.drawable.ic_pause);
            animator.start();
            iv_needle.startAnimation(mPlayNeedleAnim);
            isPlaying = false;
        }
        Log.i("INFO","下一曲按钮被点击了");
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCompletion(MediaPlayer player) {
                animator.pause();
                iv_needle.startAnimation(mStopNeedleAnim);
                btn_Play.setImageResource(R.drawable.ic_play_arrow);

            }
        });

    }
});
    }

// 总时长的格式化 格式化成00：00的格式
    public void setTotalTime() {
        int musicDuration = player.getDuration();
        musicProgress.setMax(musicDuration);
        int sec = musicDuration /1000;
        int min = sec /60;
        sec -= min*60;
        String musicTime = String.format("%02d:%02d",min,sec);
        totalTime.setText(musicTime);
    }


//通过循环遍历得到raw中的歌曲文件 添加到playList数组里
    private void preparePlayList(){
        Field[] fields = R.raw.class.getFields();
        for (int count =1; count<fields.length;count++){
            Log.i("Raw Name",fields[count].getName());
            Log.i("INFO","歌曲数"+playList.size());
            try {
                int resId =fields[count].getInt(fields[count]);
                playList.add(resId);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }



//更改进度条
    private class  OnSeekBarChangeControl implements SeekBar.OnSeekBarChangeListener{

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            player.seekTo(progress);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //当手滑动进度条时 让歌曲暂停 转盘停止转动 以至于不会出现卡带的情况
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        player.pause();
        animator.pause();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //当手指滑动结束后 音乐开始播放 进度条小于10 转盘重新开始转动
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        player.start();
        if (seekBar.getProgress() < 10){
            animator.start();
        }
        else {
            animator.resume();
        }
    }
}

    @Override
    //页面销毁的时候 音乐停止播放
    protected void onDestroy() {
        if (player.isPlaying()){
            player.stop();
        }

        super.onDestroy();
    }

//左上角图标按钮点击返回
    public void backToListen(View view) {
        finish();
    }
}