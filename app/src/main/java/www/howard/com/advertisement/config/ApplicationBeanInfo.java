package www.howard.com.advertisement.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majie on 2017/4/24.
 */

public class ApplicationBeanInfo {

    private static volatile ApplicationBeanInfo singleton = null;

    private ApplicationBeanInfo() {
    }

    public static ApplicationBeanInfo obtainSingleton() {
        synchronized (ApplicationBeanInfo.class) {
            if (singleton == null) {
                singleton = new ApplicationBeanInfo();
                singleton.configureBeanInfo();
            }
        }
        return singleton;
    }

    private List<ApplicationBeanEntity> beanInfoItems = new ArrayList<ApplicationBeanEntity>();

    public List<ApplicationBeanEntity> obtainBeanInfoItems(){
        return this.beanInfoItems;
    }
    public void configureBeanInfo() {
        if (beanInfoItems == null) {
            beanInfoItems = new ArrayList<ApplicationBeanEntity>();
        }
        try {
            /**
             public String beanName;
             public String beanClassPath;
             public String isSingleton;
             **/
            beanInfoItems.add(new ApplicationBeanEntity("controlMessageEvent","www.howard.com.advertisement.service.impl.ControlMessageEventImpl","true"));
            beanInfoItems.add(new ApplicationBeanEntity("openOtherILogicalProcessing","www.howard.com.advertisement.service.impl.OpenOtherILogicalProcessingServiceImpl","true"));
            beanInfoItems.add(new ApplicationBeanEntity("audioManagerProcessing", "www.howard.com.advertisement.service.impl.AudioManageLogicalProcessingServiceImpl", "true"));
            beanInfoItems.add(new ApplicationBeanEntity("silentInstallProcessing", "www.howard.com.advertisement.service.impl.SilentInstallLogicalProcessingServiceImpl", "true"));
            beanInfoItems.add(new ApplicationBeanEntity("shutDownLogicalProcessing", "www.howard.com.advertisement.service.impl.ShutDownLogicalProcessingServiceImpl", "true"));
        } catch (Exception e) {

        }
    }


}
