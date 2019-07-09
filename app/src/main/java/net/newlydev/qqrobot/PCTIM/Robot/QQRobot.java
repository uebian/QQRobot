package net.newlydev.qqrobot.PCTIM.Robot;
import java.net.*;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Socket.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;
import java.io.*;
import android.content.*;
import dalvik.system.*;
public class QQRobot
{
	private Udpsocket socket = null;
	private QQUser user = null;
	private RobotApi api;
	List<Plugin> plugins =new ArrayList<Plugin>();

	public QQRobot(Udpsocket _socket, QQUser _user,Context ctx)
	{
		this.socket = _socket;
		this.user = _user;
		this.api = new RobotApi(this.socket, this.user);
		ArrayList<String> plugin_list=new ArrayList<String>();
		try
		{
			DataInputStream fr=new DataInputStream(new FileInputStream(new File(ctx.getDataDir(),"pluginlist.cfg")));
			String s;
			while((s=fr.readLine())!=null)
			{
				String apkpath=ctx.getPackageManager().getApplicationInfo(s,0).sourceDir;
				plugin_list.add(apkpath);
				DexClassLoader dcl=new DexClassLoader(apkpath,ctx.getCacheDir().getPath(),null,ctx.getClassLoader());
				Class<?> pluginCls = dcl.loadClass(s+".Main");
				final Plugin plugin = (Plugin)pluginCls.newInstance();
				plugins.add(plugin);
				new Thread(){
					@Override public void run()
					{
						plugin.onLoad(api);
					}
				}.start();
				Util.log("[插件] 加载成功 [插件名]: " + plugin.name());
			}
			fr.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void call(final QQMessage qqmessage)
	{
		for (final Plugin plugin : this.plugins)
		{
			new Thread(){
				public void run()
				{
					plugin.onMessageHandler(qqmessage);
				}
			}.start();
		}
	}

}




