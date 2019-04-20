package com.lishi.demo.simplepoetrysearchdemo.Model;

import com.lishi.demo.simplepoetrysearchdemo.Item.PoetryItem;

import java.util.List;

public interface OnSearchListener {
    void searchSuccess(List<PoetryItem> list);
    void searchOver();
    void searchFailed();
}
