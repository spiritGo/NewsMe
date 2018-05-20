package com.example.spirit.template.domain;

import java.util.ArrayList;

public class NewsBean {
    private ArrayList<NewsList> newslist;

    public class NewsList {
        private String ctime;
        private String description;
        private String picUrl;
        private String title;
        private String url;


        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "NewsList{" +
                    "ctime='" + ctime + '\'' +
                    ", description='" + description + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public ArrayList<NewsList> getNewslist() {
        return newslist;
    }

    public void setNewslist(ArrayList<NewsList> newslist) {
        this.newslist = newslist;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "newslist=" + newslist +
                '}';
    }
}
