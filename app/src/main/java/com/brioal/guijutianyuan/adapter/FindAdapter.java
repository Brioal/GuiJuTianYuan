package com.brioal.guijutianyuan.adapter;

import android.content.Context;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.model.FindModel;
import com.brioal.guijutianyuan.util.DateFormat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Brioal on 2016/5/28.
 */

public class FindAdapter extends BaseQuickAdapter<FindModel> {
    private Context mContext;
    public FindAdapter(Context context, List<FindModel> data) {
        super(context, R.layout.item_find, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindModel item) {
        helper.setText(R.id.item_find_name, item.getmName()) //名称
        .setText(R.id.item_find_time, DateFormat.format(item.getmTime())) //时间
        .setText(R.id.item_find_content,item.getmContent()) //内容
        .setImageUrl(R.id.item_find_head,item.getmHeadUrl(mContext)) //头像
        .setText(R.id.item_find_parise,item.getmParise()+"") // 点赞数量
        .setText(R.id.item_find_commit,item.getmCommit()+"") //评论数量
        .setText(R.id.item_find_collect,item.getmCollect()+"") // 收藏数量
        ;
    }
}
