package com.example.shuiai.defineprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefineProgress progress = (DefineProgress) findViewById(R.id.progress);
        progress.setUpdateAngle(90);
    }
}
