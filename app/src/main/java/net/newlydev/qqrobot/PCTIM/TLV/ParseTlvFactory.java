package net.newlydev.qqrobot.PCTIM.TLV;

import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class ParseTlvFactory
{


	public static void parsetvl(TLV tlv, QQUser user)
	{

		int tlvtype = tlv.Tag;
		if (tlvtype == 274)
		{
			user.TXProtocol.BufSigClientAddr = tlv.Value;
		}
		else if (tlvtype == 23)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
            {
				user.TXProtocol.DwServerTime_Byte = bytefactory.readBytes(4);
				long timeMillis = Util.GetLong(user.TXProtocol.DwServerTime_Byte);
                user.TXProtocol.DwServerTime = Util.GetDateTimeFromMillis(timeMillis);
                user.TXProtocol.TimeDifference = (4294967295l & timeMillis) - new Date().getTime();
                user.TXProtocol.DwClientIP = Util.GetIpStringFromBytes(bytefactory.readBytes(4));
				user.TXProtocol.DwClientIP_Byte = Util.IPStringToByteArray(user.TXProtocol.DwClientIP);
                user.TXProtocol.WClientPort = Util.bytesToshort(bytefactory.readrestBytes());



			}
		}
		else if (tlvtype == 784)
		{
			user.TXProtocol.DwServerIP = Util.GetIpStringFromBytes(tlv.Value);
		}
		else if (tlvtype == 12)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 2)
			{
				user.TXProtocol.DwIdc = bytefactory.readBytes(4); /*dwIDC =*/

				user.TXProtocol.DwIsp = bytefactory.readBytes(4); /*dwISP =*/
				bytefactory.readBytes(2);
				byte[] ip_byte = bytefactory.readBytes(4);
				user.TXProtocol.DwRedirectIP = Util.GetIpStringFromBytes(ip_byte); /*dwRedirectIP =*/
				user.TXProtocol.WRedirectips.add(ip_byte);
				user.TXProtocol.WRedirectPort_Byte = bytefactory.readBytes(6);
				user.TXProtocol.WRedirectPort = Util.bytesToshort(user.TXProtocol.WRedirectPort_Byte);
				/*wRedirectPort =*/
			}
			else
			{
				System.out.println("未知版本类型");
			}

		}
		else if (tlvtype == 30)
		{
			user.TXProtocol.BufTgtgtKey = tlv.Value;
		}
		else if (tlvtype == 6)
		{
			user.TXProtocol.BufTgtgt = tlv.Value;
		}
		else if (tlvtype == 272)
		{
			if (Util.subByte(tlv.Value, 1, 1)[0] != 1)
			{
			    System.out.println("未知版本");
			}
			else
			{
				user.TXProtocol.BufSigPic = Util.subByte(tlv.Value, 2, tlv.Length - 2);
			}
		}
		else if (tlvtype == 277)
		{
			byte[] bufPacketMD5 = tlv.Value;

		}
		else if (tlvtype == 265)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
			{
				user.TXProtocol.BufSessionKey = bytefactory.readBytes(16);

			    user.TXProtocol.BufSigSession = bytefactory.readBytesbylength();

				user.TXProtocol.BufPwdForConn = bytefactory.readBytesbylength();
			}
			else
			{
				System.out.println("未知版本类型");
			}
		}
		else if (tlvtype == 259)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
			{

				user.TXProtocol.BufSid = bytefactory.readBytesbylength();
			}
			else
			{
				System.out.println("未知版本类型");
			}
		}
		else if (tlvtype == 263)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
			{
				bytefactory.readBytesbylength();
				user.TXProtocol.BufTgtGtKey = bytefactory.readBytes(16);


				user.TXProtocol.BufTgt = bytefactory.readBytesbylength();

				user.TXProtocol.Buf16BytesGtKeySt = bytefactory.readBytes(16);

				user.TXProtocol.BufServiceTicket = bytefactory.readBytesbylength();

				byte[] http = bytefactory.readBytesbylength();
				ByteFactory httpfactory = new ByteFactory(tlv.Value);

				user.TXProtocol.Buf16BytesGtKeyStHttp = httpfactory.readBytes(16);

				user.TXProtocol.BufServiceTicketHttp = httpfactory.readBytesbylength();
				user.TXProtocol.BufGtKeyTgtPwd = httpfactory.readBytes(16);

			}
			else
			{
				System.out.println("未知版本类型");
			}

		}
		else if (tlvtype == 264)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
			{
				byte[] data = bytefactory.readBytesbylength();
				ByteFactory datafactory = new ByteFactory(new ByteFactory(data).readBytesbylength());
				byte[] wSsoAccountWFaceIndex = datafactory.readBytes(2);
				int length = Util.GetInt(new byte[]{0x00,datafactory.readBytes(1)[0]});
				if (length > 0)
				{
				    user.NickName = new String(datafactory.readString(length));
				}
				user.Gender = datafactory.readBytes(1)[0];
				byte[] dwSsoAccountDwUinFlag = datafactory.readBytes(4);
                user.Age = datafactory.readBytes(1)[0];
			}
			else
			{
				System.out.println("未知版本类型");
			}

		}
		else if (tlvtype == 13)
		{
			byte[] WSubVer =  Util.subByte(tlv.Value, 1, 1);//wSubVer
			if (WSubVer[0] == 1)
			{

			}
			else
			{
				System.out.println("未知版本类型");
			}
		}
		else if (tlvtype == 31)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			user.TXProtocol.BufDeviceId = bytefactory.readrestBytes();
		}
		else if (tlvtype == 20)
		{
			byte[] WSubVer = Util.subByte(tlv.Value, 1, 1);
			if (WSubVer[0] == 1)
            {

            }
            else
            {
                System.out.println("未知版本类型");
            }
		}
		else if (tlvtype == 268)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
            {

				user.TXProtocol.SessionKey = bytefactory.readBytes(16);
				byte[] dwUin = bytefactory.readBytes(4);
				String dwClientIP = Util.GetIpStringFromBytes(bytefactory.readBytes(4));
				user.TXProtocol.WClientPort = Util.GetShort(bytefactory.readBytes(2));

				//....
            }
            else
            {
                System.out.println("未知版本类型");
            }

		}
		else if (tlvtype == 270)
		{
			ByteFactory bytefactory = new ByteFactory(tlv.Value);
			bytefactory.readBytes(1);
			byte[] WSubVer = bytefactory.readBytes(1);
			if (WSubVer[0] == 1)
            {


                ByteFactory sigfactory = new ByteFactory(bytefactory.readBytesbylength());
				byte[] dwUinLevel =sigfactory.readBytes(3);
                byte[] dwUinLevelEx = sigfactory.readBytes(3);


                byte[] buf24ByteSignature = sigfactory.readBytesbylength();



                byte[] buf32ByteValueAddedSignature = sigfactory.readBytesbylength();



                byte[] buf12ByteUserBitmap = sigfactory.readBytesbylength();

				user.TXProtocol.ClientKey = buf32ByteValueAddedSignature;
			}
            else
            {
                System.out.println("未知版本类型");
            }


		}
		else if (tlvtype == 47)
		{
			byte[] WSubVer = Util.subByte(tlv.Value, 1, 1);
			if (WSubVer[0] == 1)
            {

			}
            else
            {
                System.out.println("未知版本类型");
            }
		}
		else if (tlvtype == 269)
		{
			byte[] WSubVer = Util.subByte(tlv.Value, 1, 1);
			if (WSubVer[0] == 1)
            {

			}
            else
            {
                System.out.println("未知版本类型");
            }
		}
		else if (tlvtype == 261)
		{
			byte[] WSubVer = Util.subByte(tlv.Value, 1, 1);
			if (WSubVer[0] == 1)
            {

			}
            else
            {
                System.out.println("未知版本类型");
            }

		}
		else if (tlvtype == 256)
		{
			System.out.println(Util.byte2HexString(tlv.Value));


		}


		else if (tlvtype == 260)
		{
			ByteFactory factory = new ByteFactory(tlv.Value);

			int WSubVer = factory.readint(); //wSubVer
            if (WSubVer == 0x0001)
            {
                int wCsCmd = factory.readint();
                long errorCode = factory.readlong();

                factory.readBytes(1); //0x00
                factory.readBytes(1); //0x05
				byte PngData = factory.readBytes(1)[0]; //是否需要验证码：0不需要，1需要
                int len;
                if (PngData == 0x00)
                {
                    len = factory.readBytes(1)[0];
                    while (len == 0)
                    {
                        len = factory.readBytes(1)[0];
                    }
                }
                else //ReplyCode != 0x01按下面走 兼容多版本
                {
                    factory.readlong(); //需要验证码时为00 00 01 23，不需要时为全0
                    len = factory.readint();
                }

                byte[] buffer = factory.readBytes(len);
                user.TXProtocol.BufSigPic = buffer;
                if (PngData == 0x01) //有验证码数据
                {
                    len = factory.readint();
                    buffer = factory.readBytes(len);
                    user.QQPacket00BaVerifyCode = buffer;
                    user.Next = factory.readBytes(1)[0];
                    factory.readBytes(1);
                    //var directory = Util.MapPath("Verify");
                    //var filename = Path.Combine(directory, user.QQ + ".png");
                    //if (!Directory.Exists(directory))
                    //{
                    //    Directory.CreateDirectory(directory);
                    //}

                    //var fs = Next == 0x00
                    //    ? new FileStream(filename, FileMode.Create, FileAccess.ReadWrite, FileShare.Read)
                    //    : new FileStream(filename, FileMode.Append, FileAccess.Write, FileShare.Read);

                    ////fs.Seek(0, SeekOrigin.End);
                    //fs.Write(buffer, 0, buffer.Length);
                    //fs.Close();
                    len = factory.readint();
                    buffer = factory.readBytes(len);
                    user.TXProtocol.PngToken = buffer;
                    if (factory.data.length > factory.position)
                    {
                        factory.readint();
                        len = factory.readint();
                        buffer = factory.readBytes(len);
                        user.TXProtocol.PngKey = buffer;
                    }
                }
            }

		}

		else
		{

			System.out.println("未知tlv解析:" + tlv.Tag);
		}
	}
}
