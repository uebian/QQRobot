package net.newlydev.qqrobot.PCTIM.Utils;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class ByteBuilder
{
	public byte[] data = new byte[]{};
	
	public ByteBuilder(){
		
	}

	public void clean()
	{
		this.data = new byte[]{};
	}

	public void writebytesbylength(byte[] to_write)
	{
		this.writeint(to_write.length);
		this.writebytes(to_write);
	}

	
	public void writebytes(byte[] to_write){
		this.data = Util.byteMerger(this.data,to_write);
	}
	
	public void rewritebytes(byte[] to_write){
		this.data = Util.byteMerger(to_write,this.data);
	}
	
	public void writebyte(byte to_write){
		this.data = Util.byteMerger(this.data,new byte[]{to_write});
	}
	
	public void writeint(int to_write){
		byte[] test = Util.subByte(Util.ToByte(to_write),2,2);
		
		this.data = Util.byteMerger(this.data,test);
	}
	public void rewriteint(int to_write)
	{
		byte[] test = Util.subByte(Util.ToByte(to_write),2,2);

		this.data = Util.byteMerger(test,this.data);
	}
	
	
	public void writelong(long to_write){
		byte[] test = Util.subByte(Util.ToByte(to_write),4,4);

		this.data = Util.byteMerger(this.data,test);
	}
	public void writeshort(short to_write){
		byte[] test = Util.subByte(Util.ToByte(to_write),0,4);
		
		this.data = Util.byteMerger(this.data,test);
	}
	
	public byte[] getdata(){
		return this.data;
	}
}
