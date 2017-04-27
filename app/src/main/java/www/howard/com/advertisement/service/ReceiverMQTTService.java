package www.howard.com.advertisement.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

import www.howard.com.advertisement.message.MQTTClient;


public class ReceiverMQTTService extends Service {


    public ReceiverMQTTService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 系统会调用这个函数当某个组件(例如 activity，fragment)通过调用 startService()
     * 启动 Service 时。在该方法被调用后，Service 就会被启动并独立的在后台运行。
     * 如果重写了该方法，开发者需要在 Service 执行完操作后自行的调用
     * stopSelf() 或 stopService()，来结束 Service。
     * 如果只是会通过绑定的方式 (bind) 的方式来启动 Service 则不需要重写该方法。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        MQTTClient mqttClient=new MQTTClient();
        mqttClient.initMQTTClient(this,mAudioManager);
        mqttClient.monitorMQTTServer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
