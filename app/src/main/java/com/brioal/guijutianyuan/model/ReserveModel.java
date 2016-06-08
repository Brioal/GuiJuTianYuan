package com.brioal.guijutianyuan.model;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Brioal on 2016/5/28.
 */

public class ReserveModel extends BmobObject {
    private BmobFile mHead ; //头像
    private String mName ; //名称
    private long mTime ; //时间
    private int mHot ; //热度
    private boolean isPayed ; //是否付款
    private boolean isDone ; //是否已用
    private String mShopId ; //商家ID

    private String mHeadUrl ;


    public ReserveModel() {
    }

    public ReserveModel(String mName, long mTime, int mHot, boolean isPayed, boolean isDone, String mShopId, String mHeadUrl) {
        this.mName = mName;
        this.mTime = mTime;
        this.mHot = mHot;
        this.isPayed = isPayed;
        this.isDone = isDone;
        this.mShopId = mShopId;
        this.mHeadUrl = mHeadUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getmHot() {
        return mHot;
    }

    public void setmHot(int mHot) {
        this.mHot = mHot;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getmShopId() {
        return mShopId;
    }

    public void setmShopId(String mShopId) {
        this.mShopId = mShopId;
    }

    public String getmHeadUrl(Context mContext) {
        return mHeadUrl==null?mHead.getFileUrl(mContext):mHeadUrl;
    }

    public void setmHeadUrl(String mHeadUrl) {
        this.mHeadUrl = mHeadUrl;
    }
}
