package www.howard.com.advertisement.service.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import org.json.JSONObject;
import www.howard.com.advertisement.config.ApplicationBeanConfig;
import www.howard.com.advertisement.helper.FrameworkStringUtils;
import www.howard.com.advertisement.service.ILogicalProcessingService;

/**
 * Created by majie on 2017/4/24.
 */

public class ControlMessageEventImpl {
    /**
     * 处理事件完成后调用
     *
     * @return
     * @throws Exception
     */
    private Handler initEventsHander(final Context context) throws Exception{
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void handlingEventsMessage(Context context, String eventMessage) throws Exception {
        if (!"".equals(FrameworkStringUtils.asString(eventMessage))) {
            try {
                JSONObject messageJSONObject = new JSONObject(FrameworkStringUtils.asString(eventMessage));
                String messageType = FrameworkStringUtils.asString(messageJSONObject.get("messageType"));
                if (!"".equals(messageType)) {
                    ILogicalProcessingService logicalProcessingService = (ILogicalProcessingService) ApplicationBeanConfig.obtainSingleton().obtainApplicationBean(messageType);
                    logicalProcessingService.executionLogic(messageJSONObject, context,initEventsHander(context));
                }
            } catch (Exception e) {

            }
        }
    }
}
