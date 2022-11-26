package com.is6144.musicapp;

import android.media.MediaPlayer;
// I have created an instance to access the media player from anywhere
public class MyMediaPlayer {
    static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        if (instance == null){
            instance =new MediaPlayer();
        }
        return instance;
    }

    public static int currentIndex = -1;
}
