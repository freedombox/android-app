package org.freedombox.freedombox;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.HashMap;

public class WebAppInterface {
    Context context;
    HashMap<String,String> packageName;

    WebAppInterface(Context c) {
        context = c;
        packageName = new HashMap<>();
        packageName.put("sip","com.csipsimple");
        packageName.put("vlc","org.videolan.vlc");
    }

    @JavascriptInterface
    public void launchApp(String app) {
        PackageManager manager = context.getPackageManager();

        try {
            Intent launchIntent = manager.getLaunchIntentForPackage(packageName.get(app));
            if (launchIntent == null) {
                throw new PackageManager.NameNotFoundException();
            }
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(launchIntent);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("ERROR:",app +" is not installed");
        }
    }
}