package de.fastr.phonegap.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.LocationManager;
import android.content.Context;
/*
 * thx to http://stackoverflow.com/questions/843675/how-do-i-find-out-if-the-gps-of-an-android-device-is-enabled
 */
public class ControlWebview extends CordovaPlugin{
	protected CordovaWebView controlView;
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		boolean success = false;
		if (action.equals("init")){
			success = this.init();
		}else if (action.equals("loadUrl")){
			String url = args.getString(0);
			success = this.loadUrl(url);
		}else if (action.equals("javascript")){
			String script = args.getString(0);
			success = this.javascript(script);
		}
		if (success){
			callbackContext.success();
		}else{
			callbackContext.error(0);
		}
		return success;
	}

	private boolean init(){
		controlView.loadUrl("file:///android_asset/www/control.html");
		return true;
  }
	private boolean loadUrlinMainView(String url){
		Context context = this.cordova.getActivity().getApplicationContext();
		Activity activity = (Activity) context;
		activity.loadUrl(url);
  }
	private boolean javascript(){
  }
}
