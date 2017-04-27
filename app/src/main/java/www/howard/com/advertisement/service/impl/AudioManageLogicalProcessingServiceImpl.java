package www.howard.com.advertisement.service.impl;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import www.howard.com.advertisement.config.SystemParameterConfiguration;
import www.howard.com.advertisement.helper.FrameworkStringUtils;
import www.howard.com.advertisement.service.ILogicalProcessingService;

/**
 * Created by majie on 2017/4/24.
 */

public class AudioManageLogicalProcessingServiceImpl implements ILogicalProcessingService {
    //系统音量(0~7)
    //audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
    //audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    //通知(0~7)
    //audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
    //audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
    // 闹钟(0~7)
    //audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
    //audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
    @Override
    public void executionLogic(JSONObject paramDto, Context context, Handler handler) throws Exception {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //AudioManager.STREAM_MUSIC  音乐回放即媒体音量
        //AudioManager.STREAM_RING 铃声
        //AudioManager.STREAM_ALARM  警报
        //AudioManager.STREAM_NOTIFICATION 窗口顶部状态栏通知声
        //AudioManager.STREAM_SYSTEM  系统
        //AudioManager.STREAM_VOICE_CALL 通话
        //AudioManager.STREAM_DTMF 双音多频,不是很明白什么东西
        String audioKeyName = FrameworkStringUtils.asString(paramDto.get("audioKeyName"));
        String audioValue = FrameworkStringUtils.asString(paramDto.get("audioValue"));
        Message message = new Message();
        if (!"".equals(audioKeyName) && !"".equals(audioValue)) {
            try{
                int audioValueInt=Integer.parseInt(audioValue);
                SystemParameterConfiguration configuration = SystemParameterConfiguration.obtainSingleton();
                int audioAttributeKey = configuration.obtainAudioManagerAttribute(audioKeyName);
                String audioAttributeValue = FrameworkStringUtils.asString(configuration.obtainAudioManagerAttributeExplain(audioKeyName));
                if (!"".equals(audioAttributeKey) && !"".equals(audioAttributeValue)) {
                    audioManager.setStreamVolume(
                            audioAttributeKey,
                            audioValueInt,
                            AudioManager.FLAG_PLAY_SOUND);
                    message.obj = "调整android "+audioAttributeValue+"属性值为:"+audioValueInt;
                }else {
                    message.obj = "调整android AudioManager 参数错误,不存在"+audioAttributeValue;
                }
            }catch (Exception e){
                message.obj = "调整android AudioManager 参数错误,声音值需要为整数";
            }
        } else {
            message.obj = "调整android AudioManager 参数错误";
        }
        handler.sendMessage(message);
    }
}
