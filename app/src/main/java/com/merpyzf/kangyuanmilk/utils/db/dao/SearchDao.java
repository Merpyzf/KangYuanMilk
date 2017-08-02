package com.merpyzf.kangyuanmilk.utils.db.dao;

import com.j256.ormlite.dao.Dao;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchDao {

    private static Dao<SearchBean, Integer> mDao = null;
    private static SearchDao mSearchDao = null;


    public SearchDao() {

        mDao = DBHelper.getSingleInstance(App.getContext())
                .getDao(SearchBean.class);
    }

    public static SearchDao getInstance() {

        if (mSearchDao == null) {


            synchronized (Object.class) {

                if (mSearchDao == null) {

                    mSearchDao = new SearchDao();

                }

            }


        }

        return mSearchDao;

    }

    /**
     * 保存历史查询记录到数据库
     *
     * @param searchBean
     * @return num 影响的行数
     */
    public int saveSearchData(SearchBean searchBean) {

        int num = -1;
        try {
            num = mDao.create(searchBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;

    }

    /**
     * 删除一条历史查询的数据
     *
     * @param searchBean
     */
    public int delSearchData(SearchBean searchBean) {

        int num = -1;

        try {
            num = mDao.delete(searchBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;

    }

    /**
     * 清除所有查询记录
     *
     * @return
     */
    public void delAllSearchData() {

        List<SearchBean> searchBeanList = getAllhistorySearchData();

        LogHelper.i("长度==》"+searchBeanList.size());


        for(int i=0;i<searchBeanList.size();i++){


            try {
                mDao.delete(searchBeanList.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }



    }

    /**
     * 查询所有的关键字搜索记录
     *
     * @return
     */
    public static List<SearchBean> getAllhistorySearchData() {

        try {
            List<SearchBean> searchDataList = mDao.queryBuilder().orderBy("search_date", false).query();
            return searchDataList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


}


