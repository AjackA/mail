package com.example.administrator.myapp6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Menu extends AppCompatActivity {
    static Socket socket;
    static OutputStream outputStream;
    static BufferedReader reader;
    Button button;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        setContentView(R.layout.activity_menu);
        button=(Button)findViewById(R.id.writemail);
        Intent intent=this.getIntent();
        username=intent.getStringExtra("username");
        password=intent.getStringExtra("password");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(Menu.this,write_mail.class);
                intent3.putExtra("username",username);
                intent3.putExtra("password",password);
                startActivity(intent3);
            }
        });
    }
}