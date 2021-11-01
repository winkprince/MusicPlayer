package com.example.buttonnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText editText1,editText2,editText3;
    private TextView textView1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textView1 = findViewById(R.id.textView1);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        button2 = findViewById(R.id.button2);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/xingka.ttf");
        button2.setTypeface(typeface);
        textView1.setTypeface(typeface);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name = editText1.getText().toString();
                String   pwd = editText2.getText().toString();
                String  confirmPwd = editText3.getText().toString();


                if (TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(pwd,confirmPwd)){
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                //注册成功之后把信息存储在info.xml中
                SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name",name);
                editor.putString("pwd",pwd);
                //数据回传
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("pwd",pwd);
                intent.putExtras(bundle);
                setResult(0,intent);

                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                RegisterActivity.this.finish();



            }
        });
    }
}