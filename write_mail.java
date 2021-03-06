package com.example.administrator.myapp6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class write_mail extends AppCompatActivity {
    private EditText mailreciver;
    private EditText mailsender;
    private EditText maildata;
    private EditText Mailsubject;
    private Button mailsendout;
    private String username,password;
    static Socket socket;
    static OutputStream outputStream;
    static BufferedReader reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mail);
        mailreciver=(EditText)findViewById(R.id.reciver);
        mailsender=(EditText)findViewById(R.id.sender);
        Mailsubject=(EditText)findViewById(R.id.subject);
        maildata=(EditText)findViewById(R.id.mail);
        mailsendout=(Button)findViewById(R.id.send_mail);
        Intent intent=this.getIntent();
        username=intent.getStringExtra("username");
        password=intent.getStringExtra("password");
        class mail_Thread implements Runnable{
            @Override
            public void run() {
                socket=new Socket();
                try {
                    socket.connect(new InetSocketAddress("smtp.163.com",25),3000);
                    outputStream=socket.getOutputStream();
                    reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write("helo 163.com\r\n".getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write("auth login\r\n".getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write((username+"\r\n").getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write((password+"\r\n").getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write(("mail from:<"+mailsender.getText().toString().trim()+"@163.com>\r\n").getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write(("rcpt to:<"+mailreciver.getText().toString().trim()+"@163.com>\r\n").getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write("data\r\n".getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write(("from:"+mailsender.getText().toString()+"@163.com\r\n").getBytes("utf-8"));
                    outputStream.write(("to:"+mailreciver.getText().toString()+"@163.com\r\n").getBytes("utf-8"));
                    outputStream.write(("subject:"+Mailsubject.getText().toString()+"\r\n").getBytes("utf-8"));
                    outputStream.write("\r\n".getBytes("utf-8"));
                    outputStream.write((maildata.getText().toString()+"\r\n").getBytes("utf-8"));
                    outputStream.write(".\r\n".getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.write("quit\r\n".getBytes("utf-8"));
                    line=reader.readLine();
                    Log.i("logcat",line);
                    outputStream.close();
                    reader.close();
                    socket.close();
                    Intent intent4 = new Intent(write_mail.this, Menu.class);//A.class->B.class
                    startActivity(intent4);//启动activity
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mailsendout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mailreciver.getText().toString()==null){
                    Toast toast=Toast.makeText(getApplicationContext(),"收件人不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(mailsender.getText().toString()==null){
                    Toast toast=Toast.makeText(getApplicationContext(),"发件人不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Mailsubject.getText().toString()==null){
                    Toast toast=Toast.makeText(getApplicationContext(),"主题不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }else
                {

                    mail_Thread m=new mail_Thread();
                    Thread t=new Thread(m);
                    t.start();

                }
            }
        });
    }

}
