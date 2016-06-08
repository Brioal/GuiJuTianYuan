package com.brioal.guijutianyuan.adapter;

import android.content.Context;
import android.util.Log;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.model.ContentModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Brioal on 2016/5/28.
 */

public class HomeAdapter extends BaseQuickAdapter<ContentModel> {
    private Context mCOntext;
    private String TAG = "HomeAdapter";

    public HomeAdapter(Context context, List<ContentModel> data) {
        super(context, R.layout.item_content, data);
        mCOntext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentModel item) {
        Log.i(TAG, "convert: "+item.getmTitle());
        Log.i(TAG, "convert: "+item.getmDesc());
        Log.i(TAG, "convert: "+item.getmParise());
        Log.i(TAG, "convert: "+item.getmRead());
        helper.setText(R.id.item_content_title, item.getmTitle()) //标题
                .setText(R.id.item_content_desc, item.getmDesc()) //描述
                .setText(R.id.item_content_parise, "好评率" + item.getmParise() + "%") //好评
                .setText(R.id.item_content_read, "浏览" + item.getmRead()) //浏览
                .setImageUrl(R.id.item_content_image, item.getmUrl(mCOntext)); //头像
    }
}
