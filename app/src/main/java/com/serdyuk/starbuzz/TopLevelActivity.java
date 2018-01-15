package com.serdyuk.starbuzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
    }

    public void tapOnLogo(View view) {
        System.out.println("tap on logo");
    }
}
