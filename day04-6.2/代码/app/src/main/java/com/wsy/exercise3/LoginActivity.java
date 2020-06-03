package com.wsy.exercise3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wsy.exercise3.base.BaseApp;
import com.wsy.exercise3.db.LoginBeanDao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView mTooltvLogin;
    private Toolbar mToolLogin;
    private EditText mUsernameLogin;
    private EditText mPasswordLogin;
    private Button mBtRegisterLogin;
    private Button mBtLogin;
    private TextView mAttentionLogin;
    private String userName;
    private String passWord;
    private LoginBeanDao beanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //得到对象
        beanDao = BaseApp.getInstance().getDaoSession().getLoginBeanDao();

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

        initLestener();
    }

    private void initLestener() {

    }

/*    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_register:
                // TODO 20/06/02
                initregister();
                break;
            case R.id.login_bt:
                // TODO 20/06/02
                initLogin();
                break;
            default:
                break;
        }
    }*/

    public void initLogin(View view) {
        userName = mUsernameLogin.getText().toString();
        passWord = mPasswordLogin.getText().toString();
        if (!TextUtils.isEmpty(userName) && isMobileNO(userName)) {
            if (!TextUtils.isEmpty(passWord)) {
                //验证密码是否是4位，且都是数字
                Pattern compile = Pattern.compile("\\d{4}");
                boolean matches = compile.matcher(passWord).matches();
                if (matches) {
                    List<LoginBean> list = beanDao.queryBuilder().where(LoginBeanDao.Properties.Password.eq(userName),
                            LoginBeanDao.Properties.Password.eq(userName)).list();
                    if (list != null) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录错误", Toast.LENGTH_SHORT).show();

                    }
                }
            } else {
                Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(LoginActivity.this, "用户名输入错误", Toast.LENGTH_SHORT).show();

        }
    }

    public void initRegister(View view) {
        userName = mUsernameLogin.getText().toString();
        passWord = mPasswordLogin.getText().toString();
        if (!TextUtils.isEmpty(userName) && isMobileNO(userName)) {
            if (!TextUtils.isEmpty(passWord)) {
                //验证密码是否是4位，且都是数字
                Pattern compile = Pattern.compile("\\d{4}");
                boolean matches = compile.matcher(passWord).matches();
                if (matches) {
                    beanDao.insertOrReplace(new LoginBean());
                    Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(LoginActivity.this, "用户名输入错误", Toast.LENGTH_SHORT).show();

        }
    }

    //验证用户名号码
    private boolean isMobileNO(String userName) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[a-zA-Z][0-9a-zA-Z_]{4,17}");
            Matcher m = p.matcher(userName);
            flag = m.matches();
        } catch (Exception e) {
            Log.e("TAG", "用户名不符合要求" + e.getMessage());
            flag = false;
        }
        return flag;
    }

}
