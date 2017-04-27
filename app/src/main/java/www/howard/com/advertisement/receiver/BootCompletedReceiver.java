package www.howard.com.advertisement.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 *
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //广播：在系统启动后，这个动作被广播一次（只有一次）
        String boot_completed="android.intent.action.BOOT_COMPLETED";
        try{
            if(boot_completed.equals(intent.getAction())){
                /**
                 * 打开网络连接
                 */

                /**进行动态的配置设置启动时候调用的Activity**/
                //Intent advertisementActivityIntent =new Intent(context, AdvertisementActivity.class);
                Intent advertisementActivityIntent =new Intent(context, Class.forName("www.howard.com.advertisement.AdvertisementActivity"));
                //如果设置了此标志，这个activity将成为一个新task的历史堆栈中的第一个activity。
                // 这个task定义了一个原子组activities，用户可以对其进行移除。
                // 各种tasks可以移到前面或者后面；在一个特定的task中，所有的activities总是保持相同的顺序。
                advertisementActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(advertisementActivityIntent);
            }
        }catch (Exception e){
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
