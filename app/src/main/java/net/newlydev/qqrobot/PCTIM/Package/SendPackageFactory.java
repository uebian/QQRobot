package net.newlydev.qqrobot.PCTIM.Package;
import android.graphics.*;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Message.*;
import net.newlydev.qqrobot.PCTIM.TLV.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;
import java.io.*;

public class SendPackageFactory
{
	protected static int _seq = 0x3100; // (char)Util.Random.Next();
	protected static byte[] body_end = {0x03};
	public static byte[] get001d(QQUser user)
	{
		//Util.log("[发送包] 命令: 00 1d");
		ByteBuilder builder = new ByteBuilder();


		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);

		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("00 1d"));
		builder.writeint(GetNextSeq());

		builder.writelong(user.QQ);

		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);



		ByteBuilder body_builder = new ByteBuilder() ;
		body_builder.writebytes(Util.str_to_byte(" 33 00 05 00 08 74 2E 71 71 2E 63 6F 6D 00 0A 71 75 6E 2E 71 71 2E 63 6F 6D 00 0C 71 7A 6F 6E 65 2E 71 71 2E 63 6F 6D 00 0C 6A 75 62 61 6F 2E 71 71 2E 63 6F 6D 00 09 6B 65 2E 71 71 2E 63 6F 6D "));

		byte[] tlv_data = body_builder.getdata();
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(tlv_data, user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}
	public static byte[] get00ba(QQUser user, String code)//
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("00 ba"));
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);
		builder.writebytes(user.QQPacket00BaKey);
		ByteBuilder body_builder = new ByteBuilder() ;
		body_builder.writebytes(Util.str_to_byte("00 02 00 00 08 04 01 E0"));
		body_builder.writebytes(user.TXProtocol.DwSsoVersion);
		body_builder.writebytes(user.TXProtocol.DwServiceId);
		body_builder.writebytes(user.TXProtocol.DwClientVer);
		body_builder.writebyte((byte) 0x00);
		body_builder.writebytesbylength(user.TXProtocol.BufSigClientAddr);
		body_builder.writebytes(new byte[] {0x01, 0x02});
		body_builder.writebytesbylength(user.TXProtocol.BufDhPublicKey);
		if (code.equals(""))
		{
			body_builder.writebytes(new byte[] {0x13, 0x00, 0x05, 0x00, 0x00, 0x00, 0x00});
			body_builder.writebyte(user.QQPacket00BaSequence);
			if (user.TXProtocol.PngToken == null || user.TXProtocol.PngToken.length == 0)
			{
				body_builder.writebyte((byte) 0x00);
			}
			else
			{
				body_builder.writebytesbylength(user.TXProtocol.PngToken);
			}
		}
		else
		{
			byte[] verifyCodeBytes = code.getBytes();
			body_builder.writebytes(new byte[] {0x14, 0x00, 0x05, 0x00, 0x00, 0x00, 0x00});
			body_builder.writeint(verifyCodeBytes.length);///?????
		    body_builder.writebytes(verifyCodeBytes);
			body_builder.writebytesbylength(user.TXProtocol.BufSigPic);//输入验证码后清空图片流
			user.QQPacket00BaVerifyCode = new byte[] { };
			user.QQPacket00BaSequence = 1;
		}

		body_builder.writebytesbylength(user.QQPacket00BaFixKey);
		byte[] tlv_data = body_builder.getdata();
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(tlv_data, user.QQPacket00BaKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		user.QQPacket00BaSequence += 1;
		return builder.getdata();
	}
	
	public static byte[] get0825(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(new byte[]{0x08,0x25});
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);
		builder.writebytes(user.QQPacket0825Key);
		ByteBuilder tlv_builder = new ByteBuilder() ;
		byte[] tlv0018 = TLVFactory.tlv0018(user);
		byte[] tlv0039 = TLVFactory.tlv0309(user);
		byte[] tlv0036 = TLVFactory.tlv0036(2);
		byte[] tlv0114 = TLVFactory.tlv0114(user);
		tlv_builder.writebytes(tlv0018);
		tlv_builder.writebytes(tlv0039);
		tlv_builder.writebytes(tlv0036);
		tlv_builder.writebytes(tlv0114);
		byte[] tlv_data = tlv_builder.getdata();
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(tlv_data, user.QQPacket0825Key);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}

	public static byte[] get0836(QQUser user, boolean need_verifycode)
	{
		ByteBuilder builder = new ByteBuilder();
		byte[] tlv0110 = null;
		byte[] tlv0032 = null;
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(new byte[]{0x08,0x36});
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);
		builder.writebytes(user.TXProtocol.SubVer);
		builder.writebytes(user.TXProtocol.EcdhVer);
		builder.writebytesbylength(user.TXProtocol.BufDhPublicKey);
		builder.writebytes(new byte[] { 0x00, 0x00, 0x00, 0x10 });
		builder.writebytes(user.QQPacket0836Key1);
		ByteBuilder  tlv_builder = new ByteBuilder();
		byte[] tlv0112 = TLVFactory.tlv0112(user);
		byte[] tlv030f = TLVFactory.tlv030f(user);
		byte[] tlv0005 = TLVFactory.tlv0005(user);
		byte[] tlv0006 = TLVFactory.tlv0006(user);
		byte[] tlv0015 = TLVFactory.tlv0015(user);
		byte[] tlv001a = TLVFactory.tlv001a(tlv0015, user);
		byte[] tlv0018 = TLVFactory.tlv0018(user);
		byte[] tlv0103 = TLVFactory.tlv0103(user);
		if (need_verifycode)
		{
			tlv0110 = TLVFactory.tlv0110(user);
			tlv0032 = TLVFactory.tlv0032(user);
		}
		byte[] tlv0312= TLVFactory.tlv0312();
		byte[] tlv0508 = TLVFactory.tlv0508();
		byte[] tlv0313= TLVFactory.tlv0313(user);
		byte[] tlv0102= TLVFactory.tlv0102(user);
		tlv_builder.writebytes(tlv0112);
		tlv_builder.writebytes(tlv030f);
		tlv_builder.writebytes(tlv0005);
		tlv_builder.writebytes(tlv0006);
		tlv_builder.writebytes(tlv0015);
		tlv_builder.writebytes(tlv001a);
		tlv_builder.writebytes(tlv0018);
		tlv_builder.writebytes(tlv0103);
		if (need_verifycode)
		{
			tlv_builder.writebytes(tlv0110);
		}
		tlv_builder.writebytes(tlv0312);
		tlv_builder.writebytes(tlv0508);
		tlv_builder.writebytes(tlv0313);
		tlv_builder.writebytes(tlv0102);
		byte[] tlv_data = tlv_builder.getdata();
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(tlv_data, user.TXProtocol.BufDhShareKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}

	public static byte[] get0828(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(new byte[]{0x08,0x28});
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(new byte[] { 0x02, 0x00, 0x00});
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(new byte[] { 0x00, 0x30, 0x00, 0x3a });
		builder.writebytesbylength(user.TXProtocol.BufSigSession);
		ByteBuilder tlv_builder = new ByteBuilder();
		byte[] tlv0007 = TLVFactory.tlv0007(user);
		byte[] tlv000c = TLVFactory.tlv000c(user);
		byte[] tlv0015 = TLVFactory.tlv0015(user);
		byte[] tlv0036 = TLVFactory.tlv0036(2);
		byte[] tlv0018 = TLVFactory.tlv0018(user);
		byte[] tlv001f = TLVFactory.tlv001f(user);
		byte[] tlv0105 = TLVFactory.tlv0105(user);
		byte[] tlv010b = TLVFactory.tlv010b(user);
		byte[] tlv002d = TLVFactory.tlv002d(user);
		tlv_builder.writebytes(tlv0007);
		tlv_builder.writebytes(tlv000c);
		tlv_builder.writebytes(tlv0015);
		tlv_builder.writebytes(tlv0036);
		tlv_builder.writebytes(tlv0018);
		tlv_builder.writebytes(tlv001f);
		tlv_builder.writebytes(tlv0105);
		tlv_builder.writebytes(tlv010b);
		tlv_builder.writebytes(tlv002d);
		byte[] tlv_data = tlv_builder.getdata();
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(tlv_data, user.TXProtocol.BufSessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}
	
	public static byte[] get00ec(QQUser user, byte[] loginStatus)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("00 ec"));
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);

		ByteBuilder body_builder=new ByteBuilder();

		body_builder.writebytes(new byte[] { 0x01, 0x00 });
	    body_builder.writebytes(loginStatus);
	    body_builder.writebytes(new byte[] { 0x00, 0x01, 0x00, 0x01, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00 });

		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);

		return builder.getdata();

	}

	public static byte[] get0058(QQUser user)
	{
		ByteBuilder builder = new ByteBuilder();

		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(new byte[]{0x00,0x58});
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);

		ByteBuilder body_builder=new ByteBuilder();

		body_builder.writelong(user.QQ);

		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);

		return builder.getdata();

	}
	
	public static byte[] get0017(QQUser user, byte[] data_to_send, byte[] seq)
	{
		ByteBuilder builder = new ByteBuilder();

		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(new byte[]{0x00,0x17});
		builder.writebytes(seq);
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);

		ByteBuilder body_builder=new ByteBuilder();

		body_builder.writebytes(data_to_send);

		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);

		return builder.getdata();
	}

	public static byte[] get00ce(QQUser user, byte[] data_to_send, byte[] seq)
	{
		ByteBuilder builder = new ByteBuilder();

		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("00ce"));
		builder.writebytes(seq);
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);

		ByteBuilder body_builder=new ByteBuilder();

		body_builder.writebytes(data_to_send);

		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);

		return builder.getdata();

	}
	
	public static byte[] get0319(QQUser user, long _recvQQ, byte[] MessageTime)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("0319"));
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(new byte[]
						   {
							   0x04, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x68, 0x1C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							   0x00, 0x00
						   });
		ByteBuilder body_builder=new ByteBuilder();


		body_builder.writebytes(new byte[] { 0x00, 0x00, 0x00, 0x07 });
		ByteBuilder data_builder=new ByteBuilder();

		data_builder.writebytes(Util.str_to_byte("0A0C08"));
		data_builder.writebytes(Util.str_to_byte(Util.PB_toLength(_recvQQ)));
		data_builder.writebyte((byte) 0x10);
		data_builder.writebytes(
			Util.str_to_byte(
				Util.PB_toLength(Long.parseLong(Util.byte2HexString(MessageTime).replace(" ", ""), 16))));
		data_builder.writebytes(new byte[] { 0x20, 0x00 });
		//数据长度

		body_builder.writebytes(Util.subByte(Util.ToByte(data_builder.getdata().length), 0, 4));
		body_builder.writebytes(Util.str_to_byte("08011203980100"));
		//数据
		body_builder.writebytes(data_builder.getdata());

		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}

	public static byte[] get0002(QQUser user, long gin, String msg, boolean isxml)
	{

		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(new byte[]{0x00,0x02});
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);
		ByteBuilder body_builder=new ByteBuilder();
		//long dateTime = Util.GetTimeSeconds(new Date());
		long group = Util.ConvertQQGroupId(gin);
		if (!isxml)
		{
			byte[] message_to_send = Util.constructmessage(user, msg.getBytes());
			body_builder.writebyte((byte)0x2A);
			body_builder.writelong(group);
			body_builder.writeint(message_to_send.length + 50); // 字符串长度 + 56，但是_data里面包含了和长度有关的额外六个byte
			body_builder.writebytes(new byte[]
									{
										0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x4D, 0x53,
										0x47, 0x00,
										0x00, 0x00, 0x00, 0x00
									});
			body_builder.writelong(new Date().getTime() / 1000);
			body_builder.writebytes(Util.RandomKey(4));
			body_builder.writebytes(Util.str_to_byte("0000000009008600"));
			body_builder.writebytes(new byte[] { 0x00, 0x0C });
			body_builder.writebytes(Util.str_to_byte("E5BEAEE8BDAFE99B85E9BB91"));
			body_builder.writebytes(new byte[] { 0x00,0x00 });
			body_builder.writebytes(message_to_send);

		}
		else
		{
			byte[] message_to_send =ZLibUtils.compress(msg.getBytes());
			body_builder.writebyte((byte)0x2A);
			body_builder.writelong(group);
			body_builder.writeint(message_to_send.length + 64);
			body_builder.writebytes(new byte[]
									{
										0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x4D, 0x53,
										0x47, 0x00,
										0x00, 0x00, 0x00, 0x00
									});
			body_builder.writelong(new Date().getTime() / 1000);
			body_builder.writebytes(Util.constructxmlmessage(user, message_to_send));

		}
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}
	
	public static byte[] sendpic(QQUser user, long gin,byte[] bitmap) throws IOException
	{

		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("0002"));
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(Util.str_to_byte("02 00 00 00 01 01 01 00 00 68 20"));

		ByteBuilder body_builder=new ByteBuilder();
		//long dateTime = Util.GetTimeSeconds(new Date());
		long group = Util.ConvertQQGroupId(gin);
		//byte[] guid = ("{"+Util.GetMD5ToGuidHashFromFile(message.Message) + "}."+message.Message.split("[.]")[message.Message.split("[.]").length-1]).getBytes();
		byte[] guid = ("{" + Util.GetMD5ToGuidHashFromBytes(bitmap) + "}.jpg").getBytes();
		body_builder.writebyte((byte)0x2A);
		body_builder.writelong(group);
		body_builder.writebytes(Util.str_to_byte("01 00 00 02 01 00 00 00 00 00 00 00 4D 53 47 00 00 00 00 00"));
		body_builder.writelong(new Date().getTime() / 1000);
		body_builder.writebytes(Util.RandomKey(4));
		body_builder.writebytes(Util.str_to_byte("00 00 00 00 09 00 86 00"));
		body_builder.writebytes(Util.str_to_byte("00 0C"));
		body_builder.writebytes(Util.str_to_byte("E5 BE AE E8 BD AF E9 9B 85 E9 BB 91"));
		body_builder.writebytes(Util.str_to_byte("00 00 03 00 CB 02"));
		body_builder.writebytes(Util.str_to_byte("00 2A"));
		body_builder.writebytes(guid);
		body_builder.writebytes(Util.str_to_byte("04 00 04"));
		body_builder.writebytes(Util.str_to_byte("9B 53 B0 08 05 00 04 D9 8A 5A 70 06 00 04 00 00 00 50 07 00 01 43 08 00 00 09 00 01 01 0B 00 00 14 00 04 11 00 00 00 15 00 04 00 00 02 BC 16 00 04 00 00 02 BC 18 00 04 00 00 7D 5E FF 00 5C 15 36 20 39 32 6B 41 31 43 39 62 35 33 62 30 30 38 64 39 38 61 35 61 37 30"));
		body_builder.writebytes(Util.str_to_byte("20 20 20 20 20 20 35 30 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20"));
		body_builder.writebytes(guid);
		body_builder.writebyte((byte)0x41);


		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}

	public static byte[] get00cd(QQUser user, long uin, String msg, boolean isxml)
	{

		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("00cd"));
		builder.writeint(GetNextSeq());
		builder.writelong(user.QQ);
		builder.writebytes(user.TXProtocol.XxooA);
		builder.writebytes(user.TXProtocol.DwClientType);
		builder.writebytes(user.TXProtocol.DwPubNo);
		builder.writebytes(user.TXProtocol.XxooD);

		ByteBuilder body_builder=new ByteBuilder();
		long dateTime = Util.GetTimeSeconds(new Date());
		byte[] md5 = user.TXProtocol.SessionKey;
		if (!isxml)
		{

			byte[] message_to_send = Util.constructmessage(user, msg.getBytes());
			body_builder.writelong(user.QQ);
			body_builder.writelong(uin);
			body_builder.writebytes(new byte[]
									{
										0x00, 0x00, 0x00, 0x0D, 0x00, 0x01, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x00,
										0x01, 0x01
									});
			body_builder.writebytes(user.TXProtocol.CMainVer);
			body_builder.writebytes(user.TXProtocol.CSubVer);
			body_builder.writelong(user.QQ);
			body_builder.writelong(uin);
			body_builder.writebytes(md5);
			body_builder.writebytes(new byte[] { 0x00, 0x0B });
			body_builder.writebytes(Util.RandomKey(2));
			body_builder.writelong(dateTime);
			body_builder.writebytes(new byte[]
									{
										0x02, 0x34, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x4D,
										0x53, 0x47,
										0x00, 0x00, 0x00, 0x00, 0x00
									});
			body_builder.writelong(dateTime);
			byte[] MessageId = Util.RandomKey(4);
			body_builder.writebytes(MessageId);
			body_builder.writebytes(Util.str_to_byte("0000000009008600"));
			body_builder.writebytes(new byte[] { 0x00, 0x06 });
			body_builder.writebytes(Util.str_to_byte("E5AE8BE4BD93"));
			body_builder.writebytes(new byte[] { 0x00, 0x00 });
			body_builder.writebytes(message_to_send);

		}
		else
		{
			byte[] message_to_send =ZLibUtils.compress(msg.getBytes());
			body_builder.writelong(user.QQ);
			body_builder.writelong(uin);
			body_builder.writebytes(new byte[] { 0x00, 0x00, 0x00, 0x08, 0x00, 0x01, 0x00, 0x04 });
			body_builder.writebytes(new byte[] { 0x00, 0x00, 0x00, 0x00 });
			body_builder.writebytes(Util.str_to_byte("370F"));
			body_builder.writelong(user.QQ);
			body_builder.writelong(uin);
			body_builder.writebytes(md5);
			body_builder.writebytes(Util.str_to_byte("000B"));
			body_builder.writebytes(Util.RandomKey(2));
			body_builder.writelong(dateTime);
			body_builder.writebytes(new byte[]
									{
										0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x4D,
										0x53, 0x47,
										0x00, 0x00, 0x00, 0x00, 0x00
									});
			body_builder.writelong(dateTime);
			body_builder.writebytes(Util.constructxmlmessage(user, message_to_send));
		}
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		return builder.getdata();
	}

	public static byte[] get0388(QQUser user, long gin, Bitmap img)
	{
		ByteBuilder builder = new ByteBuilder();
		builder.writebytes(QQGlobal.QQHeaderBasicFamily);
		builder.writebytes(user.TXProtocol.CMainVer);
		builder.writebytes(user.TXProtocol.CSubVer);
		builder.writebytes(Util.str_to_byte("0388"));
		int seq = GetNextSeq();
		builder.writeint(seq);
		builder.writelong(user.QQ);
		builder.writebytes(Util.str_to_byte("04 00 00 00 01 01 01 00 00 68 20 00 00 00 00 00 00 00 00 "));
		ByteBuilder body_builder=new ByteBuilder();
		long width = img.getWidth(); // 图片的宽度
		long height = img.getHeight(); // 图片的高度
		byte[] img_byte = Util.Bufferedimg_tobytes(img);
		long img_length = img_byte.length;
		byte[] md5 = Util.str_to_byte(Util.getMD5(img_byte));
		body_builder.writebytes(new byte[] {0x00,0x00,0x00,0x07});

		ByteBuilder img_builder=new ByteBuilder();
		img_builder.writebytes(Util.str_to_byte("5A"));
		img_builder.writebyte((byte)0x08);
		img_builder.writebytes(Util.str_to_byte(Util.PB_toLength(gin)));
		img_builder.writebyte((byte)0x10);
		img_builder.writebytes(Util.str_to_byte(Util.PB_toLength(user.QQ)));
		img_builder.writebytes(Util.str_to_byte("18 00"));
		img_builder.writebyte((byte)0x22);
		img_builder.writebyte((byte)md5.length);
		img_builder.writebytes(md5);
		img_builder.writebyte((byte)0x28);
		img_builder.writebytes(Util.str_to_byte(Util.PB_toLength(img_length)));
		img_builder.writebyte((byte)0x32);
		img_builder.writebytes(Util.str_to_byte("1A 37 00 4D 00 32 00 25 00 4C 00 31 00 56 00 32 00 7B 00 39 00 30 00 29 00 52 00 38 01 48 01"));
		img_builder.writebyte((byte)0x50);
		img_builder.writebytes(Util.str_to_byte(Util.PB_toLength(width)));
		img_builder.writebyte((byte)0x58);
		img_builder.writebytes(Util.str_to_byte(Util.PB_toLength(height)));
		img_builder.writebytes(Util.str_to_byte("60 04 6A 05 32 36 36 35 36 70 00 78 03 80 01"));
		img_builder.writebyte((byte)0x00);


		body_builder.writebytes(new byte[] { 0x00, 0x00 });
		body_builder.writeint(img_builder.getdata().length);
		body_builder.writebytes(Util.str_to_byte("08 01 12 03 98 01 01 10 01 1A"));
		//数据
		body_builder.writebytes(img_builder.getdata());
		Crypter crypter = new Crypter();
		byte[] result = crypter.encrypt(body_builder.getdata(), user.TXProtocol.SessionKey);
		builder.writebytes(result);
		builder.writebytes(body_end);
		PictureStore store = new PictureStore();
		store.pictureid = seq;
		store.data = img_byte;
		store.Group = gin;
		user.imgs.add(store);
		return builder.getdata();
	}
	
	protected static int GetNextSeq()
	{
		_seq++;
		// 为了兼容iQQ
		// iQQ把序列号的高位都为0，如果为1，它可能会拒绝，wqfox称是因为TX是这样做的
		int i = 0x7FFF;
		_seq = _seq & i;
		if (_seq == 0)
		{
			_seq++;
		}
		return _seq;
	}
}



