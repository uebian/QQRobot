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
	public void GroupMemberShutUp(long gc, long uin, int time) 
	{
		try
		{
			String urls="http://qinfo.clt.qq.com/cgi-bin/qun_info/set_group_shutup";
			URL lll = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) lll.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Cookie", user.quncookie);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			String body="";
			if (uin == 0)
			{
				if (time == 0)
				{
					body = "gc=" + gc + "&all_shutup=0&bkn=" + user.bkn + "&src=qinfo_v2";
				}
				else
				{
					body = "gc=" + gc + "&all_shutup=1&bkn=" + user.bkn + "&src=qinfo_v2";
				}
			}
			else
			{
				JSONArray data=new JSONArray();
				data.put(new JSONObject().put("uin", uin).put("t", time));
				body = "gc=" + gc + "&shutup_list=" + data.toString() + "&bkn=" + user.bkn + "&src=qinfo_v2";
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			writer.write(body);
			writer.close();
			connection.getResponseCode();
			connection.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



}
