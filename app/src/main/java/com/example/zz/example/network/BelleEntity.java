package com.example.zz.example.network;


import java.util.List;

/**
 * desc:
 * author: wecent
 * date: 2018/9/27
 */
public class BelleEntity {

    /**
     * status : ok
     * current_page : 1
     * total_comments : 6292
     * page_count : 252
     * count : 25
     * comments : [{"comment_ID":"3519034","comment_post_ID":"26402","comment_author":"老大哥","comment_author_email":"pqpq3@163.com","comment_author_url":"","comment_author_IP":"218.88.71.77","comment_date":"2017-07-27 10:21:10","comment_date_gmt":"2017-07-27 02:21:10","comment_content":"<img src=\"http://wx4.sinaimg.cn/mw690/006g87eSgy1fhy89a5v21g305k09w7pm.gif\" />","comment_karma":"0","comment_approved":"1","comment_agent":"Jandan Android App V4.3.1.1;eyJzaWduIjoiMGFjMzA0YmE0NGY2YWY3ZmY4ZmEwYTUwM2QwYjZiZWEifQ==","comment_type":"","comment_parent":"0","user_id":"0","comment_subscribe":"N","comment_reply_ID":"0","vote_positive":"71","vote_negative":"2","vote_ip_pool":"","sub_comment_count":"6","text_content":"","pics":["http://wx4.sinaimg.cn/mw690/006g87eSgy1fhy89a5v21g305k09w7pm.gif"]}]
     */

    private String status;
    private int current_page;
    private int total_comments;
    private int page_count;
    private int count;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



}
