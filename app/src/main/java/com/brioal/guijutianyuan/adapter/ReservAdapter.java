package com.brioal.guijutianyuan.adapter;

import android.content.Context;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.model.SecionModel;
import com.brioal.guijutianyuan.util.DateFormat;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Brioal on 2016/5/28.
 */

public class ReservAdapter extends BaseSectionQuickAdapter<SecionModel> {
    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context          The context.
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public ReservAdapter(Context context, int layoutResId, int sectionHeadResId, List<SecionModel> data) {
        super(context, layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SecionModel item) {
        helper.setText(R.id.item_section_head_tv, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecionModel item) {
        helper.setText(R.id.section_content_name, item.t.getmName())
                .setText(R.id.section_content_time, DateFormat.format(item.t.getmTime()))
//                .setImageUrl(R.id.section_content_head, item.t.getmHeadUrl(mContext))
                .setText(R.id.section_content_pay, item.t.isPayed() ? "已付款" : "未付款")
                .setText(R.id.section_content_pay, "好评" + item.t.getmHot())
        ;
    }
}
