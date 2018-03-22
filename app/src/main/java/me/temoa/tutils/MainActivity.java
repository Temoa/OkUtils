package me.temoa.tutils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Map;

import me.temoa.baseutils.KeyboardUtils;
import me.temoa.baseutils.encrypt.RSA;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if (view.getTag() == null || (boolean) view.getTag()) {
            KeyboardUtils.showSoftInput(view);
            view.setTag(false);
        } else {
            KeyboardUtils.hideSoftInput(view);
            view.setTag(true);
        }
    }
}
