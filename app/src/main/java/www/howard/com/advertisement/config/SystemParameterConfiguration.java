package www.howard.com.advertisement.config;

import android.media.AudioManager;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by majie on 2017/4/25.
 */

public class SystemParameterConfiguration {
    private static volatile SystemParameterConfiguration singleton = null;
    private ConcurrentHashMap<String, Integer> topicMessageType = new ConcurrentHashMap<String, Integer>();
    private ConcurrentHashMap<Integer,String> topicMessageTypeControl=new ConcurrentHashMap<Integer, String>();
    private ConcurrentHashMap<String,Integer> audioManagerAttribute=new ConcurrentHashMap<String,Integer>();
    private ConcurrentHashMap<String,String> audioManagerAttributeExplain=new ConcurrentHashMap<String,String>();
    private SystemParameterConfiguration() {

    }

    public static SystemParameterConfiguration obtainSingleton() throws Exception {
        synchronized (SystemParameterConfiguration.class) {
            if (singleton == null) {
                singleton = new SystemParameterConfiguration();
                singleton.initMQTTTopicMessageType();
                singleton.initAudioManagerAttribute();
            }
        }
        return singleton;
    }

    private void initAudioManagerAttribute(){
        //AudioManager.STREAM_MUSIC  音乐回放即媒体音量
        //AudioManager.STREAM_RING 铃声
        //AudioManager.STREAM_ALARM  警报
        //AudioManager.STREAM_NOTIFICATION 窗口顶部状态栏通知声
        //AudioManager.STREAM_SYSTEM  系统
        //AudioManager.STREAM_VOICECALL 通话
        //AudioManager.STREAM_DTMF 双音多频,不是很明白什么东西
        audioManagerAttribute.put("STREAM_MUSIC", AudioManager.STREAM_MUSIC);
        audioManagerAttributeExplain.put("STREAM_MUSIC","音乐回放即媒体音量");
        audioManagerAttribute.put("STREAM_RING",AudioManager.STREAM_RING);
        audioManagerAttributeExplain.put("STREAM_RING","铃声");
        audioManagerAttribute.put("STREAM_ALARM",AudioManager.STREAM_ALARM);
        audioManagerAttributeExplain.put("STREAM_ALARM","警报");
        audioManagerAttribute.put("STREAM_NOTIFICATION",AudioManager.STREAM_NOTIFICATION);
        audioManagerAttributeExplain.put("STREAM_NOTIFICATION","窗口顶部状态栏通知声");
        audioManagerAttribute.put("STREAM_SYSTEM",AudioManager.STREAM_SYSTEM);
        audioManagerAttributeExplain.put("STREAM_SYSTEM","系统");
        audioManagerAttribute.put("STREAM_VOICECALL",AudioManager.STREAM_VOICE_CALL);
        audioManagerAttributeExplain.put("STREAM_VOICECALL","通话");
        audioManagerAttribute.put("STREAM_DTMF",AudioManager.STREAM_DTMF);
        audioManagerAttributeExplain.put("STREAM_DTMF","双音多频");
    }

    private void initMQTTTopicMessageType() {
        topicMessageType.put("successfulAcceptanceMessage", 1);
        topicMessageTypeControl.put(1,"successfulAcceptanceMessage");
        topicMessageType.put("successConnectionMessage", 2);
        topicMessageTypeControl.put(2,"successConnectionMessage");
        topicMessageType.put("failConnectionMessage", 3);
        topicMessageTypeControl.put(3,"failConnectionMessage");
    }

    public String obtainAudioManagerAttributeExplain(String attributeName){
        return audioManagerAttributeExplain.get(attributeName);
    }

    public Integer obtainMQTTTopicMessageType(String messageTypeName) {
        return topicMessageType.get(messageTypeName);
    }

    public Integer obtainAudioManagerAttribute(String attributeName){
        return audioManagerAttribute.get(attributeName);
    }
}
