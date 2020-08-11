package com.example.zz.example.network.bean;

public class WangYiNews {

    String path = "null";
    String image = "null";
    String title = "null";
    String passtime = "null";

    public WangYiNews(String path, String image, String title, String passtime) {
        this.path = path;
        this.image = image;
        this.title = title;
        this.passtime = passtime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }
}
