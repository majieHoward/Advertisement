package www.howard.com.advertisement.webview;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by majie on 2017/4/22.
 */

public class UserDefinedWebviewClient extends WebViewClient {
    private Context showMakeText;
    public void initUserDefinedWebviewClient(Context showMakeText){
        this.showMakeText=showMakeText;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
