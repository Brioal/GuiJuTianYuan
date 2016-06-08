package com.brioal.guijutianyuan.model;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 农家乐内容列表
 * Created by Brioal on 2016/5/24.
 */

public class ContentModel extends BmobObject {
    private BmobFile mPic; //图片
    private String mTitle; // 标题
    private String mDesc; //秒数
    private String mId; //农家乐详情的ID
    private int mRead; //浏览量
    private int mParise; //好评率


    private String mUrl;

    public ContentModel() {
    }

    public ContentModel(String mTitle, String mDesc, String mId, int mRead, int mParise, String mUrl) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mId = mId;
        this.mRead = mRead;
        this.mParise = mParise;
        this.mUrl = mUrl;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public int getmRead() {
        return mRead;
    }

    public void setmRead(int mRead) {
        this.mRead = mRead;
    }

    public int getmParise() {
        return mParise;
    }

    public void setmParise(int mParise) {
        this.mParise = mParise;
    }

    public String getmUrl(Context mContext) {
        return mUrl == null ? mPic.getFileUrl(mContext) : mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
