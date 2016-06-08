package com.brioal.guijutianyuan.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.activity.LoginAndRegisterActivity;
import com.brioal.guijutianyuan.inter.BaseInterFace;
import com.brioal.guijutianyuan.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Brioal on 2016/5/5.
 */
public class UserFragment extends Fragment implements BaseInterFace{
    public static UserFragment userFragment;
    @Bind(R.id.fragment_user_image_head)
    CircleImageView mImageHead;
    @Bind(R.id.fragment_user_btn_login)
    Button mBtnLogin;
    private Context mContext;

    public static UserFragment getInstance() {
        if (userFragment == null) {
            userFragment = new UserFragment();
        }
        return userFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        setView();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setView() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginAndRegisterActivity.class));
            }
        });
    }
}
