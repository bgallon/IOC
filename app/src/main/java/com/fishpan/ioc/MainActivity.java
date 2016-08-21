package com.fishpan.ioc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fishpan.ioc.annotation.BindView;
import com.fishpan.ioc.api.IOC;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_txt)
    TextView mTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IOC.inject(this);
        mTxt.setText("我是被注入的控件哦");
    }
}
