package www.howard.com.advertisement.helper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by majie on 2017/4/24.
 */

public class SystemParameterUtils {
    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                /**
                 *  当前没有获取root权限
                 */
                root = false;
            } else {
                /**
                 *  当前获取到了root权限
                 */
                root = true;
            }

        } catch (Exception e) {

        }
        return root;
    }

    public static void shutdownDevice() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream out = new DataOutputStream(
                    process.getOutputStream());
            out.writeBytes("reboot -p\n");
            out.writeBytes("exit\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
