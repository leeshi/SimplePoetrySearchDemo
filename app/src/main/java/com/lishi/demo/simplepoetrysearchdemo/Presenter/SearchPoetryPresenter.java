package com.lishi.demo.simplepoetrysearchdemo.Presenter;


import android.os.Handler;

import com.lishi.demo.simplepoetrysearchdemo.Item.PoetryItem;
import com.lishi.demo.simplepoetrysearchdemo.Model.OnSearchListener;
import com.lishi.demo.simplepoetrysearchdemo.Model.PoetryCrawler;
import com.lishi.demo.simplepoetrysearchdemo.View.SearchPoetryView;

import java.util.ArrayList;
import java.util.List;

public class SearchPoetryPresenter {
    private SearchPoetryView searchPoetryView;
    private PoetryCrawler poetryCrawler;
    private Handler mHandler = new Handler();

    private List<PoetryItem> listAllPoetryItem = new ArrayList<>();

    public SearchPoetryPresenter(SearchPoetryView searchPoetryView){
        this.poetryCrawler = new PoetryCrawler();
        this.searchPoetryView = searchPoetryView;
    }

    public void search(){
        searchPoetryView.showLoading();
        poetryCrawler.SearchPoetry(searchPoetryView.getMode(),searchPoetryView.ifSearched(), searchPoetryView.getContent(), new OnSearchListener() {
            @Override
            public void searchSuccess(List<PoetryItem> list) {
                //如果是第一次搜索就把之前保存的信息清空
                if(searchPoetryView.ifSearched())
                    listAllPoetryItem.clear();

                listAllPoetryItem.addAll(list);
                mHandler.post(()->{
                    searchPoetryView.hideLoading();
                    searchPoetryView.toMainActivity(listAllPoetryItem);
                });
            }

            @Override
            public void searchFailed() {
                mHandler.post(()->{
                    searchPoetryView.hideLoading();
                    searchPoetryView.showFailedError();
                });
            }

            @Override
            public void searchOver(){
                listAllPoetryItem.clear();
                mHandler.post(()->{
                    searchPoetryView.hideLoading();
                    searchPoetryView.toMainActivity(listAllPoetryItem);
                });
            }
        });
    }

    public void clear(){
        searchPoetryView.clearPoetry();
    }
}