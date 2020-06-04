package com.wsy.android_js;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.wsy.android_js.widget.PhotoPopupWindow;

import java.io.File;
import java.io.FileOutputStream;

public class JsToAndroid {
    private MainActivity mContext;
    private ImageView mMineTpHead;
    private PhotoPopupWindow mPhotoPopupWindow;
    //拍照或选择图片并切割
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final String IMAGE_FILE_NAME = "icon.jpg";

    public JsToAndroid(MainActivity context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void hllo(String msg) {
        Log.e("TAG---", msg);
    /*    Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivityForResult(intent,0);*/
//        initChangeHead();
    }
    /*private void initChangeHead() {
        mPhotoPopupWindow = new PhotoPopupWindow(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入相册选择
                initPress2();
            }

        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拍照
                initPress();
            }
        });
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.activity_head__mine, null);
        mPhotoPopupWindow.showAtLocation(rootView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    private void initPress2() {
        // 文件权限申请
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 权限还没有授予，进行申请
            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200); // 申请的 requestCode 为 200
        } else {
            // 如果权限已经申请过，直接进行图片选择
            mPhotoPopupWindow.dismiss();
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // 判断系统中是否有处理该 Intent 的 Activity
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_GET);
            } else {
                Toast.makeText(mContext, "未找到图片查看器", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initPress() {
        // 拍照及文件权限申请
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 权限还没有授予，进行申请
            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300); // 申请的 requestCode 为 300
        } else {
            // 权限已经申请，直接拍照
            mPhotoPopupWindow.dismiss();
            imageCapture();
        }
    }
    *
     * 处理权限回调结果

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        Toast.makeText(Head_MineActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
        }
    }
    *
     * 处理回调结果

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        Log.e("TAG", "小图切割:"+data);
                        setPicToView(data);//小图模式中，保存图片后，设置到视图中
                    }
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        startSmallPhotoZoom(data.getData());//小图模式切割图片
                        Log.e("TAG", "相册: "+data);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    Log.e("TAG", "拍照: "+temp);
                    startSmallPhotoZoom(Uri.fromFile(temp));
                    break;
            }
        }
    }

    *//**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     *//*
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
*//*        // 开始切割---------->用于大图切割
//        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(FileProvider.getUriForFile(this,
                "com.chen.lister.testchangeicon.fileProvider", file), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
// ......
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);*//*

    }

    *//**
     * 小图模式中，保存图片后，设置到视图中
     *//*
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data"); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            mMineTpHead.setImageBitmap(photo);
        }
    }
    *//**
     * 判断系统及拍照
     *//*
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);

        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pictureUri = FileProvider.getUriForFile(this,
                    "com.chen.lister.testchangeicon.fileProvider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        Log.e("TAG", "判断系统及拍照: Uri="+pictureUri+";File="+pictureFile);
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }*/
}
