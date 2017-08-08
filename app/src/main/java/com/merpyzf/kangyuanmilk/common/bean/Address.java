package com.merpyzf.kangyuanmilk.common.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wangke on 2017-07-30.
 */

@DatabaseTable(tableName = "address_dict")
public class Address {


    @DatabaseField(columnName = "id")
    private int id;
    @DatabaseField(columnName = "parentId")
    private int parentId;
    @DatabaseField(columnName = "code")
    private String code;
    @DatabaseField(columnName = "name")
    private String name;

    public Address() {
    }

    public Address(int id, int parentId, String code, String name) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
