package net.newlydev.qqrobot.PCTIM.sdk;

public interface API
{
	public void SendGroupMessage(MessageFactory factory);
	public void SendFriendMessage(MessageFactory factory);
	public void GroupMemberShutUp(long guin,long uin,int time);
}
