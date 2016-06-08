package com.brioal.guijutianyuan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.brioal.guijutianyuan.model.ADModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Brioal on 2016/5/28.
 */

public class TextViewAd extends TextView {
    private int mDuration; //切换的时长
    private int mIntervial; //切换的间隔
    private List<ADModel> mTexts; //数据
    private int mY = 0; //文字的Y坐标
    private int mIndex = 0;
    private Paint mPaint;
    private Paint mPaintIndex;
    private boolean isPlaying = true;

    public TextViewAd(Context context) {
        this(context, null);
    }

    public TextViewAd(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDuration = 200;
        mIntervial = 2000;
        mTexts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mTexts.add(new ADModel(i, "前缀" + i, "内容" + i, "链接" + i));
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);

        mPaintIndex = new Paint();
        mPaintIndex.setAntiAlias(true);
        mPaintIndex.setDither(true);
        mPaintIndex.setColor(Color.RED);
        mPaintIndex.setTextSize(30);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        ADModel model = mTexts.get(mIndex);
        String index = model.getmIndex();
        String content = model.getmContent();
        String url = model.getmUrl();
        Rect indexBound = new Rect();
        mPaintIndex.getTextBounds(index, 0, index.length(), indexBound);
        canvas.drawText(index, 0, index.length(), 10, mY, mPaintIndex);

        Rect contentBound = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), contentBound);
        canvas.drawText(content, 0, content.length(), (indexBound.right - indexBound.left) + 20, mY, mPaint);

        if (mY == 0) {
            mY = getMeasuredHeight() - (indexBound.top + indexBound.bottom);
            mIndex++;

        }
        if (mY == getMeasuredHeight() / 2 - (indexBound.top + indexBound.bottom) / 2) {
            isPlaying = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    postInvalidateDelayed(mDuration / getMeasuredHeight());
                    isPlaying = true;
                }
            }, mIntervial);
        }

        mY -= 1;
        if (mIndex == mTexts.size()) {
            mIndex = 0;
        }
        if (isPlaying) {
            postInvalidateDelayed(mDuration / getMeasuredHeight());
        }
    }
}
