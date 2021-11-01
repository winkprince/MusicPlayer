package com.example.buttonnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buttonnavigation.adapter.MusicAdapter;
import com.example.buttonnavigation.beans.Music;

import java.util.ArrayList;
import java.util.List;

public class RecentActivity extends AppCompatActivity {
    private ImageView imageView;

    List<Music> musicList;
    ListView musicListView;
    private MusicAdapter mMusicAdapter2;
    String[] songName = {"反方向的钟", "someThing just like this", "爱的魔法", "My soul", "Melancholy", "Monster-Remix", "你的爱", "哪里都是你", "泡沫Remix",
            "起风了", "等你下课", "我的歌声里", "与你", "追光者"};
    String[] singerName = {"九三", "Chainsmokers", "冷雪儿", "July", "melancho", "Katie sky", "wink", "队长", "Swang多雷", "买辣椒也用券",
            "赵悦栖", "兰琦", "程jiajia", "Aka-诉阳"};
    String[] duration = {"3:00", "5:00", "2:00", "5:20", "3:43", "3:03", "3:06", "4:06", "3:20",
            "2:50", "3:30", "4:20", "2:39", "3:50"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initMusicData();
        initEvent();
    }

    private void initMusicData() {

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

        musicList = new ArrayList<Music>();

        Music musiclist1 = new Music();

        musiclist1.setSongName(songName[0]);
        musiclist1.setSingerName(singerName[0]);
        musiclist1.setDuration(duration[0]);
        musiclist1.setImgResId(imgId[0]);

        Music musiclist2 = new Music();
        musiclist2.setSongName(songName[1]);
        musiclist2.setSingerName(singerName[1]);
        musiclist2.setDuration(duration[1]);
        musiclist2.setImgResId(imgId[1]);

        Music musiclist3 = new Music();
        musiclist3.setSongName(songName[2]);
        musiclist3.setSingerName(singerName[2]);
        musiclist3.setDuration(duration[2]);
        musiclist3.setImgResId(imgId[2]);

        Music musiclist4 = new Music();
        musiclist4.setSongName(songName[3]);
        musiclist4.setSingerName(singerName[3]);
        musiclist4.setDuration(duration[3]);
        musiclist4.setImgResId(imgId[3]);

        Music musiclist5 = new Music();
        musiclist5.setSongName(songName[4]);
        musiclist5.setSingerName(singerName[4]);
        musiclist5.setDuration(duration[4]);
        musiclist5.setImgResId(imgId[4]);

        Music musiclist6 = new Music();
        musiclist6.setSongName(songName[5]);
        musiclist6.setSingerName(singerName[5]);
        musiclist6.setDuration(duration[5]);
        musiclist6.setImgResId(imgId[5]);

        Music musiclist7 = new Music();
        musiclist7.setSongName(songName[6]);
        musiclist7.setSingerName(singerName[6]);
        musiclist7.setDuration(duration[6]);
        musiclist7.setImgResId(imgId[6]);

        Music musiclist8 = new Music();
        musiclist8.setSongName(songName[7]);
        musiclist8.setSingerName(singerName[7]);
        musiclist8.setDuration(duration[7]);
        musiclist8.setImgResId(imgId[7]);

        Music musiclist9 = new Music();
        musiclist9.setSongName(songName[8]);
        musiclist9.setSingerName(singerName[8]);
        musiclist9.setDuration(duration[8]);
        musiclist9.setImgResId(imgId[8]);


        Music musiclist10 = new Music();
        musiclist10.setSongName(songName[9]);
        musiclist10.setSingerName(singerName[9]);
        musiclist10.setDuration(duration[9]);
        musiclist10.setImgResId(imgId[9]);

        Music musiclist11 = new Music();
        musiclist11.setSongName(songName[10]);
        musiclist11.setSingerName(singerName[10]);
        musiclist11.setDuration(duration[10]);
        musiclist11.setImgResId(imgId[10]);

        Music musiclist12 = new Music();
        musiclist12.setSongName(songName[11]);
        musiclist12.setSingerName(singerName[11]);
        musiclist12.setDuration(duration[11]);
        musiclist12.setImgResId(imgId[11]);

        Music musiclist13 = new Music();
        musiclist13.setSongName(songName[12]);
        musiclist13.setSingerName(singerName[12]);
        musiclist13.setDuration(duration[12]);
        musiclist13.setImgResId(imgId[12]);

        Music musiclist14 = new Music();
        musiclist14.setSongName(songName[13]);
        musiclist14.setSingerName(singerName[13]);
        musiclist14.setDuration(duration[13]);
        musiclist14.setImgResId(imgId[13]);

        musicList.add(musiclist1);
        musicList.add(musiclist2);
        musicList.add(musiclist3);
        musicList.add(musiclist4);
        musicList.add(musiclist5);
        musicList.add(musiclist6);
        musicList.add(musiclist7);
        musicList.add(musiclist8);
        musicList.add(musiclist9);
        musicList.add(musiclist10);
        musicList.add(musiclist11);
        musicList.add(musiclist12);
        musicList.add(musiclist13);
        musicList.add(musiclist14);
    }


    private void initEvent() {
        mMusicAdapter2 = new MusicAdapter(this, musicList);
        musicListView = findViewById(R.id.lv_music_recent);
        musicListView.setAdapter(mMusicAdapter2);
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int songIndex = position;


                String songname = musicList.get(position).getSongName();
                String singername = musicList.get(position).getSingerName();
                int imgResId = musicList.get(position).getImgResId();
                Intent intent = new Intent(RecentActivity.this, MusicPlayerActivity.class);
                intent.putExtra("songIndex", songIndex);
                intent.putExtra("songname", songname);
                intent.putExtra("singername", singername);
                intent.putExtra("imgResId", imgResId);

                startActivity(intent);
            }
        });


    }


    public void backActivity(View view) {
        finish();
    }
}