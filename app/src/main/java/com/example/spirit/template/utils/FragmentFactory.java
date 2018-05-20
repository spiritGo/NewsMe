package com.example.spirit.template.utils;

import android.support.v4.app.Fragment;

import com.example.spirit.template.fragment.AboutFragment;
import com.example.spirit.template.fragment.EntrePreneurshipFragment;
import com.example.spirit.template.fragment.HealthFragment;
import com.example.spirit.template.fragment.IndexFragment;
import com.example.spirit.template.fragment.InternationalFragment;
import com.example.spirit.template.fragment.MusicFragment;
import com.example.spirit.template.fragment.PEFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class FragmentFactory {
    private static FragmentFactory factory;
    private HashMap<String, android.support.v4.app.Fragment> fragmentMap;
    private IndexFragment indexFragment;
    private ArrayList<Fragment> fragmentList;

    public static FragmentFactory getFragmentFactory() {
        if (factory == null) {
            synchronized (FragmentFactory.class) {
                if (factory == null) {
                    factory = new FragmentFactory();
                }
            }
        }
        return factory;
    }

    public android.support.v4.app.Fragment getFragment(String key) {
        if (fragmentMap == null) {
            fragmentMap = new HashMap<>();
            fragmentMap.put("国际新闻", new InternationalFragment());
            fragmentMap.put("体育新闻", new PEFragment());
            fragmentMap.put("创业新闻", new EntrePreneurshipFragment());
            fragmentMap.put("健康咨讯", new HealthFragment());
        }
        return fragmentMap.get(key);
    }

    public IndexFragment getIndexFragment() {
        return indexFragment == null ? new IndexFragment() : indexFragment;
    }

    public ArrayList<Fragment> getFragmentList() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmentList.add(new IndexFragment());
            fragmentList.add(new MusicFragment());
            fragmentList.add(new AboutFragment());
        }
        return fragmentList;
    }
}
