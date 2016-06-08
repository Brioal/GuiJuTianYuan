package com.brioal.guijutianyuan.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Brioal on 2016/5/28.
 */

public class ADModel extends BmobObject {
    private int mId ; //Id
    private String mIndex ; //前缀
    private String mContent ; //内容
    private String mUrl ; //链接


    public ADModel() {
    }

    public ADModel(int mId, String mIndex, String mContent, String mUrl) {
        this.mId = mId;
        this.mIndex = mIndex;
        this.mContent = mContent;
        this.mUrl = mUrl;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmIndex() {
        return mIndex;
    }

    public void setmIndex(String mIndex) {
        this.mIndex = mIndex;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
