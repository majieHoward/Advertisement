package www.howard.com.advertisement;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import de.greenrobot.event.EventBus;
import www.howard.com.advertisement.config.ApplicationBeanConfig;
import www.howard.com.advertisement.config.SystemParameterConfiguration;
import www.howard.com.advertisement.helper.NetWorkUtils;
import www.howard.com.advertisement.helper.WifiConnector;
import www.howard.com.advertisement.helper.WifiConnector.WifiCipherType;
import www.howard.com.advertisement.service.ReceiverMQTTService;
import www.howard.com.advertisement.webview.UserDefinedWebviewClient;


public class AdvertisementActivity extends AppCompatActivity  {
    private AudioManager mAudioManager;
    private WifiManager wifiManager;
    private WifiConnector wac;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 初始化实例化所有的对象
         */
        try{
            SystemParameterConfiguration.obtainSingleton();
            ApplicationBeanConfig.obtainSingleton();
        }catch (Exception e){

        }
        setContentView(R.layout.activity_advertisement);
        /**
         * 判断当前是否已经连接到了网络
         */
        if(NetWorkUtils.isNetworkConnected(getApplicationContext())==false){
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wac = new WifiConnector(wifiManager);
            wac.mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    // 操作界面
                    if("success".equals(msg.obj)){
                        while (NetWorkUtils.isNetworkConnected(getApplicationContext())==false) {
                            try {
                                // 为了避免程序一直while循环，让它睡个100毫秒检测……
                                Thread.sleep(100);
                            } catch (InterruptedException e) {

                            }
                        }
                        loadAdvertisementPage();
                    }
                }
            };
            try {
                wac.connect("howard-wifi", "howard18328421693",
                        "howard18328421693".equals("")?WifiCipherType.WIFICIPHER_NOPASS:WifiCipherType.WIFICIPHER_WPA);
            } catch (Exception e) {

            }
        }else{
            loadAdvertisementPage();
        }
    }

    /**
     * 在webview中展示html页面
     */
    private void loadAdvertisementPage(){
        Intent startIntent = new Intent(this, ReceiverMQTTService.class);
        startService(startIntent);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView .getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.supportMultipleWindows();  //多窗口
        //如果内容已经存在cache 则使用cache，即使是过去的历史记录。如果cache中不存在，从网络中获取！
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        /**
         * LOAD_DEFAULT: 如果我们应用程序没有设置任何cachemode， 这个是默认的cache方式。 加载一张网页会检查是否有cache，如果有并且没有过期则使用本地cache，否则                                   从网络上获取。
         LOAD_CACHE_ELSE_NETWORK: 使用cache资源，即使过期了也使用，如果没有cache才从网络上获取。
         LOAD_NO_CACHE: 不使用cache 全部从网络上获取
         LOAD_CACHE_ONLY:  只使用cache上的内容。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        UserDefinedWebviewClient userDefinedWebviewClient=new UserDefinedWebviewClient();
        userDefinedWebviewClient.initUserDefinedWebviewClient(this.getApplicationContext());
        webView.setWebViewClient(userDefinedWebviewClient);//设置用WebView打开内部链接
        //webView.loadUrl("http://192.168.1.4:8080/advertisement/index.html");
        webView.loadUrl("http://192.168.1.4:8080/advertisement/swipeSlide/full-screen-pic.html");
        // When the alarm goes off, we want to broadcast an Intent to our
        // BroadcastReceiver. Here we make an Intent with an explicit class
        // name to have our own receiver (which has been published in
        // AndroidManifest.xml) instantiated and called, and then create an
        // IntentSender to have the intent executed as a broadcast.
        //Intent intent = new Intent(this, TimingTaskReceiver.class);
        ///PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        // We want the alarm to go off 10 seconds from now.
        //Calendar calendar = Calendar.getInstance();
       // calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.add(Calendar.SECOND, 10);
        // Schedule the alarm!
       // AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5*1000), pendingIntent);
        //
       // alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

