package com.lishi.demo.simplepoetrysearchdemo;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.lishi.demo.simplepoetrysearchdemo.Adapter.PoetryListViewAdapter;
import com.lishi.demo.simplepoetrysearchdemo.Item.PoetryItem;
import com.lishi.demo.simplepoetrysearchdemo.Presenter.SearchPoetryPresenter;
import com.lishi.demo.simplepoetrysearchdemo.View.SearchPoetryView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchPoetryView {
    private ListView mPoetryListView;
    private PoetryListViewAdapter mPoetryListViewAdapter;

    private Toolbar activityToolbar;
    private SearchPoetryPresenter mSearchPoetryPresenter = new SearchPoetryPresenter(this);
    private km.lmy.searchview.SearchView mSearchView;
    private SearchView mSearchBut;

    private String SearchContent;

    private boolean searched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        init();
    }


    private void init(){
        //init toolbar
        this.activityToolbar = findViewById(R.id.activity_toolbar);
        this.activityToolbar.setTitle(this.getTitle());
        this.activityToolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(activityToolbar);

        //init ListView
        this.mPoetryListView = (ListView) findViewById(R.id.PoetryListView);
        addListViewFootView();
        this.mPoetryListViewAdapter = new PoetryListViewAdapter(this,new ArrayList<PoetryItem>());
        this.mPoetryListView.setAdapter(this.mPoetryListViewAdapter);

        //init SearchView
        this.mSearchView = findViewById(R.id.searchView);
        List<String> HistoryList = new ArrayList<>();
        this.mSearchView.setNewHistoryList(HistoryList);

        this.mSearchView.setOnSearchActionListener((v)->{
            SearchContent = this.mSearchView.getEditTextView().getText().toString();
            this.mSearchView.addOneHistory(SearchContent);
            searched = false;
            this.mSearchPoetryPresenter.search();
            this.mSearchView.getEditTextView().setText("");
            this.mSearchView.close();
        });

        //init button
        this.mSearchBut = findViewById(R.id.search);
        this.mSearchBut.setOnSearchClickListener((v)->{
            this.mSearchBut.onActionViewCollapsed();
            this.mSearchView.open();
        });
    }

    public void addListViewFootView(){
        View footer = this.getLayoutInflater().inflate(R.layout.litview_footview, null);
        ProgressBar footProgressBar = (ProgressBar) footer.findViewById(R.id.listview_footview_progressBar);
        TextView foorTextView = (TextView) footer.findViewById(R.id.listview_footview_textview);
        this.mPoetryListView.addFooterView(footer);

        footer.setVisibility(View.GONE);


    }

    /*
     * 实现OnSearchListener接口
     */
    @Override
    public void clearPoetry(){
        //TODO
    }

    @Override
    public int getMode(){
        //TODO
        // 还没有添加类型选择
        String text = "作者";
        int mode;
        if(text.equals("作者"))
            mode = 1;
        else
            mode = 0;
        return mode;
    }

    @Override
    public boolean ifSearched(){
        return searched;
    }

    @Override
    public String getContent(){
        //TODO
        //获取信息
        return this.SearchContent;
    }

    @Override
    public void showFailedError(){
        Toast.makeText(this,"failed to load poetry",Toast.LENGTH_LONG);
    }

    @Override
    public void showLoading(){
        //TODO
        Toast.makeText(this,"loading",Toast.LENGTH_LONG);
    }

    @Override
    public void hideLoading(){
        //TODO
    }

    //将诗句传送到主界面
    @Override
    public void toMainActivity(List<PoetryItem> list){
        //TODO
        /*this.mPoetryItemList.clear();
        this.mPoetryItemList.addAll(list);*/
        this.mPoetryListViewAdapter.update(list);
        this.mPoetryListViewAdapter.notifyDataSetChanged();
    }
}
