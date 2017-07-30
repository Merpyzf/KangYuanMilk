package com.merpyzf.kangyuanmilk.utils.db.dao;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.bean.Address;
import com.merpyzf.kangyuanmilk.utils.IOHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wangke on 2017-07-30.
 */

public class AddressDao {

    private static Dao<Address, String> mDao = null;
    private static AddressDao mAddressDao = null;

    private AddressDao() {

        try {

            InputStream is = App.getContext().getAssets().open("address.db");

//            App.getContext().getDataDir() api24以上才有的方法
            File dbFile = new File("/data/data/"+ App.getContext().getPackageName()+"/databases","address.ad");

            if (!dbFile.exists()) {

                IOHelper.stream2File(is, dbFile);

            }

            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                    dbFile.getPath(), null);
            // ORMLite的android.jar封装SQLiteDatabase
            AndroidConnectionSource connectionSource = new AndroidConnectionSource(
                    db);

            /**
             * 以下为官方首页快速使用方式，没有任何改动的照搬，由于需要ConnectionSource对象，
             * 通过查看API发现子类AndroidConnectionSource
             */

            // instantiate the DAO to handle Account with String id
            mDao = DaoManager.createDao(
                    connectionSource, Address.class);

            // if you need to create the 'accounts' table make this call
            TableUtils.createTableIfNotExists(connectionSource,
                    Address.class);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public static AddressDao getInstance() {


        if (mAddressDao == null) {

            synchronized (Object.class) {

                if (mAddressDao == null) {

                    mAddressDao = new AddressDao();

                }

            }
        }
        return mAddressDao;

    }

    /**
     * //获取所有的省份
     * @return
     */
    public List<Address> getProvinceList(){

        List<Address> provinceList = null;
        try {
            provinceList = mDao.queryForEq("parentId", 0);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    /*    provinceList.forEach(address -> {

            LogHelper.i("省份==>"+address.getName());

        });*/



        return provinceList;

    }


}
