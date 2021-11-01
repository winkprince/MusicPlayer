package com.example.buttonnavigation.beans;

public class Music  {
    private String songName;
    private String singerName;
    private String duration;
    private  int imgResId ;




   public  Music(){

    }
    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Music(String songName, String singerName, String duration ,int imgResId) {
        this.songName = songName;
        this.singerName = singerName;
        this.duration = duration;
        this.imgResId = imgResId;
    }
}

