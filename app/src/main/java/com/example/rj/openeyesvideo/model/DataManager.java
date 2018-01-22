package com.example.rj.openeyesvideo.model;

import com.example.rj.openeyesvideo.APP.Constants;
import com.example.rj.openeyesvideo.model.DB.DBHelper;
import com.example.rj.openeyesvideo.model.bean.DailyBean;
import com.example.rj.openeyesvideo.model.bean.DownloadBean;
import com.example.rj.openeyesvideo.model.bean.HistoryBean;
import com.example.rj.openeyesvideo.model.bean.HotBean;
import com.example.rj.openeyesvideo.model.bean.ItemListBean;
import com.example.rj.openeyesvideo.model.bean.LikeBean;
import com.example.rj.openeyesvideo.model.bean.RelateBean;
import com.example.rj.openeyesvideo.model.bean.ReplyBean;
import com.example.rj.openeyesvideo.model.bean.SearchResultBean;
import com.example.rj.openeyesvideo.model.bean.TagChildBean;
import com.example.rj.openeyesvideo.model.bean.TagsBean;
import com.example.rj.openeyesvideo.model.http.ApiHelper;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by rj on 2017/12/18.
 */

public class DataManager implements ApiHelper,DBHelper{

    ApiHelper mApiHelper;
    DBHelper mDBHelper;



    public DataManager(ApiHelper apiHelper, DBHelper dbHelper){
        this.mApiHelper=apiHelper;
        this.mDBHelper=dbHelper;
    }

    @Override
    public Flowable<List<String>> getTrendingTagInfo() {
        return mApiHelper.getTrendingTagInfo();
    }

    @Override
    public Flowable<SearchResultBean> query(String key, int start) {
        return mApiHelper.query(key,start);
    }

    @Override
    public Flowable<DailyBean> getDailyBean() {
        return mApiHelper.getDailyBean();
    }

    @Override
    public Flowable<DailyBean> getDailyBean(long date) {
        return mApiHelper.getDailyBean(date);
    }



    @Override
    public Flowable<HotBean> getWeekHotBean(String type) {
        return mApiHelper.getWeekHotBean(type);
    }

    @Override
    public Flowable<List<TagsBean>> getTagsBean() {
        return mApiHelper.getTagsBean();
    }

    @Override
    public Flowable<TagChildBean> getTagChildBean(int start, int num, int id) {
        return mApiHelper.getTagChildBean(start,num,id);
    }

    @Override
    public Flowable<RelateBean> getRelateBean(int id) {
        return mApiHelper.getRelateBean(id);
    }

    @Override
    public Flowable<ItemListBean.DataBean> getDataBean(int id) {
        return mApiHelper.getDataBean(id);
    }

    @Override
    public Flowable<ReplyBean> getReplyBean(int id) {
        return mApiHelper.getReplyBean(id);
    }

    @Override
    public Flowable<ReplyBean> getMoreReplyBean(int id, int lastId, int num) {
        return mApiHelper.getMoreReplyBean(id,lastId,num);
    }

//    @Override
//    public Flowable<HotBean> getHistroicalHotBean() {
//        return mApiHelper.getHistroicalHotBean();
//    }

    @Override
    public void insertReadId(ItemListBean historyBean) {
        mDBHelper.insertReadId(historyBean);
    }

    @Override
    public void insertLikeId(ItemListBean likeBean) {
        mDBHelper.insertLikeId(likeBean);
    }

    @Override
    public void deleteLikeId(int id) {
        mDBHelper.deleteLikeId(id);
    }

    @Override
    public void deleteReadId(int id) {
        mDBHelper.deleteReadId(id);
    }

    @Override
    public List<HistoryBean> getHistoryBeans() {
        return mDBHelper.getHistoryBeans();
    }

    @Override
    public List<LikeBean> getLikeBeans() {
        return mDBHelper.getLikeBeans();
    }

    @Override
    public void checkHistoryTime(int id, long time) {

    }

    @Override
    public void checkLikeTime(int id, long time) {

    }

    @Override
    public HistoryBean getHistoryBean(int id) {
        return mDBHelper.getHistoryBean(id);
    }

    @Override
    public boolean checkLike(int id) {
        return mDBHelper.checkLike(id);
    }

    @Override
    public int checkDownload(int id) {
        return mDBHelper.checkDownload(id);
    }

    @Override
    public List<DownloadBean> getDownloadBeans() {
        return mDBHelper.getDownloadBeans();
    }

    @Override
    public void insertDownloadId(ItemListBean itemListBean) {
        mDBHelper.insertDownloadId(itemListBean);
    }

    @Override
    public void deleteDownloadId(int id) {
        mDBHelper.deleteDownloadId(id);
    }

    @Override
    public void setDownload(int id) {
        mDBHelper.setDownload(id);
    }

    public void download(String url, final ItemListBean itemListBean){
        FileDownloader.getImpl().create(url)
                .setPath(Constants.PATH_DOWNLOAD)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        setDownload(itemListBean.getData().getId());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }

}
