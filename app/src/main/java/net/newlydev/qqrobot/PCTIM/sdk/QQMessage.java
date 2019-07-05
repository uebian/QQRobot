package net.newlydev.qqrobot.PCTIM.sdk;
import java.util.*;

public class QQMessage
{
	public int Message_Type  = 0;
	public long Sender_Uin = 0;
	public long Message_Time = 0;
	public long Message_Id =0;

	public byte[] MessageIndex;

	public long Reciece_Message_Time;

	public long Send_Message_Time;

	public String Message_Font;

	public String Message = "";

	public String Message_Text;

	public boolean Isat;
	
	public List<String> Atlist = new ArrayList<String>();

	public String SendName;

	public long Group_uin;

	public long Self_uin;

	public int contain_type =0;
	
	public QQMessage(){
		
	}
	
}
