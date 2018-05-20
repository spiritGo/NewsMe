package com.example.spirit.template.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.spirit.template.domain.NewsBean;

public abstract class MyAdapter extends BaseAdapter {

    private NewsBean newsBean;
    private Context context;
    private int layout;

    public MyAdapter(NewsBean newsBean, Context context, int layout) {
        this.context = context;
        this.layout = layout;
        this.newsBean=newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }

    @Override
    public int getCount() {
        return newsBean == null ? 0 : newsBean.getNewslist().size();
    }

    @Override
    public Object getItem(int position) {
        return newsBean == null ? null : newsBean.getNewslist().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, layout, convertView);
        convert(viewHolder,newsBean.getNewslist().get(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, NewsBean.NewsList t);

    protected static class ViewHolder {

        private View convertView;
        private SparseArray<View> views;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
            this.views = new SparseArray<>();
            convertView.setTag(this);
        }

        public static ViewHolder getViewHolder(Context context, int layout, View convertView) {
            if (convertView == null) {
                convertView = View.inflate(context, layout, null);
                return new ViewHolder(convertView);
            }
            return (ViewHolder) convertView.getTag();
        }

        public <T extends View> T findViewById(int id) {
            View view = views.get(id);
            if (view == null) {
                view = convertView.findViewById(id);
                views.append(id, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return convertView;
        }
    }
}
