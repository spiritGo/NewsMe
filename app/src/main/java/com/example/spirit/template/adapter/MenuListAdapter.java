package com.example.spirit.template.adapter;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spirit.template.R;
import com.example.spirit.template.utils.ConstanceUtil;

import java.util.ArrayList;

public class MenuListAdapter extends BaseAdapter {

    private ArrayList<String> menuListData;

    public MenuListAdapter(ArrayList<String> menuListData) {
        this.menuListData = menuListData;
    }

    @Override
    public int getCount() {
        return menuListData.size();
    }

    @Override
    public Object getItem(int position) {
        return menuListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(ConstanceUtil.getContext(), R.layout.menu_list, null);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.tv_menuListItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(menuListData.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        ImageView img;
    }
}
