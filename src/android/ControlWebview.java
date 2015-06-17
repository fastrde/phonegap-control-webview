package de.fastr.phonegap.plugins;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class ControlWebview extends CordovaPlugin {
    private WebView webview;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView cwv) {
        super.initialize(cordova, cwv);
        final CordovaWebView finalCwv = cwv;

        final String UserAgent = cordova.getActivity().getIntent().getStringExtra("control-useragent");
        final String injectfile = cordova.getActivity().getIntent().getStringExtra("control-injectfile");
        final String startUrl = cordova.getActivity().getIntent().getStringExtra("control-starturl");
        final Activity activity = cordova.getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webview = new WebView(activity);
                webview.setWebViewClient(new WebViewClient() {});
                webview.setWebChromeClient(new WebChromeClient() {});
                webview.getSettings().setJavaScriptEnabled(true);

                String origUserAgent = webview.getSettings().getUserAgentString();
                webview.getSettings().setUserAgentString(origUserAgent + "-" + UserAgent);
                webview.setId(200);
                webview.setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                activity.setContentView(webview);
                webview.loadUrl(startUrl);
                injectJavascriptFile("www/js/"+injectfile);


                View view = finalCwv.getView();
                view.setVisibility(View.GONE);               // Code for WebView goes here
            }
        });

    }

    public void injectJavascriptFile(String scriptFile){
        Log.d("cvapp", "injecting "+scriptFile+".js");
        Context context=this.cordova.getActivity().getApplicationContext();
        AssetManager assetManager = context.getAssets();
        InputStream input;
        try {
            input = context.getAssets().open(scriptFile+".js");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();

            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "if (document.getElementById('file_"+scriptFile+"') == null) {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.id = 'file_"+scriptFile+"';" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script);" +
                    "}" +
                    "})()");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        boolean success = false;
        if (action.equals("init")) {
        } else if (action.equals("loadUrl")) {
            String url = args.getString(0);
            success = this.loadUrlInMainView(url);
        } else if (action.equals("javascript")) {
            String script = args.getString(0);
            success = this.javascript(script);
        }
        if (success) {
            callbackContext.success();
        } else {
            callbackContext.error(0);
        }
        return success;
    }

    private boolean loadUrlInMainView(final String url){
        Activity activity = this.cordova.getActivity();
		activity.runOnUiThread(new Runnable() {
    	@Override
    	public void run() {
				webview.loadUrl(url);
    	}
		});
		return true;
  }
	private boolean javascript(String script){
        Log.d("cvapp", "Executing javascript:" + script);
		this.loadUrlInMainView("javascript:"+script);
		return true;
  }
}
