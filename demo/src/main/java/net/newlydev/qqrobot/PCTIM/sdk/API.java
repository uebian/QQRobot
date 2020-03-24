package net.newlydev.qqrobot.PCTIM.sdk;
import android.graphics.*;
import android.content.Context;

public interface API
{
	public void SendGroupTextMessage(long gin,String msg);
	public void SendGroupXMLMessage(long gin,String xml);
	public void SendGroupBitmapMessage(long gin,Bitmap bitmap);
	public void SendFriendTextMessage(long uin,String msg);
	public void SendFriendXMLMessage(long uin,String xml);
	public void GroupMemberShutUp(long gin,long uin,int time);
	public void setGroupMemberCard(long gin,long uin,String card);
	public Context getContext();
}
