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
	public static void SendGroupMessage(QQUser user, Udpsocket socket, MessageFactory factory)
	{
		Util.log("[机器人] [群消息 发送] 到群 " + factory.Group_uin + " [消息] " + factory.Message);
		if (factory.message_type == 0 || factory.message_type == 1)
		{
			byte[] data = SendPackageFactory.get0002(user, factory);
			socket.sendMessage(data);
		}
		else
		{
			if(factory.imagedata==null)
			{
				factory.imagedata=Util.Bufferedimg_tobytes(factory.image);
			}
			byte[] data = SendPackageFactory.get0388(user, factory);
			socket.sendMessage(data);

		}
	}

	public static void SendFriendMessage(QQUser user, Udpsocket socket, MessageFactory factory)
	{
		Util.log("[机器人] [好友消息 发送] 到 " + factory.Friend_uin + " [消息] " + factory.Message);
		if (factory.message_type == 0 || factory.message_type == 1)
		{
			byte[] data = SendPackageFactory.get00cd(user, factory);
			socket.sendMessage(data);
		}
	}

}
