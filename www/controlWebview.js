cordova.define("de.fastr.phonegap.plugins.ControlWebview.controlWebview", function(require, exports, module) { cordova.define("de.fastr.phonegap.plugins.ControlWebview.controlWebview", function(require, exports, module) { var cordova = require('cordova'),
    exec = require('cordova/exec');

var controlWebview = {
	loadUrl: function(url, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		console.log("LOADING:" + url);
		cordova.exec(success, error, "controlWebview", "loadUrl", [url]);
	},
	javascript: function(script, success, error){
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "controlWebview", "javascript", [script]);
	},
	init: function(success, error){
		document.addEventListener("onload", controlWebview.onload, false);
		success = success || function (){ };
		error   = error   || function (){ };
		cordova.exec(success, error, "controlWebview", "init",[]);
	},
	onload: function(){
		console.log("LOADED EXECUTED!!");
	}
};

module.exports = controlWebview;

});

});
