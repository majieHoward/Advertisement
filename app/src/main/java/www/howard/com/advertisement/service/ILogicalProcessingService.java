package www.howard.com.advertisement.service;

import android.content.Context;
import android.os.Handler;

import org.json.JSONObject;

/**
 * Created by majie on 2017/4/24.
 */

public interface ILogicalProcessingService {
    public void executionLogic(JSONObject paramDto, Context context, Handler handler)throws Exception;
}
