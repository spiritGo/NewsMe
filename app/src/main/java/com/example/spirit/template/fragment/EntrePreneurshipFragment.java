package com.example.spirit.template.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spirit.template.R;
import com.example.spirit.template.activity.MyWebActivity;
import com.example.spirit.template.adapter.MyAdapter;
import com.example.spirit.template.domain.NewsBean;
import com.example.spirit.template.utils.ConstanceUtil;
import com.example.spirit.template.utils.HttpUtil;
import com.example.spirit.template.utils.UrlUtil;
import com.lidroid.xutils.BitmapUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EntrePreneurshipFragment extends MyBaseFragment {

    @Override
    public String setUrl() {
        return UrlUtil.ENTREPRENEURSHIP;
    }
}
