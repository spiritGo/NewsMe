package com.example.spirit.template.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
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
import com.example.spirit.template.activity.MainActivity;
import com.example.spirit.template.activity.MyWebActivity;
import com.example.spirit.template.adapter.MyAdapter;
import com.example.spirit.template.domain.NewsBean;
import com.example.spirit.template.utils.ConstanceUtil;
import com.example.spirit.template.utils.HttpUtil;
import com.example.spirit.template.utils.NetWorkUtil;
import com.lidroid.xutils.BitmapUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class MyBaseFragment extends Fragment {
    @InjectView(R.id.lv_epfList)
    ListView lvEpfList;
    @InjectView(R.id.srl_smart)
    SmartRefreshLayout srlSmart;
    private View view;
    private HttpUtil httpUtil;
    private BitmapUtils bitmapUtils;
    private int num = 10;
    private MyBaseFragment.MAdapter mAdapter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final NewsBean newsBean = (NewsBean) msg.obj;
            if (mAdapter == null) {
                mAdapter = new MyBaseFragment.MAdapter(newsBean, ConstanceUtil
                        .getContext(), R.layout
                        .pe_list_item);
                lvEpfList.setAdapter(mAdapter);
            } else {
                mAdapter.setNewsBean(newsBean);
                mAdapter.notifyDataSetChanged();
            }

            lvEpfList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), MyWebActivity.class);
                    intent.putExtra("url", newsBean.getNewslist().get(position).getUrl());
                    startActivity(intent);
                }
            });
        }
    };
    private NetBroadcast netBroadcast;
    private MainActivity mainActivity;


    public abstract String setUrl();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.fragment_entre_preneurship, null);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        ButterKnife.inject(this, view);

        netBroadcast = new NetBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConstanceUtil.getContext().registerReceiver(netBroadcast, filter);
        mainActivity = MainActivity.getMainActivity();

        initVariable();
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initVariable() {
        httpUtil = HttpUtil.getHttpUtil();
        bitmapUtils = new BitmapUtils(ConstanceUtil.getContext());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.aio_image_default);
    }

    private void initData() {
        httpUtil.getData(setUrl() + num, NewsBean.class);
        srlSmart.setEnableScrollContentWhenLoaded(true);
        srlSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (num < 50) {
                    num += 10;
                } else {
                    num = 10;
                }
                httpUtil.getData(setUrl() + num, NewsBean.class);
                refreshLayout.finishLoadMore(1000);
            }
        });

        srlSmart.setEnableRefresh(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void revMessage(NewsBean newsBean) {
        Message message = Message.obtain();
        message.obj = newsBean;
        handler.sendMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ConstanceUtil.getContext().unregisterReceiver(netBroadcast);
    }

    class MAdapter extends MyAdapter {

        MAdapter(NewsBean newsBean, Context context, int layout) {
            super(newsBean, context, layout);
        }

        @Override
        public void convert(final ViewHolder viewHolder, final NewsBean.NewsList t) {
            ((TextView) viewHolder.findViewById(R.id.tv_title)).setText(t.getTitle());
            ImageView iv_Img = viewHolder.findViewById(R.id.iv_Img);
            bitmapUtils.display(iv_Img, t.getPicUrl());
            ((TextView) viewHolder.findViewById(R.id.tv_description)).setText(t.getDescription());
            ((TextView) viewHolder.findViewById(R.id.tv_time)).setText(t.getCtime());
        }
    }


    class NetBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int netState = NetWorkUtil.getNetState(ConstanceUtil.getContext());
            if (netState == NetWorkUtil.TYPE_WIFI) {
                httpUtil.getData(setUrl() + num, NewsBean.class);
                mainActivity.setTvNet("WIFI网络");
            } else if (netState == NetWorkUtil.TYPE_MOBILE) {
                mainActivity.setTvNet("移动网络");
            } else if (netState == NetWorkUtil.TYPE_NONE) {
                mainActivity.setTvNet("无网络");
            }
        }
    }
}
