package com.brioal.guijutianyuan.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.activity.QueryActivity;
import com.brioal.guijutianyuan.adapter.HomeAdapter;
import com.brioal.guijutianyuan.bgabanner.BGABanner;
import com.brioal.guijutianyuan.citypicker.CityPickerActivity;
import com.brioal.guijutianyuan.database.DBHelper;
import com.brioal.guijutianyuan.inter.BaseInterFace;
import com.brioal.guijutianyuan.model.BannerModel;
import com.brioal.guijutianyuan.model.ContentModel;
import com.brioal.guijutianyuan.util.NetWorkUtil;
import com.brioal.guijutianyuan.util.ToastUtils;
import com.bumptech.glide.Glide;
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
public class HomeFragment extends Fragment implements BaseInterFace, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    public static HomeFragment homeFragment;

    @Bind(R.id.home_mainContainer)
    LinearLayout mContainer;
    @Bind(R.id.main_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar_location)
    TextView mLocation;
    @Bind(R.id.toolbar_query)
    TextView mQuery;
    @Bind(R.id.toolBar_msg)
    ImageButton mMsg;

    private Context mContext;
    private DBHelper mHelper;
    private String TAG = "HomeFragment";

    private List<ContentModel> mList;
    private List<BannerModel> mBanner;
    private HomeAdapter mAdapter;
    private View mHeadBanner; //首页轮播图


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setView();
        }
    };

    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    @Override
    public void setView() {
        mAdapter = new HomeAdapter(mContext, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(true);
        mAdapter.setOnLoadMoreListener(this);
        addBannerView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }


    //添加头部Banner
    public void addBannerView() {
        mHeadBanner = LayoutInflater.from(mContext).inflate(R.layout.item_banner, mContainer, false);
        BGABanner banner = (BGABanner) mHeadBanner.findViewById(R.id.home_banner);
        List<View> views = new ArrayList<>();
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < mBanner.size(); i++) {
            final BannerModel model = mBanner.get(i);
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext).load(model.getmImageUrl(mContext)).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            views.add(imageView);
            tips.add(model.getmTip());
        }

        banner.setViews(views);
        banner.setTips(tips);
        banner.setPageChangeDuration(2000);
        mAdapter.addHeaderView(mHeadBanner);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mHelper = new DBHelper(mContext, "TianYun.db3", null, 1);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // TODO: 2016/5/6 主界面
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBanner();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();
        initView();
    }

    private void initBanner() {

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
        }
        if (mBanner == null) {
            mBanner = new ArrayList<>();
        }

        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from Banner", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            BannerModel model = new BannerModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            mBanner.add(model);
        }
        Cursor cursorContent = null;
        database = mHelper.getReadableDatabase();
        cursorContent = database.rawQuery("select * from Content", null);
        while (cursorContent.moveToNext()) {
            ContentModel model = new ContentModel(cursorContent.getString(1), cursorContent.getString(2), cursorContent.getString(3), cursorContent.getInt(4), cursorContent.getInt(5), cursorContent.getString(6));
            mList.add(model);
        }
        if (mList.size() > 0) {
            mHandler.sendEmptyMessage(0);
        }
        if (NetWorkUtil.isNetworkConnected(mContext)) {

            BmobQuery<BannerModel> queryModel = new BmobQuery<BannerModel>();
            queryModel.setLimit(20);
            queryModel.findObjects(mContext, new FindListener<BannerModel>() {
                @Override
                public void onSuccess(List<BannerModel> list) {
                    Log.i(TAG, "onSuccess: 加载成功" + list.size() + "条Banner");
                    if (mBanner.size() > 0) {
                        mBanner.clear();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        mBanner.add(list.get(i));
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Log.i(TAG, "onError: 加载失败" + s);
                }
            });

            BmobQuery<ContentModel> queryContent = new BmobQuery<>();
            queryContent.findObjects(mContext, new FindListener<ContentModel>() {
                @Override
                public void onSuccess(List<ContentModel> list) {
                    Log.i(TAG, "onSuccess: 加载成功" + list.size() + "条内容");
                    mList = list;
                    mHandler.sendEmptyMessage(0);
                }

                @Override
                public void onError(int i, String s) {
                    Log.i(TAG, "onError: 加载失败" + s);
                }
            });
        }


    }

    @Override
    public void initView() {
        mLocation.setOnClickListener(this);
        mQuery.setOnClickListener(this);
        mMsg.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_location:
                // TODO: 2016/5/26 Location
                mContext.startActivity(new Intent(mContext, CityPickerActivity.class));

                break;
            case R.id.toolbar_query:
                // TODO: 2016/5/26 Query
                mContext.startActivity(new Intent(mContext, QueryActivity.class));
                break;
            case R.id.toolBar_msg:
                // TODO: 2016/5/26 Message
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        ToastUtils.showToast(mContext, "加载更多");
    }
}
