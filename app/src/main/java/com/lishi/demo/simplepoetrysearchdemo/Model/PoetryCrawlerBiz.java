package com.lishi.demo.simplepoetrysearchdemo.Model;

public interface PoetryCrawlerBiz {
    void SearchPoetry(int mode,boolean ifSearched,String content,OnSearchListener searchListener);
}