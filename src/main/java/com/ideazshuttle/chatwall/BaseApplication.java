package com.ideazshuttle.chatwall;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;
import com.ideazshuttle.chatwall.receivers.ConnectivityReceiver;


/**
 * Created by mayank on 11/2/17.
 */

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectivityReceiver.init(this);
        EmojiManager.install(new GoogleEmojiProvider());

//        String admobAppId = getString(R.string.admob_app_id);\
//        if (!TextUtils.isEmpty(admobAppId))
//            MobileAds.initialize(this, admobAppId);
    }
}
