#import "CDVControlWebview.h"
#import "MainViewController.h"
#import "AppDelegate.h"

@implementation ControlWebview

- (void)init:(CDVInvokedUrlCommand*)command
{
	CDVPluginResult* pluginResult = nil;
  pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
	AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
#if __has_feature(objc_arc)
	self.controlViewController = [[MainViewController alloc] init];
#else
  self.controlViewController = [[[MainViewController alloc] init] autorelease];
#endif
  //self.controlViewController.startPage = startPage;
	//appDelegate.viewController.startPage = @"control.html";
	//appDelegate.viewController.view.hidden = YES;
  self.controlViewController.startPage = @"control.html";
  self.controlViewController.view.hidden = YES;
  [appDelegate.window.rootViewController.view addSubview:self.controlViewController.view];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)loadUrl:(CDVInvokedUrlCommand*)command
{
	AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
	NSString* url = [command.arguments objectAtIndex:0];
	NSLog(@"URL:%@", url);
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
	CDVPluginResult* pluginResult = nil;
  pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
@end
