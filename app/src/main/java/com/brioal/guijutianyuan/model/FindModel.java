package com.brioal.guijutianyuan.model;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Brioal on 2016/5/28.
 */

public class FindModel extends BmobObject {
    private BmobFile mHead ; //用户头像
    private String mId; //用户ID
    private String mName; //用户名称
    private String mContent; //文字内容
    private String mPicId; //图片的索引
    private long mTime; //时间
    private int mParise; //点赞数
    private int mCommit; //评论数
    private int mCollect; //收藏的数量

    private String mHeadUrl;

    public FindModel() {
    }

    public FindModel(String mId, String mName, String mContent, String mPicId, long mTime, int mParise, int mCommit, int mCollect, String mHeadUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mContent = mContent;
        this.mPicId = mPicId;
        this.mTime = mTime;
        this.mParise = mParise;
        this.mCommit = mCommit;
        this.mCollect = mCollect;
        this.mHeadUrl = mHeadUrl;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmPicId() {
        return mPicId;
    }

    public void setmPicId(String mPicId) {
        this.mPicId = mPicId;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getmParise() {
        return mParise;
    }

    public void setmParise(int mParise) {
        this.mParise = mParise;
    }

    public int getmCommit() {
        return mCommit;
    }

    public void setmCommit(int mCommit) {
        this.mCommit = mCommit;
    }

    public int getmCollect() {
        return mCollect;
    }

    public void setmCollect(int mCollect) {
        this.mCollect = mCollect;
    }

    public String getmHeadUrl(Context mContext) {
        return mHeadUrl==null?mHead.getFileUrl(mContext):mHeadUrl;
    }

    public void setmHeadUrl(String mHeadUrl) {
        this.mHeadUrl = mHeadUrl;
    }
}
