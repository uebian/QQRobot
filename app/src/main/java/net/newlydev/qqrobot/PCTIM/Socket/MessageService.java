package net.newlydev.qqrobot.PCTIM.Socket;
import android.graphics.*;
import java.io.*;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Message.*;
import net.newlydev.qqrobot.PCTIM.Package.*;
import net.newlydev.qqrobot.PCTIM.Robot.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;

public class MessageService
{
	private Thread thread = null;
	private QQUser user = null;
	private Udpsocket socket = null;
	private long timemills = 0;
	private QQRobot robot;

	public MessageService(QQUser _user, Udpsocket _socket, QQRobot _robot)
	{
		this.user = _user;
		this.socket = _socket;
		this.robot = _robot;
		this.thread = new Thread(){
			@Override
			public void run()
			{
				while (true)
				{
					final byte[] data = socket.receiveMessage();
				    if (data != null)
					{
						user.lastMessage = new Date().getTime();
						manage(data);
				    }
				}
			}
		};
	}
	public void manage(byte[] data)
	{

		final ParseRecivePackage parsereceive = new ParseRecivePackage(data, user.TXProtocol.SessionKey, user);
		//Util.log("[接收包] 命令: "+Util.byte2HexString(parsereceive.Command));
		if (Util.GetInt(parsereceive.Command) == 23)
		{
			QQMessage qqmessage = parsereceive.parse0017();
			if (qqmessage != null)
			{
				byte[] data_to_send = SendPackageFactory.get0017(this.user, parsereceive.Message_To_Respone, parsereceive.Sequence);
				this.socket.sendMessage(data_to_send);
				if  (qqmessage != null)
				{

					if (qqmessage.Sender_Uin != 0 && qqmessage.Sender_Uin != user.QQ)
					{
						if (user.logintime > qqmessage.Send_Message_Time)
						{

							Util.log("[群消息(作废)] 来自群:" + qqmessage.Group_uin + " 的成员: " + qqmessage.SendName + " [消息] " + qqmessage.Message);
						}
						else
						{
							Util.log("[群消息] 来自群:" + qqmessage.Group_uin + " 的成员: " + qqmessage.SendName + " [消息] " + qqmessage.Message);
							this.robot.call(qqmessage);
						}
					}
				}
			}
		}
		else if (Util.GetInt(parsereceive.Command) == 88)
		{
			parsereceive.decrypt_body();
			if (parsereceive.body_decrypted[0] != 0)
			{
				user.offline = true;
				user.islogined = false;
			}
		}
		else if (Util.GetInt(parsereceive.Command) == 904)
		{
			PictureStore store = null;
			final PictureKeyStore keystore = parsereceive.parse0388();
			if (keystore.uploaded == false)
			{
				new Thread(){
					public void run()
					{
						PictureStore  new_store = Util.uploadimg(keystore, user, Util.GetInt(parsereceive.Sequence));
						try
						{
							byte[] data_to_send = SendPackageFactory.sendpic(user,new_store.Group,new_store.data);
							socket.sendMessage(data_to_send);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}.start();
			}
			else
			{


				for (PictureStore onestore: user.imgs)
				{
					if (onestore.pictureid == Util.GetInt(parsereceive.Sequence))
					{
						store = onestore;
						user.imgs.remove(onestore);
						break;
					}

				}
				try
				{
					byte[] data_to_send = SendPackageFactory.sendpic(this.user, store.Group,store.data);
					this.socket.sendMessage(data_to_send);
				}
				catch (IOException e)
				{}
			}

		}
		else if (Util.GetInt(parsereceive.Command) == 206)
		{
			QQMessage qqmessage = parsereceive.parse00ce();
			byte[] data_to_send = SendPackageFactory.get00ce(this.user, parsereceive.Message_To_Respone, parsereceive.Sequence);
			this.socket.sendMessage(data_to_send);
			data_to_send = SendPackageFactory.get0319(this.user, parsereceive.Friend_Message_QQ, parsereceive.Friend_Message_TIME);
			this.socket.sendMessage(data_to_send);
			if  (qqmessage != null)
			{

				if (qqmessage.Sender_Uin != 0 && qqmessage.Sender_Uin != user.QQ)
				{
					if (user.logintime > qqmessage.Send_Message_Time)
					{

						Util.log("[好友消息(作废)] 来自好友: " + qqmessage.Sender_Uin + " [消息] " + qqmessage.Message);
					}
					else
					{
						Util.log("[好友消息] 来自好友: " + qqmessage.Sender_Uin + " [消息] " + qqmessage.Message);
						this.robot.call(qqmessage);
					}
				}
			}
		}
	}
	public void updateRobot(QQRobot robot)
	{
		this.robot=robot;
	}
	public void start()
	{
		this.thread.start();

	}
}
