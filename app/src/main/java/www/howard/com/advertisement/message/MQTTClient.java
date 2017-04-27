package www.howard.com.advertisement.message;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import www.howard.com.advertisement.config.ApplicationBeanConfig;
import www.howard.com.advertisement.helper.EncoderHandler;
import www.howard.com.advertisement.helper.FrameworkStringUtils;
import www.howard.com.advertisement.service.impl.ControlMessageEventImpl;

/**
 * Created by majie on 2017/4/18.
 */

public class MQTTClient {
    /**
     * MQTT这是一个客户端服务端架构的发布/订阅模式的消息传输协议；
     * 轻巧、开放、简单、规范，易于实现
     */
    private Context showMakeText;
    private String host = "tcp://192.168.1.4:61613";
    private String userName = "admin";
    private String passWord = "password";
    private Handler topicMessagehandler;
    private MqttClient client;
    private String myTopic = "test/topic";
    private MqttConnectOptions options;
    private AudioManager mAudioManager;
    private ScheduledExecutorService scheduler;

    public void initMQTTClient(Context showMakeText, AudioManager mAudioManager) {
        this.mAudioManager = mAudioManager;
        this.showMakeText = showMakeText;
    }

    public void monitorMQTTServer() {
        init();
        topicMessagehandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    if (!"".equals(FrameworkStringUtils.asString(message.obj))) {
                        EncoderHandler.encode("SHA1", FrameworkStringUtils.asString(message.obj));
                        try {
                            ControlMessageEventImpl controlMessageEventImpl=(ControlMessageEventImpl) ApplicationBeanConfig.obtainSingleton().obtainApplicationBean("controlMessageEvent");
                            controlMessageEventImpl.handlingEventsMessage(showMakeText,FrameworkStringUtils.asString(message.obj));
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                } else if (message.what == 2) {
                    Toast.makeText(showMakeText, "连接成功", Toast.LENGTH_SHORT).show();
                    try {
                        /**
                         * At Most Once (QoS=0)
                         At Least Once (QoS=1)
                         Exactly Once (QoS=2)
                         最多一次
                         这个设置时推送消息给Client，可靠性最低的一种。
                         如果设置Qos=0，那broker就不会返回结果码，告诉你他收到消息了，
                         也不会在失败后尝试重发。这有点像不可靠消息，如JMS。
                         至少一次
                         该设置会确保消息会被至少一次推送到Client。
                         如果推送设置为至少推送一次，Apollo会返回一个回调函数，确保代理已经收到消息，
                         而且确保会确保推送该消息。如果Client 将发布了一个Qos=1的消息，
                         如果在指定的时间内没有收到回复，Client会希望重新发布这个消息。
                         所以可能存在这种情况：代理收到一个需要推送的消息，
                         然后又收到一个消息推送到同一个Client。所以如果传输过程中PUBACK丢失，
                         Client会重新发送，而且不会去检测是否是重发，broker就将消息发送到订阅主题中。
                         恰好一次
                         该设置是可靠等级最高的。他会确保发布者不仅仅会推送，
                         而且不会像Qos=1 那样，会被接收两次。当然这个设置会增加网络的负载。
                         当一个消息被发布出去的时候，broker会保存该消息的id,而且会利用任何长连接，
                         坚持要把该消息推送给目标地址。如果Client收到PUBREC 标志，
                         那就表明broker已经收到消息了。 这个时候broker会期待Client发送一个PUBREL
                         来清除session 中消息id，broker如果发送成功就会发送一个PUBCOMP通知Client。
                         */
                        client.subscribe(myTopic, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (message.what == 3) {
                    Toast.makeText(showMakeText, "连接失败，系统正在重连", Toast.LENGTH_SHORT).show();
                }
            }
        };
        //startReconnect();
    }

    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void init() {
        try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，
            // 一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，
            // 默认为以内存保存
            client = new MqttClient(host, "testCl",
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);

            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒
            // 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，
            // 但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);

            //设置回调
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    //connect();
                    //连接丢失后，一般在这里面进行重连
                    Toast.makeText(showMakeText, "连接丢失",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里

                }
                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里
                    Message successfulAcceptanceMessage = new Message();
                    successfulAcceptanceMessage.what = 1;
                    successfulAcceptanceMessage.obj = message.toString();
                    topicMessagehandler.sendMessage(successfulAcceptanceMessage);
                }
            });
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(options);
                    Message successConnectionMessage  = new Message();
                    successConnectionMessage.what = 2;
                    topicMessagehandler.sendMessage(successConnectionMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message failConnectionMessage = new Message();
                    failConnectionMessage.what = 3;
                    topicMessagehandler.sendMessage(failConnectionMessage);
                }
            }
        }).start();
    }

}
