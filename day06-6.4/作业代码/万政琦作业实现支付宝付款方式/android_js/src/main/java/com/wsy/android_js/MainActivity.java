package com.wsy.android_js;

import android.Manifest;
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
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.wsy.android_js.widget.PhotoPopupWindow;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String APP_CACAHE_DIRNAME = "/webimg";
    private Button mBt;
    private WebView mWebview;
    private ProgressBar mPb;
    private String mWebviewUrl;
    //-----------------------------------

    private PhotoPopupWindow mPhotoPopupWindow;
    //拍照或选择图片并切割
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final String IMAGE_FILE_NAME = "icon.jpg";
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBt = (Button) findViewById(R.id.bt);
        mWebview = (WebView) findViewById(R.id.webview);
        mPb = (ProgressBar) findViewById(R.id.pb);
        mImg = (ImageView) findViewById(R.id.img);
    }

    public void initWeb(View view) {

        //网页的网址
        //-------以下是JS(BUtton和图片都是再网页里面的)调用Android-----------
        mWebview.loadUrl("file:///android_asset/demo2.html");

        //1.要映射的那个类对象  2.类对象引用
        mWebview.addJavascriptInterface(new JsToAndroid(view), "test");
        //-------以上是JS(BUtton和图片都是再网页里面的)调用Android-----------
        //在自己的页面里面打开网页，防止跳到外网
        mWebview.setWebViewClient(new WebViewClient());
        //想让网页适配webview
        WebSettings settings = mWebview.getSettings();
        //支持js代码
        settings.setJavaScriptEnabled(true);
        //允许js弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //设置网页支持缩放
        //缩放操作
        //支持缩放，默认为true。是下面那行的前提。
        settings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //（☝true的时候，会有“+”/“-”用于缩放，false：则隐藏掉）
        //网页无图模式
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片

        //设置编码格式 为gbk  当网页出现乱码 就可以设置网页编码格式
        settings.setDefaultTextEncodingName("GBK");//设置编码格式

        //自己测
        //webview的缓存   如果有网 根据cache-ctral 是否从网络上或者缓存中加载  如果没网就从缓存中获取网页(离线加载)
        if (NetWorkUtl.isConnected(getApplicationContext())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        settings.setDomStorageEnabled(true); // 开启 DOM storage API 功能——SD卡
        settings.setDatabaseEnabled(true);   //开启 database storage API 功能——数据库
        settings.setAppCacheEnabled(true);//开启 Application Caches 功能——缓存存储
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        settings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录


        //在自己的页面里面打开网页
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("TAG", "网页开始加载了....");
                mPb.setVisibility(View.VISIBLE);
                mPb.setProgress(0);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("TAG", "网页加载完成了");
                mPb.setVisibility(View.GONE);
                mPb.setProgress(100);
                super.onPageFinished(view, url);
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mPb.setVisibility(ProgressBar.INVISIBLE);
                } else {
                    Log.e("MyTag", "加载进度" + newProgress);
                }
                mPb.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    //监听返回键——方法一
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK){ //点击返回键
            Log.e("TAG", "onKeyDown: ","点击返回键操作" );
            finish();
           return true;
        }
        return super.onKeyDown(keyCode, event);//这行不能删
    }*/
    //监听返回键——方法二
    @Override
    public void onBackPressed() {
        //mWebview.canGoBack()  //还能返回
        if (mWebview != null && mWebview.canGoBack()) {
//            mWebview.goBack();//返回上一步操作
            //虽切换tab，但还是属于首页面
            mWebviewUrl = mWebview.getUrl();
            //如果当前网页是这些tab(导航，体系, 项目分类，工具)的话  再点击返回键就退出程序
            if (mWebviewUrl.equals("https://www.wanandroid.com/index") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/user_article") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/navi") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/wenda") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/projectindex") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/wxarticle/list/408/1") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/project") ||
                    mWebviewUrl.equals("https://www.wanandroid.com/tools"))
                super.onBackPressed();
            else {
                //当前网页不是这些，有可能是其他的网页  比如：二级网页
                mWebview.goBack();
            }
        } else {
            super.onBackPressed();//退出程序
        }

    }


    class JsToAndroid {


        private View view;

        public JsToAndroid(View view) {

            this.view = view;
        }

        @JavascriptInterface
        public void hllo(String msg) {
            Log.e("TAG---", msg);
    /*    Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivityForResult(intent,0);*/
            initChangeHead();
        }

        private void initChangeHead() {
            mPhotoPopupWindow = new PhotoPopupWindow(MainActivity.this, new View.OnClickListener() {
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
            //View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
            mPhotoPopupWindow.showAtLocation(view,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        }

        private void initPress2() {
            // 文件权限申请
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 权限还没有授予，进行申请
                ActivityCompat.requestPermissions(MainActivity.this,
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
                    Toast.makeText(MainActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void initPress() {
            // 拍照及文件权限申请
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 权限还没有授予，进行申请
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300); // 申请的 requestCode 为 300
            } else {
                // 权限已经申请，直接拍照
                mPhotoPopupWindow.dismiss();
                imageCapture();
            }
        }


    }

    /**
     * 处理权限回调结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

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
                        Toast.makeText(MainActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
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
/*
处理回调结果
 */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        Log.e("TAG", "小图切割:" + data);
                        setPicToView(data);//小图模式中，保存图片后，设置到视图中
                    }
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        startSmallPhotoZoom(data.getData());//小图模式切割图片
                        Log.e("TAG", "相册: " + data);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    Log.e("TAG", "拍照: " + temp);
                    startSmallPhotoZoom(Uri.fromFile(temp));
                    break;
            }
        }
    }

    /**
     * 小图模式中，保存图片后，设置到视图中
     */
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
            mImg.setImageBitmap(photo);

        }
    }

    /**
     * 判断系统及拍照
     */
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);

        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pictureUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.chen.lister.testchangeicon.fileProvider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        Log.e("TAG", "判断系统及拍照: Uri=" + pictureUri + ";File=" + pictureFile);
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
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
/*        // 开始切割---------->用于大图切割
//        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(FileProvider.getUriForFile(this,
                "com.chen.lister.testchangeicon.fileProvider", file), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
// ......
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);*/

    }
}
