package net.newlydev.qqrobot.PCTIM.Socket;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Robot.*;

public class MessageService
{
	private Thread thread = null;
	private QQUser user = null;
	private MessageManager messagemanager= null;
	private Udpsocket socket = null;
	private long timemills = 0;

	private QQRobot robot;
	
	public MessageService(QQUser _user,Udpsocket _socket,QQRobot _robot){
		this.user = _user;
		this.socket = _socket;
		this.robot = _robot;
		this.messagemanager = new MessageManager(this.user,this.socket,this.robot);
		this.thread = new Thread(){
			public void run(){
				while(true){
					   byte[] data = socket.receiveMessage();
				    if (data != null){
						user.lastMessage = new Date().getTime();
				        messagemanager.manage(data);
				    }
				}
			}
		};
	}
	
	
	public void start(){
		this.thread.start();
		
	}
	
	public void stop(){
		this.thread.stop();

	}
}
