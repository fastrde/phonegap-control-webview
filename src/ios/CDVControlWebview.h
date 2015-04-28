#import <Cordova/CDV.h>

@interface ControlWebview: CDVPlugin<UIWebViewDelegate>
	@property (nonatomic, strong) IBOutlet CDVViewController* controlViewController;
	@property int webViewLoads;
  - (void)init:(CDVInvokedUrlCommand*)command;
  - (void)loadUrl:(CDVInvokedUrlCommand*)command;
  - (void)javascript:(CDVInvokedUrlCommand*)command;
@end
