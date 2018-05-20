package com.example.spirit.template.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spirit.template.R;
import com.example.spirit.template.adapter.MenuListAdapter;
import com.example.spirit.template.utils.ConstanceUtil;
import com.example.spirit.template.utils.FragmentFactory;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends FragmentActivity {

    @InjectView(R.id.lv_list)
    ListView menuList;
    @InjectView(R.id.fl_container)
    FrameLayout frameLayout;
    @InjectView(R.id.drawer)
    DrawerLayout drawerLayout;
    @InjectView(R.id.iv_menu)
    ImageView ivMenu;
    @InjectView(R.id.tv_topTitle)
    TextView topTitle;
    @InjectView(R.id.music_count)
    TextView musicCount;
    @InjectView(R.id.tv_net)
    TextView tvNet;

    private ArrayList<String> menuListTitle;
    private FragmentManager fragmentManager;
    private FragmentFactory fragmentFactory;
    private static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mainActivity = MainActivity.this;

        initVariable();
        initUI();
    }

    private void initVariable() {
        menuListTitle = new ArrayList<>();
        menuListTitle.add("首页");
        menuListTitle.add("轻松一下[音乐]");
        menuListTitle.add("关于我们");

        fragmentManager = getSupportFragmentManager();
        fragmentFactory = FragmentFactory.getFragmentFactory();
    }

    private void initUI() {
        MenuListAdapter menuListAdapter = new MenuListAdapter(menuListTitle);
        View header = View.inflate(this, R.layout.menu_header, null);
        menuList.addHeaderView(header);
        menuList.setAdapter(menuListAdapter);

        fragmentManager.beginTransaction().add(R.id.fl_container, fragmentFactory
                .getIndexFragment()).commit();
        topTitle.setText(menuListTitle.get(0));
        musicCount.setVisibility(View.INVISIBLE);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    return;
                }

                fragmentManager.beginTransaction().replace(R.id.fl_container, fragmentFactory
                        .getFragmentList().get(position - 1)).commit();
                drawerControl();
                topTitle.setText(menuListTitle.get(position - 1));
                musicCount.setVisibility(View.INVISIBLE);
                if (position == 2) {
                    musicCount.setVisibility(View.VISIBLE);
                }

                if (position != 1) {
                    tvNet.setVisibility(View.INVISIBLE);
                } else {
                    tvNet.setVisibility(View.VISIBLE);
                }
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerControl();
            }
        });
        tvNet.setText(ConstanceUtil.isNetConnect(this));
    }

    public void setTvNet(String net) {
        tvNet.setText(net);
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMusicCount(String musicCount) {
        this.musicCount.setText(musicCount);
    }

    private void drawerControl() {
        if (drawerLayout.isDrawerOpen(menuList)) {
            drawerLayout.closeDrawer(menuList);
        } else {
            drawerLayout.openDrawer(menuList);
        }
    }
}
