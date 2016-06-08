package com.brioal.guijutianyuan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brioal.guijutianyuan.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QueryActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.query_back)
    ImageButton mBtnBack;
    @Bind(R.id.query_searchView)
    EditText mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_back:
                finish();
                break;
        }
    }
}
