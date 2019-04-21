package com.lishi.demo.simplepoetrysearchdemo.View;

import com.lishi.demo.simplepoetrysearchdemo.Item.PoetryItem;

import java.util.List;

public interface SearchPoetryView {
    int getMode();
    boolean ifSearched();
    void setSearched();
    String getContent();

    void clearPoetry();
    void showLoading();
    void hideLoading();
    void toMainActivity(List<PoetryItem> list);
    void showFailedError();
}