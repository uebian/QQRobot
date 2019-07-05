package net.newlydev.qqrobot.PCTIM.Utils;

import net.newlydev.qqrobot.PCTIM.Utils.*;

public class ByteFactory
{
	public int position = 0;
	public byte[] data = null;
	public ByteFactory(byte[] _data){
		this.data=_data;
	}

	public byte[] readrestBytes()
	{
		int length = this.data.length -this.position;
		byte[] test = Util.subByte(this.data,this.position,length);
		this.position +=length;
		return test;
	}


	
	public String readStringbylength(){
		int length = Util.GetInt(Util.subByte(this.data,this.position,2));
		this.position +=2;
		byte[] test = Util.subByte(this.data,position,length);
		this.position +=length;
		return new String(test);
	}
	
	public String readString(int length)
	{
		byte[] test = Util.subByte(this.data,position,length);
		this.position +=length;
		return new String(test);
	}
	
	
	public byte[] readBytesbylength(){
		int length = Util.GetInt(Util.subByte(this.data,this.position,2));
		this.position +=2;
		byte[] test = Util.subByte(this.data,position,length);
		this.position +=length;
		return test;
	}
	
	public byte[] readBytes(int length){
		byte[] test = Util.subByte(this.data,this.position,length);
		this.position +=length;
		return test;
	}
	
	
	
	public int readint(){
		int test = Util.GetInt(Util.subByte(this.data,this.position,2));
		this.position +=2;
		return test;
	}
	
	
	
	public long readlong()
	{
		long test = Util.GetLong(Util.subByte(this.data,this.position,4));
		this.position +=4;
		return test;
	}
}
