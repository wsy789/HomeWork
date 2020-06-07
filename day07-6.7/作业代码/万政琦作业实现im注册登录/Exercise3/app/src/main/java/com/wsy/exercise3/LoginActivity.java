package com.wsy.exercise3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wsy.exercise3.util.ToastUtil;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jiguang.analytics.android.api.LoginEvent;
import cn.jiguang.analytics.android.api.RegisterEvent;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class LoginActivity extends AppCompatActivity  {

    private TextView mTooltvLogin;
    private Toolbar mToolLogin;
    private EditText mUsernameLogin;
    private EditText mPasswordLogin;
    private Button mBtRegisterLogin;
    private Button mBtLogin;
    private TextView mAttentionLogin;
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mTooltvLogin = (TextView) findViewById(R.id.login_tooltv);
        mToolLogin = (Toolbar) findViewById(R.id.login_tool);
        mUsernameLogin = (EditText) findViewById(R.id.login_username);
        mPasswordLogin = (EditText) findViewById(R.id.login_password);
        mBtRegisterLogin = (Button) findViewById(R.id.login_bt_register);
//        mBtRegisterLogin.setOnClickListener(this);
        mBtLogin = (Button) findViewById(R.id.login_bt);
//        mBtLogin.setOnClickListener(this);
        mAttentionLogin = (TextView) findViewById(R.id.login_attention);

        sharedPrefHelper = SharedPrefHelper.getInstance();
        if (!sharedPrefHelper.getUserId().equals("")) {
            mUsernameLogin.setText(sharedPrefHelper.getUserId());
        }
    }

/*    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_register:
                // TODO 20/06/07
                initRegister();
                break;
            case R.id.login_bt:
                // TODO 20/06/07
                break;
            default:
                break;
        }
    }*/

    private void initRegister(View view) {
        //注册

        JMessageClient.register(mUsernameLogin.getText().toString(), mPasswordLogin.getText().toString(), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.e("s=======1:", i + "，" + s);
                switch (i) {
                    case 0:
//                        Toast.makeText(BaseApp.sContext,msg, Toast.LENGTH_SHORT).show();
                        ToastUtil.showToastLong( "注册成功");
                        initLogin(mUsernameLogin.getText().toString(),mPasswordLogin.getText().toString(),1);
                        RegisterEvent event = new RegisterEvent("userName", true);
                        JAnalyticsInterface.onEvent(LoginActivity.this,event);
                        break;
                    case 898001:
                        ToastUtil.showToastLong( "用户名已存在");
                        break;
                    case 871301:
                        ToastUtil.showToastLong( "密码格式错误");
                        break;
                    case 871304:
                        ToastUtil.showToastLong( "密码错误");
                        break;
                    default:
                        ToastUtil.showToastLong( s);
                        break;
                }
            }
        });
    }
    private void initLogin(String userName, String passWord, final int type){
//        showProgressDialog("正在登陆...");
        JMessageClient.login(userName, passWord, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
//                dismissProgressDialog();
                switch (i) {
                    case 801003:

                        ToastUtil.showToastLong( "用户名不存在");
                        break;
                    case 871301:

                        ToastUtil.showToastLong( "密码格式错误");
                        break;
                    case 801004:

                        ToastUtil.showToastLong( "密码错误");
//                        handler.sendEmptyMessage(-1);
                        break;
                    case 0:

                        ToastUtil.showToastLong( "登陆成功");
                        sharedPrefHelper.setUserId(mUsernameLogin.getText().toString());
                        sharedPrefHelper.setUserPW(mPasswordLogin.getText().toString());
                        initUserInfo(mUsernameLogin.getText().toString(),type);
                        //登录成功计数
                        LoginEvent event = new LoginEvent("userName", true);
                        JAnalyticsInterface.onEvent(LoginActivity.this,event);
                        break;
                    default:

                        break;
                }

            }
        });
    }
    //初始化个人资料
    public void initUserInfo(String id, final int type){
//        showProgressDialog("正在初始化数据");
        JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
//                dismissProgressDialog();
                if (i==0) {
//                    Log.e("info-Login", ""+JMessageClient.getMyInfo()+"\n"+JMessageClient.getConversationList()+"\n"+userInfo);
                    Intent intent = new Intent(LoginActivity.this
                            , MainActivity.class);
                    intent.putExtra("LOGINTYPE", type);
                    startActivity(intent);
                }
            }
        });
    }
}
