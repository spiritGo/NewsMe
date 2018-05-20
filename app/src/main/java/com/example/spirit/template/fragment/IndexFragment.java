package com.example.spirit.template.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.spirit.template.R;
import com.example.spirit.template.utils.ConstanceUtil;
import com.example.spirit.template.utils.FragmentFactory;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class IndexFragment extends Fragment {

    @InjectView(R.id.ft_host)
    FragmentTabHost ftHost;

    private HashMap<String, Integer> bottomTitleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_index, null);

        ButterKnife.inject(this, view);
        ftHost.setup(getContext(), getChildFragmentManager(), R.id.fl_container);
        ftHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        initVariable();
        initUI();
        return view;
    }

    private void initVariable() {
        bottomTitleList = ConstanceUtil.getBottomTitleList();
    }

    private void initUI() {
        createSpec();
        System.out.println("IndexFragment");
        ftHost.setCurrentTab(0);
    }

    private void createSpec() {
        for (String key : bottomTitleList.keySet()) {
            View bottom = View.inflate(getContext(), R.layout.botton_layout, null);
            ImageView iv_bottom = bottom.findViewById(R.id.iv_bottom);
            TextView tv_bottom = bottom.findViewById(R.id.tv_bottom);
            iv_bottom.setImageResource(bottomTitleList.get(key));
            tv_bottom.setText(key);
            TabHost.TabSpec tabSpec = ftHost.newTabSpec(key).setIndicator(bottom);
            Class<? extends Fragment> aClass = FragmentFactory.getFragmentFactory().getFragment
                    (key).getClass();
            ftHost.addTab(tabSpec, aClass, null);
            ftHost.getChildAt(0).setSelected(true);
        }
    }
}
