package net.newlydev.qqrobot.PCTIM.Robot;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Message.*;
import net.newlydev.qqrobot.PCTIM.Socket.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;

public class RobotApi implements API{

private Udpsocket socket = null;
	private QQUser user = null;
	 
 public RobotApi(Udpsocket _socket,QQUser _user){
   this.user = _user;
   this.socket = _socket;
 
 }
@Override
	public void SendGroupMessage(MessageFactory factory){

		SendMessage.SendGroupMessage(this.user,this.socket,factory);

	}
	@Override
	public void SendFriendMessage(MessageFactory factory){

		SendMessage.SendFriendMessage(this.user,this.socket,factory);

	}




}
