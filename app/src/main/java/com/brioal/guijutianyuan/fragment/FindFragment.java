package com.brioal.guijutianyuan.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.adapter.FindAdapter;
import com.brioal.guijutianyuan.database.DBHelper;
import com.brioal.guijutianyuan.inter.BaseInterFace;
import com.brioal.guijutianyuan.model.FindModel;
import com.brioal.guijutianyuan.util.NetWorkUtil;
import com.brioal.guijutianyuan.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2016/5/5.
 */
public class FindFragment extends Fragment implements BaseInterFace, BaseQuickAdapter.RequestLoadMoreListener {
    public static FindFragment findFragment;
    @Bind(R.id.fragment_find_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_find_refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_find_fab)
    FloatingActionButton mFab;

    private FindAdapter mAdapter;
    private List<FindModel> mList;
    private Context mContext;
    private DBHelper mHelper;
    private String TAG = "FindFragment";

    public static FindFragment getInstance() {
        if (findFragment == null) {
            findFragment = new FindFragment();
        }
        return findFragment;
    }

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


    @Override
    public void initData() {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Find", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            FindModel model = new FindModel();
            model.setmId(cursor.getString(1));
            model.setmName(cursor.getString(2));
            model.setmContent(cursor.getString(3));
            model.setmPicId(cursor.getString(4));
            model.setmParise(cursor.getInt(5));
            model.setmCommit(cursor.getInt(6));
            model.setmCollect(cursor.getInt(7));
            mList.add(model);
        }
        if (mList.size() > 0) {
            mHandler.sendEmptyMessage(0);
        }
        if (NetWorkUtil.isNetworkConnected(mContext)) {
            BmobQuery<FindModel> query = new BmobQuery<>();
            query.setLimit(15);
            query.findObjects(mContext, new FindListener<FindModel>() {
                @Override
                public void onSuccess(List<FindModel> list) {
                    Log.i(TAG, "Find加载成功内容 " + list.size());
                    mList = list;
                    mHandler.sendEmptyMessage(0);
                }

                @Override
                public void onError(int i, String s) {
                    Log.i(TAG, "onError: Find加载失败" + s);
                }
            });
        }
    }

    @Override
    public void initView() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(mRunnable).start();
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(mContext, "发表内容");
            }
        });
    }

    @Override
    public void setView() {
        mAdapter = new FindAdapter(mContext, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(true);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mHelper = new DBHelper(mContext, "TianYun.db3", null, 1);
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
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
    public void onLoadMoreRequested() {

    }
}
