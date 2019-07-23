# 0.QQRobot插件规范
## 0.1维持QQRobot正常运作
作为开源应用，我不想限制插件的使用。但是，希望各位插件开发者能够合理，合法地使用QQRobot。不要利用插件过于频繁地发送消息（尤其是xml消息）。被TX封的可能不仅仅是你的账号，还可能是整个QQRobot框架。不要因为自己的行为影响更多的人。
## 0.2不要作恶
不要试图利用某种方式获取并存储非插件功能必须的信息。更不要试图破坏宿主的设备。建议将发布在公共场合的插件进行开源，以免引发纠纷。
## 0.3禁止商业化
不得利用插件获取利益（包括卖插件/部分功能，插件内嵌广告，群发广告等）。产生一切后果与QQRobot无关
# 1.QQRobot插件开发快速入门
## 1.1.请求权限
QQRobot插件与Xposed模块类似，也是一种安卓应用。任何QQRobot插件应当请求**net.newlydev.qqrobot.permission.plugin**权限
 
`<uses-permission android:name="net.newlydev.qqrobot.permission.plugin"/>`
## 1.2安装SDK
将QQRobot源码目录中net.newlydev.qqrobot.PCTIM.sdk包下的三个文件复制到你的工程对应目录（/net/newlydev/qqrobot/PCTIM/sdk/)下即可，**不要改变包路径**
## 1.3实现Main类
应在你应用的包名下创建Main类，实现Plugin接口
`public class Main implements Plugin{}`

并实现几个回调方法（不解释，基本上看方法名就知道是啥方法）
## 1.4持久化API
onLoad方法会传入API，许多功能都需要它来实现
## 1.5编译运行
不解释
# 2.疑难处理
QQRobot不断在更新，如果你发现更新到某个版本时你的插件用不了了，请同步QQRobot源码重新安装SDK并更新你的代码。如果还有问题请发Issus