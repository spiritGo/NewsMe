package com.example.spirit.template.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.example.spirit.template.domain.MusicBean;

import java.security.PublicKey;
import java.util.ArrayList;

public class MusicUtil {

    private static MediaPlayer player;
    final public static String ACTION_PLAY = "action_play";
    final public static String ACTION_PAUSE = "action_pause";
    final public static String ACTION_STOP = "action_stop";
    final public static String ACTION_REPLAY = "action_replay";
    final public static String ACTION_NEXT = "action_next";
    final public static String ACTION_PREV = "action_prev";

    public enum MusicState {PLAY, PAUSE, STOP, RE_PLAY}

    public static MediaPlayer getPlayer() {
        if (player == null) {
            synchronized (MediaPlayer.class) {
                if (player == null) {
                    player = new MediaPlayer();
                }
            }
        }
        return player;
    }

    public static ArrayList<MusicBean> getMusicList() {
        ContentResolver resolver = ConstanceUtil.getContext().getContentResolver();
        Cursor query = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, null);
        ArrayList<MusicBean> musicBeans = new ArrayList<>();
        if (query != null) {
            while (query.moveToNext()) {
                MusicBean musicBean = new MusicBean();
                musicBean.setTitle(query.getString(query.getColumnIndex(MediaStore.Audio.Media
                        .TITLE)));
                musicBean.setArtist(query.getString(query.getColumnIndex(MediaStore.Audio.Media
                        .ARTIST)));
                musicBean.setDuration(query.getString(query.getColumnIndex(MediaStore.Audio.Media
                        .DURATION)));
                musicBeans.add(musicBean);
                musicBean.setPath(query.getString(query.getColumnIndex(MediaStore.Audio.Media
                        .DATA)));
            }
            query.close();
        }
        return musicBeans;
    }

    public static int getCount() {
        ContentResolver resolver = ConstanceUtil.getContext().getContentResolver();
        Cursor query = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, null);

        if (query != null) {
            int count = query.getCount();
            query.close();
            return count;
        }
        return 0;
    }
}
