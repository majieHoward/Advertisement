package www.howard.com.advertisement.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by majie on 2017/4/23.
 */

public class OpenOtherApplication {

    private Context loadContext;

    public void initOpenOtherApplication(Context loadContext){
        this.loadContext=loadContext;
    }
    public void openApp(String applicationPackageName) throws Exception{
        PackageInfo packageInfo = loadContext.getPackageManager().getPackageInfo(applicationPackageName, 0);
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        List<ResolveInfo> applicationItems = loadContext.getPackageManager().queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveInfo = applicationItems.iterator().next();
        if (resolveInfo != null ) {
            String packageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(packageName, className);
            //startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag.
            // Is this really what you want? need to add intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            loadContext.startActivity(intent);
        }
    }
}
