#import <Cordova/CDV.h>

@interface ControlWebview: CDVPlugin
	@property (nonatomic, strong) IBOutlet CDVViewController* controlViewController;
  - (void)loadUrl:(CDVInvokedUrlCommand*)command;
  - (void)javascript:(CDVInvokedUrlCommand*)command;
@end
