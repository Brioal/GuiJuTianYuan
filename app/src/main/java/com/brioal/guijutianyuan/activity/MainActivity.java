package com.brioal.guijutianyuan.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.brioal.guijutianyuan.R;
import com.brioal.guijutianyuan.fragment.FindFragment;
import com.brioal.guijutianyuan.fragment.HomeFragment;
import com.brioal.guijutianyuan.fragment.OrderFragment;
import com.brioal.guijutianyuan.fragment.UserFragment;
import com.brioal.guijutianyuan.util.StatusBarUtils;
import com.brioal.guijutianyuan.view.adapter.BottomBaseAdapter;
import com.brioal.guijutianyuan.view.view.ButtomTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_container)
    RelativeLayout mContainer;
    @Bind(R.id.bottom_tablayout)
    ButtomTabLayout mTablayout;

    private Context mContext;


    private FragmentManager manager;
    private String[] texts = new String[]{
            "首页",
            "动态",
            "预定",
            "个人中心"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        StatusBarUtils.setColor(this,getResources().getColor(R.color.colorPrimary));
        initTab();
        initActions();
        changeFragment(HomeFragment.getInstance());
    }

    private void initActions() {
    }

    private void initTab() {
        final Drawable[] images = new Drawable[]{
                getResources().getDrawable(R.mipmap.ic_home),
                getResources().getDrawable(R.mipmap.ic_find),
                getResources().getDrawable(R.mipmap.ic_reserve),
                getResources().getDrawable(R.mipmap.ic_user)
        };
        mTablayout.setBackgroundColor(Color.WHITE);
        mTablayout.setmCurrentCheck(0);
        mTablayout.setSelectItem(new ButtomTabLayout.onSelectItem() {
            @Override
            public void onSelectedItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = HomeFragment.getInstance();
                        break;
                    case 1:
                        fragment = FindFragment.getInstance();
                        break;
                    case 2:
                        fragment = OrderFragment.getInstance();
                        break;
                    case 3:
                        fragment = UserFragment.getInstance();
                        break;
                }
                changeFragment(fragment);
            }
        });
        mTablayout.setmCheckedPerscent(1.4f);
        mTablayout.setmCheckedColor(getResources().getColor(R.color.colorPrimary));
        mTablayout.setmNormalColor(Color.GRAY);
        mTablayout.setmAdapter(new BottomBaseAdapter() {
            @Override
            public int getItemCount() {
                return texts.length;
            }

            @Override
            public Drawable getDrawable(int position) {
                return images[position];
            }

            @Override
            public CharSequence getText(int position) {
                return texts[position];
            }
        });
    }

    public void changeFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            manager.beginTransaction().replace(mContainer.getId(), fragment).commit();
        } else {
            manager.beginTransaction().add(mContainer.getId(), fragment).commit();
        }
    }


}
