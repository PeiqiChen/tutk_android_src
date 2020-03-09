package com.camera.camerawithtutk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camera.api.AVAPIsClient;
import com.camera.model.User;
import com.decode.tools.BufferInfo;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class MainActivity extends AppCompatActivity {

    private String UID ="GV4GRAS1S2XJY3F1111A";
    public static BlockingDeque<BufferInfo> bq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bq = new LinkedBlockingDeque<>();// videobuffer信息存储到这里 解码器从此阻塞队列poll video的信息
        RecyclerView rc =findViewById(R.id.page_rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rc.setLayoutManager(linearLayoutManager);
        CameraAdapter adapter = new CameraAdapter(this);
        rc.setAdapter(adapter);
        ImageView add = findViewById(R.id.page_add);
        add.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
        });
        // 此线程获取videobuffer并offer进阻塞队列
        (new Thread() {
            public void run() {
                Intent intent = getIntent();
                String key = intent.getStringExtra("key");
                String name = intent.getStringExtra("name");
                String uid = intent.getStringExtra("uid");
                User user = User.getInstance("admin", "123456");
                AVAPIsClient.start(MainActivity.this.UID, user, bq);
            }
        }).start();

    }
}
