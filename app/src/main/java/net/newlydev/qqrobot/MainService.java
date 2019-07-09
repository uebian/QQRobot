package net.newlydev.qqrobot;
import android.app.*;
import android.content.*;
import android.os.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Robot.*;
import net.newlydev.qqrobot.PCTIM.Socket.*;

public class MainService extends Service
{
	LoginManager manager ;
	HeartBeat heartbeat;
	MessageService messageservice;
	QQUser user;
	Object lock=new Object();
	Messenger currenthandler;
	final Messenger mMessenger = new Messenger(new Handler(){
			@Override
			public void handleMessage(Message msg)
			{
				currenthandler = msg.replyTo;
				switch (msg.getData().getString("type"))
				{
					case "login":
						{
							user = new QQUser(msg.getData().getLong("qq", -1), msg.getData().getByteArray("pwd"));
							new Thread(){
								@Override
								public void run()
								{
									manager = new LoginManager(user,lock);
									if(manager.login(currenthandler))
									{
										Message msg=new Message();
										Bundle data=new Bundle();
										heartbeat=new HeartBeat(user,manager.socket);
										QQRobot robot=new QQRobot(manager.socket,user,getApplicationContext());
										messageservice=new MessageService(user,manager.socket,robot);
										heartbeat.start();
										messageservice.start();
										data.putString("type","ok");
										data.putString("name",user.NickName);
										msg.setData(data);
										try
										{
											currenthandler.send(msg);
										}
										catch (RemoteException e)
										{}
									}else{
										Message msg=new Message();
										Bundle data=new Bundle();
										data.putString("type","error");
										data.putString("info","Unknown error");
										msg.setData(data);
										try
										{
											currenthandler.send(msg);
										}
										catch (RemoteException e)
										{}
									}
								}
							}.start();
						}
						break;
					case "islogined":
						if (user != null && user.islogined)
						{
							Message msga=new Message();
							Bundle data=new Bundle();
							data.putString("type", "islogined");
							msga.setData(data);
							try
							{
								currenthandler.send(msga);
							}
							catch (RemoteException e)
							{}

						}
						break;
					case "verifyCode":
						manager.setVerifyCode(msg.getData().getString("code"));
						synchronized(manager){
							manager.notify();
						}
						break;
					case "updateRobot":
						QQRobot robot=new QQRobot(manager.socket,user,getApplicationContext());
						messageservice.updateRobot(robot);
						break;
				}
			}
		});

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{

        Notification.Builder builder = new Notification.Builder(this);
		builder.setContentText("QQRobot is running");
		builder.setContentTitle("QQRobot");
		builder.setSmallIcon(R.drawable.ic_launcher);
		Notification notification=builder.build();
		notification.flags=Notification.FLAG_ONGOING_EVENT;
		startForeground(1,notification);
		return super.onStartCommand(intent, flags, startId);
	}
		

	@Override
	public IBinder onBind(Intent p1)
	{
		return mMessenger.getBinder();
	}

}
