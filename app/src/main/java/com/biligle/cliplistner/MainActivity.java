package com.biligle.cliplistner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.biligle.mylibrary.ClipManage;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ClipManage clipManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        clipManage = new ClipManage(this);
        clipManage.setListener(new ClipManage.ClipManageListener() {
            @Override
            public void onResult(String content) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clipManage.onDestroy();
    }
}
