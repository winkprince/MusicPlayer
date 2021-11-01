package com.example.buttonnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    private  EditText et1,et2;
    private Button button1;
    CheckBox checkBox;
    TextView textView6;
    ImageButton imageButton1, imageButton2,imageButton3;

    private String userName;
    private String passWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

       initData();

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/xingka.ttf");

        button1.setTypeface(typeface);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString();
                String pwd = et2.getText().toString();

                Log.i("INFO", "onClick: "+name);
                Log.i("INFO", "onClick: "+pwd);
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"还没有注册账号请前往注册！",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.equals(name,userName))
                {
                    if (TextUtils.equals(pwd,passWard)){
                        if (checkBox.isChecked()){
                            SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("name",name);
                            editor.putString("pwd",pwd);
                            editor.putBoolean("isRemember",true);
                            editor.commit();

                        }else {
                            SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("isRemember",false);
                            editor.commit();
                        }
                Intent in= new Intent(LoginActivity.this,Love.class);
                startActivity(in);
//                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
//                LoginActivity.this.finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(LoginActivity.this,"用户名不正确",Toast.LENGTH_SHORT).show();
                }






            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiometricPrompt.PromptInfo promptInfo =
                        new BiometricPrompt.PromptInfo.Builder()
                                .setTitle("指纹登录")
                                .setSubtitle("Login using your biometric credential")
                                .setNegativeButtonText("Cancel")
                                .build();

                getPrompt().authenticate(promptInfo);
            }
        });


    }

    private void initData() {

        SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
        Boolean isRemember= sp.getBoolean("isRemember",false);
        String name = sp.getString("name","");
        String pwd = sp.getString("pwd","");

        userName = name;
        passWard = pwd;

        if (isRemember){
            et1.setText(name);
            et2.setText(pwd);
            checkBox.setChecked(true);
        }
    }

    private void initView() {

        checkBox = findViewById(R.id.checkbox);
        button1 = findViewById(R.id.button1);
        textView6 = findViewById(R.id.textView6);
        et1 = findViewById(R.id.edit11);
        et2 = findViewById(R.id.editText8);
        imageButton1 = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
    }

    private BiometricPrompt getPrompt() {
        Executor exeCutor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Authentication Succeeded!");
                Intent intent = new Intent(LoginActivity.this, Home.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Authentication Failed!");
            }
        };

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, exeCutor, callback);
        return biometricPrompt;

    }

    private void notifyUser (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    public void gotoRegister(View view) {

        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 0 && data != null){
            Bundle extras = data.getExtras();
            String name = extras.getString("name", "");
            String pwd =extras.getString("pwd","");

            et1.setText(name);
            et2.setText(pwd);

            userName = name;
            passWard = pwd;
        }
    }
}