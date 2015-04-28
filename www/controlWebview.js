exports.loadUrl = function(url, success, error){
		console.log("LOADING:" + url);
		cordova.exec(success, error, "controlWebview", "loadUrl", [url]);
};

exports.javascript = function(success, error, script){
		cordova.exec(success, error, "controlWebview", "javascript", script);
};

exports.init= function(success, error){
		cordova.exec(success, error, "controlWebview", "init");
};
