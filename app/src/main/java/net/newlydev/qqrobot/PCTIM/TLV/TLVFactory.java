package net.newlydev.qqrobot.PCTIM.TLV;

import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class TLVFactory
{

	
	public static byte[] tlv0018(QQUser user){
		ByteBuilder builder = new ByteBuilder();
	    byte[] WSubVer ={0x00,0x01};
		builder.writebytes(WSubVer); //wSubVer 
		builder.writebytes(user.TXProtocol.DwSsoVersion); //dwSSOVersion
		builder.writebytes(user.TXProtocol.DwServiceId); //dwServiceId
		builder.writebytes(user.TXProtocol.DwClientVer); //dwClientVer
		builder.writelong(user.QQ);
		builder.writeint(user.TXProtocol.WRedirectCount); //wRedirectCount 
		builder.writebytes(new byte[]{00,00});
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x18});//头部
		
		return builder.getdata();
	}
	
	public static byte[] tlv0309(QQUser user){
		ByteBuilder builder = new ByteBuilder();
	    byte[] WSubVer ={0x00,0x01};
		builder.writebytes(WSubVer); //wSubVer
		builder.writebytes(Util.IPStringToByteArray(user.TXProtocol.DwServerIP)); //LastServerIP - 服务器最后的登录IP，可以为0
		builder.writebytes( Util.subByte(Util.ToByte(user.TXProtocol.WRedirectips.size()),3,1)); //cRedirectCount - 重定向的次数（IP的数量）
		for (byte[] ip : user.TXProtocol.WRedirectips)
			{
				builder.writebytes(ip);
			}

		builder.writebytes(user.TXProtocol.CPingType); //cPingType 
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x03,0x09});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv0036(int ver){
		ByteBuilder builder = new ByteBuilder();
		
		if (ver ==2){
			builder.writebytes(new byte[]{0x00,0x02,0x0,0x01,0x00,0x00,0x00,0x05,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}); //wSubVer
			builder.rewriteint(builder.data.length); //长度
			builder.rewritebytes(new byte[]{0x00,0x36});//头部
		}else if  (ver ==1){
			builder.rewritebytes(new byte[]{0x00,0x01,0x0,0x01,0x0,0x00,0x0,0x00}); //wSubVer
			builder.rewriteint(builder.data.length); //长度
			builder.rewritebytes(new byte[]{0x00,0x36});//头部
		}
		
		return builder.getdata();
	}
	
	
	public static byte[] tlv0114(QQUser user){
		ByteBuilder builder = new ByteBuilder();
	    byte[] WSubVer ={0x01,0x02};
		builder.rewritebytes(WSubVer); //wDHVer
		builder.writebytesbylength(user.TXProtocol.BufDhPublicKey); //bufDHPublicKey长度
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x01,0x14});//头部

		return builder.getdata();
	}
	
	
	
	public static byte[] tlv0112(QQUser user){
	    
		ByteBuilder builder = new ByteBuilder();
		
		builder.writebytes(user.TXProtocol.BufSigClientAddr);
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x01,0x12});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv030f(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytesbylength(user.TXProtocol.BufComputerName.getBytes());
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x03,0x0f});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv0005(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x02};
		builder.writebytes(WSubVer);
		builder.writelong(user.QQ);
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x05});//头部

		return builder.getdata();
	}
	
	
	public static byte[] tlv0006(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		if (user.TXProtocol.BufTgtgt == null){

			byte[] WSubVer = {0x00,0x02};

			byte[] random_byte = Util.random_byte(4);
			builder.writebytes(random_byte);
			builder.writebytes(WSubVer);
			builder.writelong(user.QQ);
			builder.writebytes(user.TXProtocol.DwSsoVersion);
			builder.writebytes(user.TXProtocol.DwServiceId);
			builder.writebytes(user.TXProtocol.DwClientVer);
			builder.writebytes(new byte[]{0x00,0x00});
			builder.writebytes(user.TXProtocol.BRememberPwdLogin);
			builder.writebytes(user.MD51);    //密码
			builder.writebytes(user.TXProtocol.DwServerTime_Byte);
			builder.writebytes(new byte[13]);
			builder.writebytes(user.TXProtocol.DwClientIP_Byte);
			builder.writebytes(user.TXProtocol.DwIsp); //dwISP
			builder.writebytes(user.TXProtocol.DwIdc); //dwIDC
			builder.writebytesbylength(user.TXProtocol.BufComputerIdEx); //机器码
			builder.writebytes(user.TXProtocol.BufTgtgtKey); //临时密匙
			Crypter crypter = new Crypter();
			user.TXProtocol.BufTgtgt = crypter.encrypt(builder.getdata(), user.MD52);
		}
		builder.clean();
		builder.writebytes(user.TXProtocol.BufTgtgt);
		
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x06});//头部
		
		return builder.getdata();
	}

	
	public static byte[] tlv0015(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x01};
		builder.writebytes(WSubVer);
		builder.writebyte((byte)0x01);
		builder.writebytes(user.TXProtocol.BufComputerId_crc32_reversed);
		builder.writebytesbylength(user.TXProtocol.BufComputerId);
		builder.writebyte((byte)0x02);
		builder.writebytes(user.TXProtocol.BufComputerIdEx_crc32_reversed);
		
		builder.writebytesbylength(user.TXProtocol.BufComputerIdEx);
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x15});//头部

		return builder.getdata();
	}


	public static byte[] tlv001a(byte[] tlv0015, QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		
		Crypter crypter = new Crypter();
		
		builder.writebytes(crypter.encrypt(tlv0015,user.TXProtocol.BufTgtgtKey));
		
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x1a});//头部

		return builder.getdata();
	}

	
	public static byte[] tlv0103(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x01};
		builder.writebytes(WSubVer);
		builder.writebytesbylength(user.TXProtocol.BufSid);
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x01,0x03});//头部

		return builder.getdata();
		
	}

	public static byte[] tlv0312()
	{
		ByteBuilder builder = new ByteBuilder();
	
		builder.writebytes(new byte[]{0x01,0x00,0x00,0x00,0x01});
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x03,0x12});//头部

		return builder.getdata();
	}

	public static byte[] tlv0508()
	{
		ByteBuilder builder = new ByteBuilder();

		builder.writebytes(new byte[]{0x01,0x00,0x00,0x00,0x00});
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x05,0x08});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv0313(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();

		builder.writebytes(new byte[]{0x01,0x01,0x02});
		
		builder.writebytesbylength(user.TXProtocol.BufMacGuid);
		builder.writebytes(new byte[]{0x00,0x00,0x00,0x02});
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x03,0x13});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv0102(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x01};
		builder.writebytes(WSubVer);
		
		builder.writebytes(Util.str_to_byte("9e9b03236d7fa881a81072ec5097968e"));
		byte[] pic_byte = null;
		if (user.TXProtocol.BufSigPic == null){
		    pic_byte = Util.RandomKey(56);
		}else{
			pic_byte = user.TXProtocol.BufSigPic;
		}
		builder.writebytesbylength(pic_byte);
		byte[] crckey = Util.RandomKey(16);
		byte[] crccode = Util.get_crc32(crckey);
		
		builder.writebytesbylength(Util.byteMerger(crckey,crccode));
		
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x01,0x02});//头部

		return builder.getdata();
	}

	public static byte[] tlv0110(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x01};
		if (user.TXProtocol.BufSigPic == null)
		{
			return new byte[] { };
		}else{
			
			builder.writebytes(WSubVer); //wSubVer
			builder.writebytesbylength(user.TXProtocol.BufSigPic);
			builder.rewriteint(builder.data.length); //长度
			builder.rewritebytes(new byte[]{0x01,0x10});//头部
			return builder.getdata();
		}
		
	}
	
	public static byte[] tlv0032(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(Util.GetQdData(user));
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x32});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv0007(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(user.TXProtocol.BufTgt);
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x07});//头部

		return builder.getdata();
	}

	public static byte[] tlv000c(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x02};
		builder.writebytes(WSubVer);
		builder.writebytes(new byte[]{0x00,0x00});
		builder.writebytes(user.TXProtocol.DwIdc);
		builder.writebytes(user.TXProtocol.DwIsp);
		if (user.TXProtocol.DwServerIP == null){
		    builder.writebytes(Util.IPStringToByteArray(user.TXProtocol.DwServerIP));
		}else{
			builder.writebytes(Util.IPStringToByteArray(user.TXProtocol.DwRedirectIP));
			
		}
		builder.writeshort(user.TXProtocol.WServerPort);
		builder.writebytes(new byte[]{0x00,0x00});
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x0c});//头部

		return builder.getdata();
	}

	public static byte[] tlv001f(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x01};
		builder.writebytes(WSubVer);
		builder.writebytes(user.TXProtocol.BufDeviceId);
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x1f});//头部

		return builder.getdata();
	}
	
	public static byte[] tlv0105(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		
		byte[] WSubVer = {0x00,0x01};
		builder.writebytes(WSubVer);
		builder.writebytes(user.TXProtocol.XxooB);
		builder.writebytes(new byte[]{0x02,0x00,0x14,0x01,0x01,0x00,0x10});
		builder.writebytes(Util.RandomKey());
		builder.writebytes(new byte[]{0x00,0x14,0x01,0x02,0x00,0x10});
		builder.writebytes(Util.RandomKey());
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x01,0x05});//头部

		return builder.getdata();
	}

	public static byte[] tlv010b(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();

		byte[] WSubVer = {0x00,0x02};
		builder.writebytes(WSubVer);
		byte[] newbyte = user.TXProtocol.BufTgt;
		byte flag = EncodeLoginFlag(newbyte, QQGlobal.QqexeMD5);
		builder.writebytes(user.MD51);
		builder.writebyte(flag);
		builder.writebyte((byte)0x10);
		builder.writebytes(new byte[]{0x00,0x00,0x00,0x00});
		builder.writebytes(new byte[]{0x00,0x00,0x00,0x02});
		byte[] qddata = Util.GetQdData(user);
		builder.writebytesbylength(qddata);
		builder.writebytes(new byte[]{0x00,0x00,0x00,0x00});
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x01,0x0b});//头部

		return builder.getdata();
	}

	public static byte[] tlv002d(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] WSubVer = {0x00,0x01};
		builder.writebytes(WSubVer);
		builder.writebytes(Util.IPStringToByteArray(Util.getHostIP()));
		builder.rewriteint(builder.data.length); //长度
		builder.rewritebytes(new byte[]{0x00,0x2d});//头部

		return builder.getdata();
	}

	
	
	
	private static byte EncodeLoginFlag(byte[] bufTgt /*bufTGT*/, byte[] qqexeMD5 /*QQEXE_MD5*/)
	{
		byte flag = 1;
		byte rc = flag;
		for (byte t : bufTgt)
		{
			rc ^= t;
		}

		for (int i = 0; i < 4; i++)
		{
			int rcc = qqexeMD5[i * 4]&0x0ffffff;
			rcc ^= qqexeMD5[i * 4 + 1];
			rcc ^= qqexeMD5[i * 4 + 3];
			rcc ^= qqexeMD5[i * 4 + 2];
			rc ^= rcc;
		}
		return rc;
	}
	
	
}

