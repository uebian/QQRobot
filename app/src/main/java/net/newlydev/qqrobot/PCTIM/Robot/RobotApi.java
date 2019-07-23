package net.newlydev.qqrobot.PCTIM.Robot;
import java.io.*;
import java.net.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Message.*;
import net.newlydev.qqrobot.PCTIM.Socket.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;
import org.json.*;
import android.graphics.*;
import android.content.Context;
import net.newlydev.qqrobot.mApplication;

public class RobotApi implements API
{

	private Udpsocket socket = null;
	private QQUser user = null;
	
	@Override
	public Context getContext()
	{
		// TODO: Implement this method
		return mApplication.getContext();
	}

	
	public RobotApi(Udpsocket _socket, QQUser _user)
	{
		this.user = _user;
		this.socket = _socket;
	}
	
	@Override
	public void SendGroupTextMessage(long gin, String msg)
	{
		SendMessage.SendGroupMessage(this.user, this.socket, gin,msg,false);
		// TODO: Implement this method
	}

	@Override
	public void SendGroupXMLMessage(long gin, String xml)
	{
		SendMessage.SendGroupMessage(this.user, this.socket, gin,xml,true);
		// TODO: Implement this method
	}

	@Override
	public void SendGroupBitmapMessage(long gin, Bitmap bitmap)
	{
		SendMessage.SendGroupMessage(this.user,this.socket,gin,bitmap);
		// TODO: Implement this method
	}

	@Override
	public void SendFriendTextMessage(long uin, String msg)
	{
		SendMessage.SendFriendMessage(this.user,this.socket,uin,msg,false);
		// TODO: Implement this method
	}

	@Override
	public void SendFriendXMLMessage(long uin, String xml)
	{
		SendMessage.SendFriendMessage(this.user,this.socket,uin,xml,true);
		// TODO: Implement this method
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
				body = "gc=" + gc + "&shutup_list=" +URLEncoder.encode(data.toString()) + "&bkn=" + user.bkn + "&src=qinfo_v2";
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

	@Override
	public void setGroupMemberCard(long gin, long uin, String card)
	{
		try
		{
			String urls="http://qinfo.clt.qq.com/cgi-bin/mem_card/set_group_mem_card";
			URL lll = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) lll.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Cookie", user.quncookie);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			String body="gc="+gin+"&u="+uin+"&name="+URLEncoder.encode(card)+"&bkn="+user.bkn;
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
