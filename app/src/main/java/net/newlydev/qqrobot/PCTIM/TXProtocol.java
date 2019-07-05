package net.newlydev.qqrobot.PCTIM;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class TXProtocol
{
	public byte[] PngKey = new byte[0];
	
	public byte[] PngToken =new byte[0];
	public byte[] CMainVer = {0x37};
	public byte[] CSubVer = {0x13};
	
	public byte[] XxooA = { 0x03, 0x00, 0x00 };
	public byte[] XxooD = { 0x00, 0x00, 0x00, 0x00 };
	public byte[] XxooB = {0x01};
	public byte[] SubVer  = {0x00,0x01};
	public byte[] EcdhVer  = {0x01,0x03};
	public byte[] DwClientType = { 0x00, 0x01, 0x2e, 0x01 };
	
	
	public byte[] DwPubNo = { 0x00, 0x00, 0x68, 0x52 };
	
	public byte[] DwSsoVersion  = {0x00,0x00,0x04,0x53};
	public byte[] DwServiceId  = {0x00,0x00,0x00,0x01};
	
	public  byte[] DwClientVer  = Util.str_to_byte("00 00 15 85");
	
	
	public int  WRedirectCount =0;
	
	public String DwServerIP  = Util.http_dns("sz.tencent.com");
	public short WServerPort  = 8000;
	public List<byte[]>  WRedirectips = new ArrayList<byte[]>();
	
	public static byte[] CPingType  ={0x02};
	
	
	public byte[] BufDhPublicKey  = Util.str_to_byte("02 6D 28 41 D2 A5 6F D2 FC 3E 2A 1F 03 75 DE 6E 28 8F A8 19 3E 5F 16 49 D3");
	
	public String DwRedirectIP ;
	
	public byte[] BufSigClientAddr ;
	public Date DwServerTime ;
	public byte[] DwServerTime_Byte ;
	
	public long TimeDifference ;
	public String DwClientIP ;
	public byte[] DwClientIP_Byte ;

	public short WClientPort ;
	
	public byte[] DwIsp = new byte[] {0x00,0x00,0x00,0x00};
	public byte[] DwIdc = new byte[] {0x00,0x00,0x12,0x00};
	
	public short WRedirectPort ;
	public byte[] WRedirectPort_Byte ;
	
	public byte[] BufDhShareKey  = Util.str_to_byte("1A E9 7F 7D C9 73 75 98 AC 02 E0 80 5F A9 C6 AF");
	
	public String BufComputerName  = "马化腾的灵魂战妓";
	
	public byte[] BufTgtgt ;
	
	public byte[] BRememberPwdLogin  = {0x00};
	public byte[] BufComputerId_crc32_reversed  = Util.str_to_byte("CCC2E96A");
	//public byte[] bufComputerID  = Util.RandomKey();
	public byte[] BufComputerIdEx  = Util.str_to_byte("7798000BAB5D4F3D3050652C4A2AF865");

	public byte[] BufComputerIdEx_crc32_reversed  = Util.str_to_byte("3CDE845F");

	public byte[] BufDeviceId  =Util.str_to_byte("0fabbe2104a72af1e19da1956a363df07b22ff2ec2cac92ba8d6da459d31a960");
	
	public byte[] BufComputerId  ={ 0x43, 0x04, 0x21, 0x7D, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	
	public byte[] BufTgtgtKey  = Util.RandomKey();
	
	public byte[] BufSid  = Util.str_to_byte("1EC12571B24CEA919A6E8DE6954ECE06");
	public byte[] BufMacGuid  = Util.str_to_byte("214B1A0409ED1970987551BB2D3A7E0A");
	public byte[] BufSigPic;
	
	public byte[] QdData ;
	public byte[] BufQdKey =
	{ 0x77, 0x45, 0x37, 0x5e, 0x33, 0x69, 0x6d, 0x67, 0x23, 0x69, 0x29, 0x25, 0x68, 0x31, 0x32, 0x5d };
	public byte[] DwQdVerion_Byte = {0x02,0x04,0x04,0x04};
	public byte[] QdSufFix = { 0x68 };
	public byte[] QdPreFix = { 0x3E };
	public short CQdProtocolVer = 0x0063;
	public byte[] CQdProtocolVer_Byte = {0x00,0x63};
	public long DwQdVerion = 0x02040404;
	public short WQdCsCmdNo = 0x0004;
	public byte[] WQdCsCmdNo_Byte = {0x00,0x04};

	public byte[] CQdCcSubNo = {0x00};
	
	
	public byte[] COsType = {0x03};
	
	public byte[] BIsWow64 = {0x01};
	
	
	public byte[] DwDrvVersionInfo = {0x01,0x02};
	
	public byte[] BufVersionTsSafeEditDat = Util.str_to_byte("07df000a000c0001");

	/// <summary>
	///     QScanEngine.dll的"文件版本"
	/// </summary>
	public byte[] BufVersionQScanEngineDll = { 0x00, 0x04, 0x00, 0x03, 0x00, 0x04, 0x20, 0x5c };
	public byte[] BufTgtGtKey ;
	public byte[] BufTgt ;
	public byte[] Buf16BytesGtKeySt ;
	public byte[] BufServiceTicket ;
	public byte[] Buf16BytesGtKeyStHttp ;
	public byte[] BufServiceTicketHttp ;
	public byte[] BufGtKeyTgtPwd ;
	public byte[] BufSessionKey ;
	public byte[] BufSigSession ;
	public byte[] BufPwdForConn ;
	public byte[] ClientKey ;
	
	public byte[] SessionKey ;
	
	
}
