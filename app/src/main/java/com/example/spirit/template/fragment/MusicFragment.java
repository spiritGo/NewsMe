package com.example.spirit.template.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.spirit.template.R;
import com.example.spirit.template.activity.MainActivity;
import com.example.spirit.template.domain.MusicBean;
import com.example.spirit.template.service.MusicService;
import com.example.spirit.template.utils.ConstanceUtil;
import com.example.spirit.template.utils.MusicUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MusicFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.iv_playAndPause)
    ImageView ivPlayAndPause;
    @InjectView(R.id.sb_bar)
    SeekBar sbBar;
    @InjectView(R.id.tv_musicTitle)
    TextView tvMusicTitle;
    @InjectView(R.id.iv_prev)
    ImageView ivPrev;
    @InjectView(R.id.iv_next)
    ImageView ivNext;
    @InjectView(R.id.lv_musicList)
    ListView lvMusicList;
    private View view;
    private MainActivity mainActivity;
    private MediaPlayer player;
    private static MusicFragment musicFragment;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateProgress();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.fragment_music, null);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        ButterKnife.inject(this, view);
        ArrayList<MusicBean> musicList = MusicUtil.getMusicList();

        if (musicList.size() > 0) {
            initUI(musicList);
            startService();
            player = MusicUtil.getPlayer();
            musicFragment = MusicFragment.this;
            ivPlayAndPause.setSelected(player.isPlaying());
            clickEvent();

            if (player.isPlaying()){
                handler.sendEmptyMessage(0);
            }
        }

        System.out.println(player.isPlaying());
        return view;
    }

    private void clickEvent() {
        lvMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(MusicUtil.ACTION_REPLAY);
                intent.putExtra(MusicUtil.ACTION_REPLAY, position);
                ConstanceUtil.getContext().sendBroadcast(intent);
                handler.sendEmptyMessage(0);
                ivPlayAndPause.setSelected(true);
            }
        });

        ivPlayAndPause.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivPrev.setOnClickListener(this);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sendBroadcast(MusicUtil.ACTION_NEXT);
            }
        });


        sbBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });
    }

    private void startService() {
        Intent intent = new Intent(getContext(), MusicService.class);
        ConstanceUtil.getContext().startService(intent);
    }

    private void initUI(ArrayList<MusicBean> musicList) {
        System.out.println("onCreateView");
        MAdapter mAdapter = new MAdapter(musicList);
        lvMusicList.setAdapter(mAdapter);
        mainActivity = MainActivity.getMainActivity();
        mainActivity.setMusicCount(MusicUtil.getCount() + "");
    }

    public void updateProgress() {
        int currentPosition = player.getCurrentPosition();
        sbBar.setProgress(currentPosition);
        handler.sendEmptyMessageDelayed(0, 350);
    }

    public static MusicFragment getMusicFragment() {
        return musicFragment;
    }

    public SeekBar getSbBar() {
        return sbBar;
    }

    public void setTvMusicTitle(String title) {
        tvMusicTitle.setText(title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_playAndPause:
                if (player.isPlaying()) {
                    sendBroadcast(MusicUtil.ACTION_PAUSE);
                    ivPlayAndPause.setSelected(false);
                    handler.removeCallbacksAndMessages(0);
                } else {
                    sendBroadcast(MusicUtil.ACTION_PLAY);
                    ivPlayAndPause.setSelected(true);
                    handler.sendEmptyMessage(0);
                }
                break;
            case R.id.iv_next:
                sendBroadcast(MusicUtil.ACTION_NEXT);
                break;
            case R.id.iv_prev:
                sendBroadcast(MusicUtil.ACTION_PREV);
                break;
        }
    }

    private void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        ConstanceUtil.getContext().sendBroadcast(intent);
    }

    class MAdapter extends BaseAdapter {
        private ArrayList<MusicBean> list;

        MAdapter(ArrayList<MusicBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.music_list, null);
                holder = new ViewHolder();
                holder.musicTitle = convertView.findViewById(R.id.tv_title);
                holder.musicArtist = convertView.findViewById(R.id.tv_artist);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.musicArtist.setText(list.get(position).getArtist());
            holder.musicTitle.setText(list.get(position).getTitle());
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView musicTitle;
        private TextView musicArtist;
    }
}
