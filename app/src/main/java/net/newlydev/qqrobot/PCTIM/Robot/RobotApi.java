package net.newlydev.qqrobot.PCTIM.Robot;
import java.io.*;
import java.net.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Message.*;
import net.newlydev.qqrobot.PCTIM.Socket.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;
import org.json.*;

public class RobotApi implements API
{



	private Udpsocket socket = null;
	private QQUser user = null;

	public RobotApi(Udpsocket _socket, QQUser _user)
	{
		this.user = _user;
		this.socket = _socket;

	}
	@Override
	public void SendGroupMessage(MessageFactory factory)
	{

		SendMessage.SendGroupMessage(this.user, this.socket, factory);

	}
	@Override
	public void SendFriendMessage(MessageFactory factory)
	{

		SendMessage.SendFriendMessage(this.user, this.socket, factory);

	}

	@Override
	public void GroupMemberShutUp(long guin, long uin, int time) 
	{
		try
		{
			String urls="http://qinfo.clt.qq.com/cgi-bin/qun_info/set_group_shutup";
			URL lll = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) lll.openConnection();// 打开连接  
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Cookie", user.quncookie);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			String body="";
			if (uin == 0)
			{
				body = "gc=" + guin + "&all_shutup=1&bkn=" + user.bkn + "&src=qinfo_v2";
			}
			else
			{
				JSONArray data=new JSONArray();
				data.put(new JSONObject().put("uin",uin).put("t",time));
				body = "gc=" + guin + "&shutup_list=" + data.toString()+"&bkn=" + user.bkn + "&src=qinfo_v2";
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			writer.write(body);
			writer.close();
			int rc=connection.getResponseCode();
			DataInputStream dis=new DataInputStream(connection.getInputStream());
			String line,result="";
			while ((line = dis.readLine()) != null)
			{
				result += line;
			}
			connection.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// TODO: Implement this method
	}



}
