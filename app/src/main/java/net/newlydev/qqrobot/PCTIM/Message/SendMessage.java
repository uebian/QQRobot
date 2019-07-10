package net.newlydev.qqrobot.PCTIM.Message;
import java.io.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Package.*;
import net.newlydev.qqrobot.PCTIM.Socket.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;
import android.graphics.*;

public class SendMessage
{
	public static void SendGroupMessage(QQUser user, Udpsocket socket, long gin, String msg, boolean isxml)
	{
		Util.log("[机器人] [群消息 发送] 到群 " + gin + " [消息] " + msg);
		byte[] data = SendPackageFactory.get0002(user, gin, msg, isxml);
		socket.sendMessage(data);
	}
	public static void SendGroupMessage(QQUser user, Udpsocket socket, long gin, Bitmap bitmap)
	{
		Util.log("[机器人] [群消息 发送] 到群 " + gin + " [图片消息]");
		byte[] data = SendPackageFactory.get0388(user, gin, bitmap);
		socket.sendMessage(data);

	}
	public static void SendFriendMessage(QQUser user, Udpsocket socket, long uin, String msg, boolean isxml)
	{
		Util.log("[机器人] [好友消息 发送] 到 " + uin + " [消息] " + msg);
		byte[] data = SendPackageFactory.get00cd(user, uin,msg,isxml);
		socket.sendMessage(data);
	}

}
