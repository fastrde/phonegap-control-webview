#import "CDVControlWebview.h"
#import "MainViewController.h"
#import "AppDelegate.h"

@implementation ControlWebview

- (void)init:(CDVInvokedUrlCommand*)command
{
	self.webViewLoads = 0;
	CDVPluginResult* pluginResult = nil;
  pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
	AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
#if __has_feature(objc_arc)
	self.controlViewController = [[MainViewController alloc] init];
#else
  self.controlViewController = [[[MainViewController alloc] init] autorelease];
#endif
  self.controlViewController.startPage = @"control.html";
  self.controlViewController.view.hidden = YES;
	appDelegate.viewController.webView.delegate = self;
  [appDelegate.window.rootViewController.view addSubview:self.controlViewController.view];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)webViewDidStartLoad:(UIWebView *)webView {
  self.webViewLoads++;
}


- (void)webViewDidFinishLoad:(UIWebView *)webView {
  self.webViewLoads--;
  if (self.webViewLoads > 0) {
    return;
  }
	[self.controlViewController.webView stringByEvaluatingJavaScriptFromString:@"javascript:cordova.fireWindowEvent('pagechanged');"];
}

- (void)webView:(UIWebView*)webView didFailLoadWithError:(NSError*)error {
  self.webViewLoads--;
}

- (void)loadUrl:(CDVInvokedUrlCommand*)command
{
	AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
	NSString* url = [command.arguments objectAtIndex:0];
	NSURL *urlOverwrite = [NSURL URLWithString:url];
	NSURLRequest *request = [NSURLRequest requestWithURL:urlOverwrite];
	[appDelegate.viewController.webView stopLoading];
	[appDelegate.viewController.webView loadRequest:request];

	CDVPluginResult* pluginResult = nil;
  pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)javascript:(CDVInvokedUrlCommand*)command
{
	NSString* script = [command.arguments objectAtIndex:0];
	[self.controlViewController.webView stringByEvaluatingJavaScriptFromString:script];

	CDVPluginResult* pluginResult = nil;
  pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
@end
