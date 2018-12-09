>文章链接：[https://mp.weixin.qq.com/s/1gkMtLu0BTXOUOj6isDjUw](https://mp.weixin.qq.com/s/1gkMtLu0BTXOUOj6isDjUw)

日常android开发过程中，会遇到编辑框输入内容弹出软键盘，往往会出现键盘遮挡内容，或者出现页面整体上移的，或多或少在体验上都不是很优雅，今天提供个方法是自行控制页面上移距离，竟可能让页面呈现给用户友好点。   

一般我们会在`AndroidManifest.xml` 里配置`windowSoftInputMode`来控制键盘与页面的交互。

举个栗子，一个简单的登录页面。    
##### adjustResize
activity 加`<activity android:windowSoftInputMode="adjustResize">`   
`adjustResize`：Activity总是调整屏幕的大小以便留出软键盘的空间，可以看到页面整体上移，最下面的一行字也可以看到。   
![](https://user-gold-cdn.xitu.io/2018/12/7/167867161d815adb?w=233&h=480&f=gif&s=1468038)   

##### adjustPan
activity 加`<activity android:windowSoftInputMode="adjustPan">`    
`adjustPan`：当前窗口的内容将自动移动以便当前焦点不被键盘覆盖，用户能总是看到输入内容的部分。   
可以发现页面会自动移动，以便获取焦点的editText 不被键盘遮住，**但是**确定按钮被遮住了，用户需要自行隐藏键盘 再确定。  
![](https://user-gold-cdn.xitu.io/2018/12/7/167867a2132f9a9e?w=216&h=416&f=gif&s=1291200)   

而我想要的效果是用户输入过程中 确定 按钮一直可见，且要底部的内容被遮挡，这就需要我们自己控制页面上移距离。   
监听键盘弹出/隐藏的过程，获取键盘高度，计算需要上移的高度，以便按钮可见。   
```
//监听键盘弹出/隐藏
container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        //getWindowVisibleDisplayFrame 获取当前窗口可视区域大小
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int screenHeight = getWindow().getDecorView().getHeight();
        //键盘弹出时，可视区域大小改变，屏幕高度 - 窗口可视区域高度 = 键盘弹出高度
        int softHeight = screenHeight - rect.bottom;
        /**
         * 上移的距离 = 键盘的高度 - 按钮距离屏幕底部的高度(如果手机高度很大，上移的距离会是负数，界面将不会上移)
         * 按钮距离屏幕底部的高度是用屏幕高度 - 按钮底部距离父布局顶部的高度
         * 注意这里 btn.getBottom() 是按钮底部距离父布局顶部的高度，这里也就是距离最外层布局顶部高度
         */
        int scrollDistance = softHeight - (screenHeight - btn.getBottom());
        if (scrollDistance > 0) {
            //具体移动距离可自行调整
            container.scrollTo(0, scrollDistance + 60);
        } else {
            //键盘隐藏，页面复位
            container.scrollTo(0, 0);
        }
    }
});
```
效果如下：   
![](https://user-gold-cdn.xitu.io/2018/12/7/167869ff35140b3d?w=224&h=445&f=gif&s=1477487)   
这样用户输入完成之后就可以直接点击确定按钮，体验上有所改善。    

当然，具体使用哪种方法得看页面需求。   


github地址：[https://github.com/taixiang/Input](https://github.com/taixiang/Input)

欢迎关注我的个人博客：[https://www.manjiexiang.cn/](https://www.manjiexiang.cn/)  

更多精彩欢迎关注微信号：春风十里不如认识你  
一起学习，一起进步，欢迎上车，有问题随时联系，一起解决！！！

![](https://user-gold-cdn.xitu.io/2018/8/12/1652cd77eaebeb98?w=900&h=540&f=jpeg&s=64949)    
