package www.howard.com.advertisement.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by majie on 2017/4/19.
 */

public class SilentInstall {

    public void downloadApk(final Context context, final String fileUrl, final String apkName, final Handler handle) {

        new Thread() {
            public void run() {
                Message message = new Message();
                String filePath = Environment.getExternalStorageDirectory().getPath() + "/" +apkName;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    File file = new File(filePath);
                    file.createNewFile();// 新建文件
                    is = conn.getInputStream();
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int numread = is.read(buffer);
                        if (numread <= 0) {
                            break;
                        }
                        fos.write(buffer, 0, numread);
                    }
                    fos.flush();
                } catch (MalformedURLException e) {
                    message.obj="从"+fileUrl+"地址下载安装包失败！"+e.getMessage();
                    handle.sendMessage(message);
                } catch (IOException e) {
                    message.obj="从"+fileUrl+"地址下载安装包失败！"+e.getMessage();
                    handle.sendMessage(message);
                } finally {
                    try {
                        is.close();
                        fos.close();
                        message.obj="从"+fileUrl+"地址下载安装包成功！下载到本地"+filePath;
                        handle.sendMessage(message);
                        installApk(context,filePath,handle);
                    } catch (IOException e) {
                        message.obj="从"+fileUrl+"地址下载安装包失败！"+e.getMessage();
                        handle.sendMessage(message);
                    }
                }
            }
        }.start();
    }

    public void installApk(final Context context,final String filePath,final Handler handle) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + filePath),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
        /*
        new Thread() {
            public void run() {
                Message message = new Message();
                Process process = null;
                OutputStream out = null;
                try {
                    message.obj="从本地"+filePath+"地址开始安装APK"+" 执行pm install -r " + filePath;
                    handle.sendMessage(message);
                    // 请求root
                    //process = Runtime.getRuntime().exec("su");
                    process =Runtime.getRuntime().exec("su -c pm install -r " + filePath);
                    out = process.getOutputStream();
                    //out.write(("chmod 777 " + filePath + "\n").getBytes());
                    // 调用安装
                    //out.write(("pm install -r " + filePath + "\n").getBytes());
                    message = new Message();
                    message.obj=out.toString();
                    handle.sendMessage(message);
                } catch (IOException e) {
                    message = new Message();
                    message.obj="从"+filePath+"安装APK失败！"+e.getMessage();
                    handle.sendMessage(message);
                } catch (Exception e) {
                    message = new Message();
                    message.obj="从"+filePath+"安装APK失败！"+e.getMessage();
                    handle.sendMessage(message);
                } finally {
                    try {
                        out.flush();
                        out.close();
                        message = new Message();
                        message.obj="从"+filePath+"安装APK成功！";
                        handle.sendMessage(message);
                    } catch (IOException e) {
                        message = new Message();
                        message.obj="从"+filePath+"安装APK失败！"+e.getMessage();
                        handle.sendMessage(message);
                    }
                }
            }
        }.start();*/
    }


}
