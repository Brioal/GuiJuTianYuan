package com.brioal.guijutianyuan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.adapter.ReservAdapter;
import com.brioal.guijutianyuan.database.DBHelper;
import com.brioal.guijutianyuan.inter.BaseInterFace;
import com.brioal.guijutianyuan.model.ReserveModel;
import com.brioal.guijutianyuan.model.SecionModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Brioal on 2016/5/5.
 */
public class OrderFragment extends Fragment implements BaseInterFace {
    public static OrderFragment orderFragment;
    @Bind(R.id.fragment_reserve_toolBar)
    Toolbar mToolBar;
    @Bind(R.id.fragment_reserve_recyclerView)
    RecyclerView mRecyclerView;

    public static OrderFragment getInstance() {
        if (orderFragment == null) {
            orderFragment = new OrderFragment();
        }
        return orderFragment;
    }

    private ReservAdapter mAdapter;
    private Context mContext;
    private List<SecionModel> mList;
    private DBHelper mHelper;
    private String TAG = "ReserveFragment";


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setView();
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            initData();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mHelper = new DBHelper(mContext, "TianYun.db3", null, 1);
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        new Thread(mRunnable).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initData() {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }

        mList.add(new SecionModel(true, "农家乐1"));
        mList.add(new SecionModel(new ReserveModel("活动1", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动2", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动3", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动4", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(true, "农家乐2"));
        mList.add(new SecionModel(new ReserveModel("活动1", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动2", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动3", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(true, "农家乐3"));
        mList.add(new SecionModel(new ReserveModel("活动1", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动2", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动3", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动4", System.currentTimeMillis(), 50, true, false, "id", "")));
        mList.add(new SecionModel(new ReserveModel("活动5", System.currentTimeMillis(), 50, true, false, "id", "")));
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void initView() {
//        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Thread(mRunnable).start();
//            }
//        });
    }

    @Override
    public void setView() {
        mAdapter = new ReservAdapter(mContext,R.layout.item_section_content, R.layout.item_section_head,  mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }
}
