package www.howard.com.advertisement.service.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import www.howard.com.advertisement.helper.FrameworkStringUtils;
import www.howard.com.advertisement.helper.OpenOtherApplication;
import www.howard.com.advertisement.service.ILogicalProcessingService;

/**
 * Created by majie on 2017/4/26.
 */

public class OpenOtherILogicalProcessingServiceImpl implements ILogicalProcessingService {
    @Override
    public void executionLogic(JSONObject paramDto, Context context, Handler handler) throws Exception {
        String applicationPackageName = FrameworkStringUtils.asString(paramDto.get("applicationPackageName"));
        if (!"".equals(applicationPackageName)) {
            OpenOtherApplication openOtherApplication = new OpenOtherApplication();
            openOtherApplication.initOpenOtherApplication(context);
            openOtherApplication.openApp(applicationPackageName);
            Message message=new Message();
            message.obj="打开"+applicationPackageName+"应用成功";
            handler.sendMessage(message);
        }
    }
}
