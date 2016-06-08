package com.brioal.guijutianyuan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

import zxing.QRCodeDecoder;
import zxing.core.QRCodeView;


public class QRScanActivity extends Activity implements QRCodeView.Delegate {
    private QRCodeView qrCodeView;
    private boolean isFlashOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullScreen();//设置全屏
        setContentView(cn.bingoogolapple.qrcode.zxing.R.layout.activity_qrscan);
        initView();
    }

    private void initView() {
        qrCodeView = (QRCodeView) findViewById(cn.bingoogolapple.qrcode.zxing.R.id.main_zxingview);
        qrCodeView.setResultHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        qrCodeView.startCamera();//打开相机
        qrCodeView.startSpot(); //开始识别
    }

    @Override
    protected void onStop() {
        qrCodeView.stopCamera();
        super.onStop();
    }

    //震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        setResult(result);
    }

    public void setResult(String string) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", string);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

    private void initFullScreen() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //打开闪光灯
    public void light(View view) {
        if (isFlashOpen) {
            qrCodeView.closeFlashlight();
            isFlashOpen = false;
        } else {
            qrCodeView.openFlashlight();
            isFlashOpen = true;
        }
    }

    //打开图库扫描
    public void scanPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            QRCodeDecoder.decodeQRCode(bitmap, new QRCodeDecoder.Delegate() {
                @Override
                public void onDecodeQRCodeSuccess(String result) {
                    setResult(result);
                }

                @Override
                public void onDecodeQRCodeFailure() {
                    Toast.makeText(QRScanActivity.this, "图片解码失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
