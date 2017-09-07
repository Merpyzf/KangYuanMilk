package com.merpyzf.kangyuanmilk.ui.home.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.merpyzf.kangyuanmilk.utils.CalendarUtils;

/**
 * Created by wangke on 2017-08-01.
 * 封装历史搜索信息
 */
@DatabaseTable(tableName = "tab_search")
public class SearchHistoryBean {

    @DatabaseField(columnName = "_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "search_info")
    private String searchInfo;
    @DatabaseField(columnName = "search_date")
    private String date;

    public SearchHistoryBean(String searchInfo) {

        this.searchInfo = searchInfo;
        this.date = CalendarUtils.getDateTime(System.currentTimeMillis());
    }


    public SearchHistoryBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
