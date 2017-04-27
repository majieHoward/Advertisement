package www.howard.com.advertisement.service.impl;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONObject;

import www.howard.com.advertisement.helper.SystemParameterUtils;
import www.howard.com.advertisement.service.ILogicalProcessingService;

/**
 * Created by majie on 2017/4/24.
 */

public class ShutDownLogicalProcessingServiceImpl implements ILogicalProcessingService {
    @Override
    public void executionLogic(JSONObject paramDto, Context context, Handler handler) throws Exception {
        /**
         * 判断是否有root权限
         */
        if(SystemParameterUtils.isRoot()){
            Toast.makeText(context, "即将关闭设备", Toast.LENGTH_SHORT).show();
            SystemParameterUtils.shutdownDevice();
        }
    }
}
