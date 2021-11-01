package com.example.buttonnavigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.buttonnavigation.R;
import com.example.buttonnavigation.RoundImageView;
import com.example.buttonnavigation.beans.Music;

import java.util.List;

public class MusicAdapter extends BaseAdapter {

    private Context mContext;
    private List<Music> musicList;
    private LayoutInflater mLayoutInflater;//LayoutInflater 抽象类，将布局xml文件实例化为其对应的View对象


    public MusicAdapter(Context mContext ,List<Music> musicList) {

        this.mContext = mContext;
        this.mLayoutInflater =  LayoutInflater.from(mContext);
        this.musicList = musicList;



    }



    //返回适配器中所代表的数据集合的条数
    //会首先执行这个方法（连续好几次），如果是 0 则后面的方法就不执行了
    @Override
    public int getCount() {
        return musicList.size();
    }
    //返回数据集合中指定索引 position 对应的数据项
    //手动调用才会执行
    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }
    //返回列表洪与指定索引的行id
    //手动调用才会执行
    @Override
    public long getItemId(int position) {
        return 0;
    }
//返回指定索引对应的数据的视图， 会多次调用
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.music_list_item,null);
           RoundImageView roundImageView = convertView.findViewById(R.id.iv_photo);
            TextView songName = convertView.findViewById(R.id.song_Name);
            TextView singerName =convertView.findViewById(R.id.singer_Name);
            TextView duration = convertView.findViewById(R.id.music_duration);

            viewHolder.roundImageView = roundImageView;
            viewHolder.songName = songName;
            viewHolder.singerName = singerName;
            viewHolder.duration = duration;
            //将ViewHolder 绑定到 convertView 中实现复用
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Music musics = musicList.get(position);
        viewHolder.roundImageView.setImageResource(musics.getImgResId());
        viewHolder.songName.setText(musics.getSongName());
        viewHolder.singerName.setText(musics.getSingerName());
        viewHolder.duration.setText(musics.getDuration());

        return convertView;



    }
     class  ViewHolder{
        RoundImageView roundImageView;
        TextView songName;
        TextView singerName;
        TextView duration;


    }
}
