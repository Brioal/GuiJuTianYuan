package com.brioal.guijutianyuan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.util.BrioalConstant;
import com.brioal.guijutianyuan.util.BrioalUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class LauncherActivity extends AppCompatActivity {


    @Bind(R.id.launcher_back)
    ImageView mBack;
    @Bind(R.id.launcher_logo)
    ImageView mLogo;

    private Context mContext;
    private String TAG = "Launcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);

        initSdk();
        initWindow();
        startAnimation();
    }

    //初始化SDK
    private void initSdk() {
        BrioalUtil.init(this);
        Bmob.initialize(this, BrioalConstant.APPID);
    }

    //初始化窗口
    private void initWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //开始动画
    private void startAnimation() {
        //背景动画
        final Animation backAnimation = AnimationUtils.loadAnimation(this, R.anim.launcher_back);
        backAnimation.setDuration(3000);
        backAnimation.setInterpolator(new DecelerateInterpolator());
        backAnimation.setFillAfter(true);
        backAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_down);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //Logo动画
        final Animation logoAnimation = AnimationUtils.loadAnimation(mContext, R.anim.launcher_logo);
        logoAnimation.setDuration(1500);
        logoAnimation.setFillAfter(true);
        logoAnimation.setInterpolator(new DecelerateInterpolator());
        Glide.with(mContext).load(R.mipmap.ic_launcher_back).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Log.i(TAG, "onResourceReady: ");
                mBack.setImageDrawable(glideDrawable);
                mBack.startAnimation(backAnimation);
                mLogo.startAnimation(logoAnimation);
            }
        });


    }
}
