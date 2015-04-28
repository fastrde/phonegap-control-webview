exports.loadUrl = function(url, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		console.log("LOADING:" + url);
		cordova.exec(success, error, "controlWebview", "loadUrl", [url]);
};

exports.javascript = function(script, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "controlWebview", "javascript", [script]);
};

exports.init= function(success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "controlWebview", "init",[]);
};
