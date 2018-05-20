package com.example.spirit.template.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spirit.template.R;
import com.example.spirit.template.utils.UrlUtil;

public class HealthFragment extends MyBaseFragment {

    @Override
    public String setUrl() {
        return UrlUtil.HEALTH;
    }
}
