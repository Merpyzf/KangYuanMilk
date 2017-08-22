package com.merpyzf.kangyuanmilk.utils.db.dao;

import com.j256.ormlite.dao.Dao;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchHistoryBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.TimeHelper;
import com.merpyzf.kangyuanmilk.utils.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchDao {

    private static Dao<SearchHistoryBean, Integer> mDao = null;
    private static SearchDao mSearchDao = null;


    public SearchDao() {

        mDao = DBHelper.getSingleInstance(App.getContext())
                .getDao(SearchHistoryBean.class);
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
     * @param searchHistoryBean
     * @return num 影响的行数
     */
    public int saveSearchData(SearchHistoryBean searchHistoryBean) {

        int num = -1;
        try {

            //先查询记录中是否已经存在这条搜索记录

            //如果有的话,直接根据查询出来的id更新这条搜索记录的事件

            //如果没有则直接插入进去

            List<SearchHistoryBean> search_info = mDao.queryBuilder().where().eq("search_info", searchHistoryBean.getSearchInfo()).query();

            //表示这个关键字在之前已经查询过
            if (search_info.size() == 1) {
                num = mDao.updateRaw("UPDATE tab_search SET  search_date = ? WHERE search_info = ?", TimeHelper.getDateTime(System.currentTimeMillis()), searchHistoryBean.getSearchInfo());

                return num;
            }
            num = mDao.create(searchHistoryBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;

    }

    /**
     * 删除一条历史查询的数据
     *
     * @param searchHistoryBean
     */
    public int delSearchData(SearchHistoryBean searchHistoryBean) {

        int num = -1;

        try {
            num = mDao.delete(searchHistoryBean);
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

        List<SearchHistoryBean> searchHistoryBeanList = getAllhistorySearchData();

        LogHelper.i("长度==》" + searchHistoryBeanList.size());


        for (int i = 0; i < searchHistoryBeanList.size(); i++) {


            try {
                mDao.delete(searchHistoryBeanList.get(i));
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
    public static List<SearchHistoryBean> getAllhistorySearchData() {

        try {
            List<SearchHistoryBean> searchDataList = mDao.queryBuilder().orderBy("search_date", false).query();
            return searchDataList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


}


