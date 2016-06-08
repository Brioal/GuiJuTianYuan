package com.brioal.guijutianyuan.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.model.MyUser;
import com.brioal.guijutianyuan.util.LocalUserUtil;
import com.brioal.guijutianyuan.util.ToastUtils;
import com.dd.CircularProgressButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Brioal on 2016/5/10.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    public static LoginFragment mFragment;
    AppCompatMultiAutoCompleteTextView mUsername;
    AppCompatMultiAutoCompleteTextView mPassword;
    CircularProgressButton mBtnLogin;
    Button mWeixin;
    Button mWeibo;
    Button mQQ;
    private Timer timer;

    private MyUser mUser;
    private Context mContext;
    private boolean isComplete = false;
    private String TAG = "LoginInfo";
    private int progress;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (isComplete) {
                    mBtnLogin.setProgress(100);
                } else {
                    mBtnLogin.setProgress(99);
                    progress = 0;
                }
            } else {
                mBtnLogin.setProgress(msg.what);
            }
        }
    };

    public static LoginFragment getInstance() {
        if (mFragment == null) {
            mFragment = new LoginFragment();
        }
        return mFragment;
    }

    public void initId(View rootView) {
        mUsername = (AppCompatMultiAutoCompleteTextView) rootView.findViewById(R.id.login_et_username);
        mPassword = (AppCompatMultiAutoCompleteTextView) rootView.findViewById(R.id.login_et_password);
        mBtnLogin = (CircularProgressButton) rootView.findViewById(R.id.login_et_btn_login);
        mWeixin = (Button) rootView.findViewById(R.id.login_btn_weixin);
        mWeibo = (Button) rootView.findViewById(R.id.login_btn_weibo);
        mQQ = (Button) rootView.findViewById(R.id.login_btn_qq);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initId(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActions();
        initView();
    }

    private void initActions() {
        mBtnLogin.setOnClickListener(this);
    }

    private void initView() {
        Drawable weiXinDrawable = mWeixin.getCompoundDrawables()[1];
        weiXinDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN));
        Drawable weiBoDrawable = mWeibo.getCompoundDrawables()[1];
        weiBoDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorOrange), PorterDuff.Mode.SRC_IN));
        Drawable weiQQDrawable = mQQ.getCompoundDrawables()[1];
        weiQQDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorBlue), PorterDuff.Mode.SRC_IN));

    }

    //登陆
    public void login() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progress += 20;
                if (progress == 120) {
                    progress = 100;
                }
                mHandler.sendEmptyMessage(progress);
            }
        }, 100);
        BmobUser.loginByAccount(mContext, mUsername.getText().toString(), mPassword.getText().toString(), new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if (user != null) {
                    mUser = user;
                    String objectId = mUser.getObjectId();
                    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                    query.addWhereEqualTo("objectId", objectId);
                    query.findObjects(mContext, new FindListener<MyUser>() {
                        @Override
                        public void onSuccess(List<MyUser> list) {
                            MyUser user1 = list.get(0);
                            Log.i(TAG, "onSuccess: " + user1.getmHeadUrl(mContext));
                            if (timer != null) {
                                timer.cancel();
                            }
                            LocalUserUtil.Save(mContext, list.get(0));
                            isComplete = true;
                            mBtnLogin.setProgress(100);
                            ToastUtils.showToast(mContext, "登录成功");
                            mBtnLogin.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().setResult(Activity.RESULT_OK);
                                    getActivity().finish();
                                }
                            }, 1000);
                        }

                        @Override
                        public void onError(int i, String s) {
                            if (timer != null) {
                                timer.cancel();
                            }
                            mBtnLogin.setProgress(-1);
                        }
                    });


                } else {
                    Log.i(TAG, "done: 登陆失败");
                    // TODO: 2016/5/10 LoginFiled
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_et_btn_login) {
            login();
        }
    }
}
