package com.example.buttonnavigation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.like.LikeButton;

public class MyLoveActivity extends AppCompatActivity {

    private LikeButton likeButton;
    private TextView songName;
     private ListView listView;
     private DBHelper helper;
    private SimpleCursorAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love);
        likeButton = findViewById(R.id.like_button);
        listView = findViewById(R.id.lv_music_love);
       songName = findViewById(R.id.song_Name);





        showMusic();

//       likeButton.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//                //取消收藏 将歌曲从music.db 中删除数据
//                 String  musicName = songName.getText().toString();
//                SQLiteDatabase db = helper.getWritableDatabase();
//                String sql = "delete from music where songName ='"+musicName+"'";
//                db.execSQL(sql);
//                //删除之后刷新界面
//                showMusic();
//            }
//        });





    }

    private void showMusic() {
        helper = new DBHelper(this,"music.db",null,1);
        helper.getWritableDatabase();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("music",null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(MyLoveActivity.this,R.layout.music_list_love,cursor,new String[]{"_id","songName","singerName"},new int[]{R.id._id,R.id.song_Name,R.id.singer_Name});
        listView.setAdapter(adapter);
        db.close();
    }

    public void backToListen(View view) {
        finish();
    }
}