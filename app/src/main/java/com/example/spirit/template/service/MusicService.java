package com.example.spirit.template.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.spirit.template.domain.MusicBean;
import com.example.spirit.template.fragment.MusicFragment;
import com.example.spirit.template.utils.MusicUtil;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {

    private MediaPlayer player;
    private ArrayList<MusicBean> musicList;
    private int count;
    private int currentItem;
    private MusicFragment musicFragment;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MusicBroadCast musicBroadCast = new MusicBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicUtil.ACTION_PAUSE);
        filter.addAction(MusicUtil.ACTION_PLAY);
        filter.addAction(MusicUtil.ACTION_REPLAY);
        filter.addAction(MusicUtil.ACTION_STOP);
        filter.addAction(MusicUtil.ACTION_PREV);
        filter.addAction(MusicUtil.ACTION_NEXT);
        registerReceiver(musicBroadCast, filter);

        player = MusicUtil.getPlayer();
        musicList = MusicUtil.getMusicList();
        count = MusicUtil.getCount();
        musicFragment = MusicFragment.getMusicFragment();
    }

    private void rePlay() {
        player.reset();
        try {
            player.setDataSource(musicList.get(currentItem).getPath());
            player.prepare();
            player.start();
            musicFragment.setTvMusicTitle(musicList.get(currentItem).getTitle());
            musicFragment.getSbBar().setMax(Integer.parseInt(musicList.get(currentItem)
                    .getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void play() {
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    private void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    private void next() {
        if (currentItem >= count - 1) {
            currentItem = 0;
        } else {
            currentItem++;
        }
        rePlay();
    }

    private void prev() {
        if (currentItem <= 0) {
            currentItem = count - 1;
        } else {
            currentItem--;
        }
        rePlay();
    }

    private void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    class MusicBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MusicUtil.ACTION_PAUSE.equals(action)) {
                pause();
            } else if (MusicUtil.ACTION_PLAY.equals(action)) {
                play();
            } else if (MusicUtil.ACTION_STOP.equals(action)) {
                stop();
            } else if (MusicUtil.ACTION_REPLAY.equals(action)) {
                currentItem = intent.getIntExtra(MusicUtil.ACTION_REPLAY, 0);
                rePlay();
            } else if (MusicUtil.ACTION_PREV.equals(action)) {
                prev();
            } else if (MusicUtil.ACTION_NEXT.equals(action)) {
                next();
            }
        }
    }
}
