package com.cit.fool.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cit.fool.core.delegates.BaseDelegate;
import com.cit.fool.core.delegates.FoolDelegate;
import com.cit.fool.core.net.rx.RxRestClient;
import com.cit.fool.ec.R;
import com.cit.fool.ec.R2;
import com.cit.fool.ec.database.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpDelegate extends FoolDelegate
{

    @BindView(R2.id.edit_sign_up_username)
    TextInputEditText mUsername;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword;
    @BindView(R2.id.edit_sign_up_password_confirm)
    TextInputEditText mPasswordConfirm;

    private ISignListener mSignListener;

    @Override
    public Object setLayout()
    {
        return R.layout.delegate_sign_up;
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

    @OnClick({R2.id.btn_sign_up, R2.id.tv_link_sign_in})
    public void onClick(View v)
    {
        if (R.id.btn_sign_up == v.getId())
        {
            signUp();
        } else if (R.id.tv_link_sign_in == v.getId())
        {
            getSupportDelegate().start(new SignInDelegate());
        }
    }


    private void signUp()
    {
        if (checkForm())
        {
            RxRestClient.builder()
                    .url("/user")
                    .params("username", mUsername.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .params("passwordConfirm", mPasswordConfirm.getText().toString())
                    .success(s ->
                    {
                        List<User> users = JSON.parseArray(s, User.class);
                        if (users != null && !users.isEmpty())
                        {
                            SignHandler.onSignUp(users.get(0), mSignListener);
                        } else
                        {
                            Toast.makeText(getContext(), "s: " + s, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .error(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show())
                    .build()
                    .post();
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

        final String passwordConfirm = mPasswordConfirm.getText().toString();
        if (!passwordConfirm.equals(password))
        {
            mPasswordConfirm.setError("密码不一致");
            return false;
        } else
        {
            mPasswordConfirm.setError(null);
        }
        return true;
    }

}
