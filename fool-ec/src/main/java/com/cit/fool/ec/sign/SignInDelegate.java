package com.cit.fool.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cit.fool.core.delegates.BaseDelegate;
import com.cit.fool.core.delegates.FoolDelegate;
import com.cit.fool.core.net.rx.RxRestClient;
import com.cit.fool.ec.R;
import com.cit.fool.ec.R2;
import com.cit.fool.ec.database.entity.User;
import com.cit.fool.ec.main.EcBottomDelegate;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends FoolDelegate
{

    @BindView(R2.id.edit_sign_in_username)
    TextInputEditText mUsername;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword;

    private ISignListener mSignListener;

    @Override
    public Object setLayout()
    {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView)
    {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof ISignListener)
        {
            mSignListener = (ISignListener) activity;
        }
    }

    @OnClick({R2.id.btn_sign_in, R2.id.tv_link_sign_up, R2.id.tv_link_go})
    public void onClick(View v)
    {
        if (R.id.btn_sign_in == v.getId())
        {
            signIn();
        } else if (R.id.tv_link_sign_up == v.getId())
        {
            getSupportDelegate().start(new SignUpDelegate());
        } else if (R.id.tv_link_go == v.getId())
        {
            getSupportDelegate().startWithPop(new EcBottomDelegate());
        }
    }

    private void signIn()
    {
        if (checkForm())
        {
            RxRestClient.builder()
                    .url("/user")
                    .params("username", mUsername.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(s ->
                    {
                        List<User> users = JSON.parseArray(s, User.class);
                        if (users != null && !users.isEmpty())
                        {
                            SignHandler.onSignIn(users.get(0), mSignListener);
                        } else
                        {
                            Toast.makeText(getContext(), "s: " + s, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .error(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show())
                    .build()
                    .get();
        }
    }

    private boolean checkForm()
    {
        final String username = mUsername.getText().toString();
        if (username.isEmpty())
        {
            mUsername.setError("用户名不能为空");
            return false;
        } else
        {
            mUsername.setError(null);
        }

        final String password = mPassword.getText().toString();
        if (password.length() < 6)
        {
            mPassword.setError("密码6位数");
            return false;
        } else
        {
            mPassword.setError(null);
        }
        return true;
    }
}
