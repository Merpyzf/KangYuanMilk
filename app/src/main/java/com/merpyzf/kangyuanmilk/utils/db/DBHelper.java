package com.merpyzf.kangyuanmilk.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.ui.base.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangke on 2017-07-26.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static DBHelper instance;


    private DBHelper(Context context) {
        super(context, Common.USER_DB_NAME, null, 1);
    }


    public static DBHelper getSingleInstance(Context context) {

        if (instance == null) {

            synchronized (Object.class) {

                if (instance == null) {

                    instance = new DBHelper(context);
                }

            }


        }
        return instance;
    }


    /**
     * 创建数据库
     *
     * @param sqLiteDatabase
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {

            TableUtils.createTable(connectionSource, User.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 更新数据库
     *
     * @param sqLiteDatabase
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,User.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public synchronized Dao getDao(Class clazz) {

        Dao dao = null;

        String clazzName = clazz.getSimpleName();

        if (daos.containsKey(clazzName)) {

            dao = daos.get(clazzName);
        }

        if (dao == null) {

            try {

                dao = super.getDao(clazz);
                daos.put(clazzName, dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return dao;

    }

    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }

    }
}
