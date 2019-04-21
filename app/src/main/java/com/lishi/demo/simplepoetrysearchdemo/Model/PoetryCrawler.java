package com.lishi.demo.simplepoetrysearchdemo.Model;

import com.lishi.demo.simplepoetrysearchdemo.Item.PoetryItem;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PoetryCrawler implements PoetryCrawlerBiz{
    private final String mPoetryWebUrl = "https://so.gushiwen.org/search.aspx?";
    private String mSearchType = "title";
    private int PageCount = 0;                  //当前搜索内容的总页数
    private int NowCount = 1;                   //当前搜索到的页数


    /*
     * 1-> author
     * 2-> poetry
     */
    @Override
    public void SearchPoetry(int searchMode,boolean searched, String content, final OnSearchListener searchListener){
        new Thread( () ->{
            if(searchMode == 1)
                this.mSearchType = "author";
            else
                this.mSearchType = "title";

            //test
            System.out.println("ifSearched: "+searched);

            if(!searched)
                NowCount = 1;

            //生成目标url
            String TarUrl = this.mPoetryWebUrl + "type="+this.mSearchType +"&page=" + NowCount +"&value="+content;
            System.out.println(TarUrl);
            try {
                Connection conn = Jsoup.connect(TarUrl).timeout(3000);
                Document doc = Jsoup.parse(conn.get().html());
                Elements ListDiv = doc.getElementsByAttributeValue("class","sons");

                List<PoetryItem> list = new ArrayList<>();

                if(NowCount == 1) {
                    PageCount = Integer.parseInt(doc.getElementsByAttributeValue("id", "sumPage").get(0).text());
                    System.out.println("sumPage: "+PageCount);
                }
                else if(NowCount > PageCount) {
                    searchListener.searchOver();      //如果已经搜索到所有页面，结束所有搜索
                    return;
                }

                NowCount++;
                for(Element element:ListDiv){
                    Element contson = element.getElementsByAttributeValue("class","contson").get(0);
                    Element source = element.getElementsByAttributeValue("class","source").get(0);
                    Element title = element.getElementsByTag("b").get(0);
                    String AfterRe = contson.html().replaceAll("<br>","").replaceAll("</?[^>]+>","").replaceAll("\\(.*?\\)","");
                    list.add(new PoetryItem(AfterRe,title.text(),source.text()));
                }
                //test
                System.out.println("sum of poetry: "+ListDiv.size());

                searchListener.searchSuccess(list);
            }
            catch(IOException e){
                e.printStackTrace();
                searchListener.searchFailed();
            }

        }).start();
    }
}
