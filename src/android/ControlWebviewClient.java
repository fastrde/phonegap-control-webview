package de.fastr.phonegap.plugins;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import android.webkit.WebView;

public class ControlWebviewClient extends CordovaWebViewClient{
	protected CordovaWebView controlView;		
	protected CordovaWebView appView;		

	public ControlWebviewClient(CordovaInterface cordova, CordovaWebView view, CordovaWebView controlView){
		super(cordova, view);
		this.appView = view;
		this.controlView = controlView;
	}
	public void onPageFinished(WebView view, String url)
  {
		super.onPageFinished(view,url);
		controlView.loadUrl("javascript:cordova.fireWindowEvent('pagechanged');");
  }
}
