package net.newlydev.qqrobot.PCTIM.Package;

import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Message.*;
import net.newlydev.qqrobot.PCTIM.TLV.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;

public class ParseRecivePackage
{
	public long Friend_Message_QQ;
	public byte[] Friend_Message_TIME;
	public byte[] Header = null;
	public int PackageLength = 0;
	public byte[] Version = null;
	public byte[] Command = null;
	public byte[] Sequence = null;
	public byte[] QQ = null;
	public byte[] empty = null;
	public byte[] body_encrypted = null;
	public byte[] body_decrypted = null;
	public byte[] tea_key=null;
	private Collection<TLV> tlvs = null;
	private byte[] _body = null;
	private QQUser _user = null;
	public byte[] Message_To_Respone = null;

	public byte Status=0x1;

	public byte VerifyCommand=0x1;

	public ParseRecivePackage(byte[] body, byte[] key, QQUser user)
	{
		this.PackageLength = body.length;
		this._body = body;
		ByteFactory bytefactory = new ByteFactory(body);
		this.tea_key = key;
		this._user = user;
		bytefactory.readBytes(1);
		this.Version = bytefactory.readBytes(2);
		this.Command = bytefactory.readBytes(2);
		this.Sequence = bytefactory.readBytes(2);
		this.QQ = bytefactory.readBytes(4);
		this.empty = bytefactory.readBytes(3);
		this.body_encrypted = bytefactory.readBytes(bytefactory.data.length - bytefactory.position - 1);

	}
	public void parse001d()
	{
		this.decrypt_body();
		ByteFactory factory = new ByteFactory(this.body_decrypted);
		factory.readBytes(4);
		this._user.userskey = new String(factory.readBytes(10));
	}
	
	public void parse00ba()
	{
		this.decrypt_body();
		ByteFactory factory = new ByteFactory(this.body_decrypted);
		byte VerifyType = factory.readBytes(1)[0];
		factory.readint();
		this.Status = factory.readBytes(1)[0];
		factory.readBytes(4);
		_user.TXProtocol.BufSigPic = factory.readBytesbylength();
		if (VerifyType == 0x13)
		{
			byte[] VerifyCode = factory.readBytesbylength();

			this.VerifyCommand = factory.readBytes(1)[0];
			if (this.VerifyCommand == 0x00)
			{
				this.VerifyCommand = factory.readBytes(1)[0];
			}
			else
			{
				factory.readBytes(1);
			}

			if (_user.QQPacket00BaVerifyCode.length == 0 || _user.QQPacket00BaVerifyCode == null)
			{
				_user.QQPacket00BaVerifyCode = VerifyCode;
			}
			else
			{
				ByteBuilder resultArr = new ByteBuilder();
				resultArr.writebytes(_user.QQPacket00BaVerifyCode);
				resultArr.writebytes(VerifyCode);
				_user.QQPacket00BaVerifyCode = resultArr.getdata();
			}

			_user.TXProtocol.PngToken = factory.readBytesbylength();
			factory.readBytesbylength();
		}
		else if (VerifyType == 0x14)
		{




		}



	}

	public void decrypt_body()
	{
		Crypter crypter = new Crypter();
		this.body_decrypted = crypter.decrypt(this.body_encrypted, this.tea_key);
		this.Header = Util.subByte(this.body_decrypted, 0, 1);

	}

	public void parse_tlv()
	{
		tlvs = TLV.ParseTlv(Util.subByte(this.body_decrypted, 1, this.body_decrypted.length - 1));
		for (TLV tlv: tlvs)
		{
			ParseTlvFactory.parsetvl(tlv, _user);
		}

	}

	public void parse0836()
	{
		this.decrypt_body();

		int result = this.Header[0];
		if (result == -5 || result == 0 || result == 1 || result == 51 ||
			result == 52 || result == 63 || result == 248 ||
			result == 249 || result == 250 || result == 251 ||
			result == 254 || result == 15 || result == 255)
		{

			this.parse_tlv();
		}
		else
		{

			Crypter crypter = new Crypter();
			this.body_decrypted = crypter.decrypt(this.body_decrypted, _user.TXProtocol.BufTgtgtKey);
			this.Header = Util.subByte(this.body_decrypted, 0, 1);
			this.parse_tlv();
		}



	}
	public QQMessage parse0017()
	{
		QQMessage qqmessage = new QQMessage();
		try
		{
			this.decrypt_body();
			ByteFactory bytefactory = new ByteFactory(this.body_decrypted);
			this.Message_To_Respone = Util.subByte(this.body_decrypted, 0, 16);
			long Target_uin = bytefactory.readlong();
			qqmessage.Self_uin = bytefactory.readlong();
			bytefactory.readBytes(10);
			int type = bytefactory.readint();
			bytefactory.readBytes(2);
			bytefactory.readBytesbylength();
			qqmessage.Group_uin = bytefactory.readlong();
			byte[] flag = bytefactory.readBytes(1);
			ByteFactory message_datafactory = new ByteFactory(bytefactory.readrestBytes());
			switch (type)
			{
				case 0x52 : // 群消息、被拉进/踢出群
					{
						if (flag[0] == 0x01)
						{
							qqmessage.Message_Type = 0;//群消息

							qqmessage.Sender_Uin = message_datafactory.readlong();
							//发消息人的QQ

							qqmessage.MessageIndex = message_datafactory.readBytes(4); //姑且叫消息索引吧
							qqmessage.Reciece_Message_Time = message_datafactory.readlong(); //接收时间  
							message_datafactory.readBytes(24);
							qqmessage.Send_Message_Time = message_datafactory.readlong() * 1000; //发送时间 
							qqmessage.Message_Id = message_datafactory.readlong(); //id
							message_datafactory.readBytes(8);

							qqmessage.Message_Font = message_datafactory.readStringbylength();//字体

							message_datafactory.readBytes(2);
							byte[] rich_data = message_datafactory.readrestBytes();
							Util.parseRichText(qqmessage, rich_data);

						}
						break;
					}
				case 0x21:
				case 0x22:
					{
						message_datafactory.readBytes(5);
						qqmessage.Sender_Uin = message_datafactory.readlong();
						Util.log("用户"+qqmessage.Sender_Uin+"变更："+Util.byte2HexString(this._body));
						break;
					}
				case 0x2C:
					{
						break;
					}
				default:
					{
						break;
					}
			}
			return qqmessage;
		}
		catch (Exception e)
		{
			Util.log("解析0017错误,Exception: " + e.getMessage() + " 错误包: " + Util.byte2HexString(this._body));
			return null;
		}
	}
	public PictureKeyStore parse0388()
	{
		PictureKeyStore keystore = new PictureKeyStore();
		byte[] _ukey = null;
		this.decrypt_body();
		if (this.PackageLength == 239)
		{
			ByteFactory factory = new ByteFactory(this.body_decrypted);
			byte last1 = 0;
			byte last2 = 0;
			byte last = 0;
			while (factory.position < factory.data.length)
			{
				last2 = last1;
				last1 = last;
				last = factory.readBytes(1)[0];

				if (last == (byte)0x01 && last1 == (byte)0x80 && last2 == (byte) 0x42)
				{
					_ukey = factory.readBytes(128);
					break;
				}
			}
			keystore.uploaded = false;
		}
		else
		{
			keystore.uploaded = true;
		}
		keystore.ukey = _ukey;
		return keystore;
	} 

	public QQMessage parse00ce()
	{
		QQMessage qqmessage = new QQMessage();
		try
		{
			this.decrypt_body();
			ByteFactory bytefactory = new ByteFactory(this.body_decrypted);
			this.Message_To_Respone = Util.subByte(this.body_decrypted, 0, 16);
			qqmessage.Message_Type = 1;
			qqmessage.Sender_Uin = bytefactory.readlong();
			this.Friend_Message_QQ = qqmessage.Sender_Uin;
			qqmessage.Self_uin = bytefactory.readlong();//自己的QQ
			bytefactory.readBytes(10);
			bytefactory.readBytes(2);
			bytefactory.readint();
			bytefactory.readBytesbylength(); 
			bytefactory.readint(); //消息来源QQ的版本号
			bytefactory.readBytes(4); //FromQQ
			bytefactory.readBytes(4); //自己的QQ
			bytefactory.readBytes(20);
			qqmessage.Send_Message_Time = bytefactory.readlong() * 1000;
			bytefactory.readint(); //00
			this.Friend_Message_TIME =  bytefactory.readBytes(4); //MessageDateTime
            bytefactory.readBytes(5); //00
            bytefactory.readBytes(3);
            bytefactory.readBytes(5); //00
            bytefactory.readBytes(4); //MessageDateTime
            qqmessage.Message_Id = bytefactory.readlong(); //id
            bytefactory.readBytes(8);
            qqmessage.Message_Font = bytefactory.readStringbylength();
            bytefactory.readBytes(1);
            bytefactory.readBytes(1);
            Util.parseRichText(qqmessage, bytefactory.readrestBytes());
			return qqmessage;
		}
		catch (Exception e)
		{
			Util.log("解析00ce错误,Exception: " + e.getMessage() + " 错误包: " + Util.byte2HexString(this._body));
			return null;
		}

	}

}
