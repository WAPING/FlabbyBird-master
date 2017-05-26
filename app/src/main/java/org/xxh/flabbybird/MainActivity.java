package org.xxh.flabbybird;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GameFlabbyBird mGame;

    public final static String ACTION_EXIT_APP = "cn.edu.zafu.leakcanary.exit";
    private static LocalBroadcastManager mLocalBroadcatManager;
    private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_EXIT_APP)) {
                Log.d("TAG", "exit from broadcast");
                finish();
            }
        }
    };
    private void init() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_EXIT_APP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        getLocalBroadcastManager().registerReceiver(mExitReceiver, filter);
    }

    private  LocalBroadcastManager getLocalBroadcastManager() {
        if (mLocalBroadcatManager == null) {
            mLocalBroadcatManager = LocalBroadcastManager.getInstance(this);
        }
        return mLocalBroadcatManager;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mGame = new GameFlabbyBird(this);
        setContentView(mGame);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getRefWatcher().watch(this);
    }
}
