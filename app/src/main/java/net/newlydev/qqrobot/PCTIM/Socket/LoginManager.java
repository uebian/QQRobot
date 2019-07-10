package net.newlydev.qqrobot.PCTIM.Socket;
import android.graphics.*;
import android.os.*;
import java.io.*;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.*;
import net.newlydev.qqrobot.PCTIM.Package.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class LoginManager
{
	String verifyCode="";
	private byte[] data = null;
	public Udpsocket socket = null;
	private QQUser _user;
	private Object lock;
	public LoginManager(QQUser user, Object lock)
	{
		this._user = user;
		this.lock = lock;
		user.TXProtocol.DwServerIP = "sz.tencent.com";
		socket = new Udpsocket(user);

	}

	public void setVerifyCode(String verifyCode)
	{
		this.verifyCode = verifyCode;
		synchronized(this){
			this.notify();
		}
	}

	public void relogin()
	{
		data = SendPackageFactory.get0825(_user);
		socket.sendMessage(data);
	}


	public boolean login(Messenger messenger)
	{

		try
		{
			data = SendPackageFactory.get0825(_user);
			socket.sendMessage(data);
			byte[] result = socket.receiveMessage();
			//System.out.println(Util.byte2HexString(result));
			ParseRecivePackage parsereceive = new ParseRecivePackage(result, _user.QQPacket0825Key, _user);
			parsereceive.decrypt_body();
			parsereceive.parse_tlv();
			while (parsereceive.Header[0] == -2)
			{
				Util.log("重定向到:" + _user.TXProtocol.DwRedirectIP);
				_user.TXProtocol.WRedirectCount += 1;
				_user.IsLoginRedirect = true;
				socket = new Udpsocket(_user);

				data = SendPackageFactory.get0825(_user);

				socket.sendMessage(data);

				result = socket.receiveMessage();
				parsereceive = new ParseRecivePackage(result, _user.QQPacket0825Key, _user);
				parsereceive.decrypt_body();
				parsereceive.parse_tlv();
			}

			Util.log("服务器连接成功,开始登陆");
			data = SendPackageFactory.get0836(_user, false);
			socket.sendMessage(data);
			result = socket.receiveMessage();
			parsereceive = new ParseRecivePackage(result, _user.TXProtocol.BufDhShareKey, _user);
			parsereceive.parse0836();
			if (parsereceive.Header[0] == 52)
			{
				Util.log("密码错误");
				Message msg=new Message();
				Bundle mdata=new Bundle();
				mdata.putString("type", "error");
				mdata.putString("info", "password wrong");
				msg.setData(mdata);
				messenger.send(msg);
				return false;
			}
			if (parsereceive.Header[0] == -5)
			{
				Util.log("需要验证码");
				while (parsereceive.Status == 0x1)
				{
					while (verifyCode.equals(""))
					{
						data = SendPackageFactory.get00ba(_user, "");
						socket.sendMessage(data);
						result = socket.receiveMessage();
						parsereceive = new ParseRecivePackage(result, _user.QQPacket00BaKey, _user);
						parsereceive.parse00ba();
						try
						{
							//InputStream verifyCodeStream = new ByteArrayInputStream(_user.QQPacket00BaVerifyCode);
							BitmapFactory.decodeByteArray(_user.QQPacket00BaVerifyCode, 0, _user.QQPacket00BaVerifyCode.length).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream("/sdcard/yzm.png"));
							Message msg=new Message();
							Bundle mdata=new Bundle();
							msg.setData(mdata);
							mdata.putString("type", "verifyCode");
							mdata.putByteArray("image", _user.QQPacket00BaVerifyCode);
							messenger.send(msg);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						synchronized (this)
						{
							wait();
						}
					}
					data = SendPackageFactory.get00ba(_user, verifyCode);
					socket.sendMessage(data);
					result = socket.receiveMessage();
					parsereceive = new ParseRecivePackage(result, _user.QQPacket00BaKey, _user);
					parsereceive.parse00ba();
					if (parsereceive.Status != 0x0)
					{
						return false;
					}
				}
			}	
			while (parsereceive.Header[0] != 0)
			{
				Util.log("二次登陆");
				data = SendPackageFactory.get0836(_user, true);
				socket.sendMessage(data);
				result = socket.receiveMessage();
				parsereceive = new ParseRecivePackage(result, _user.TXProtocol.BufDhShareKey, _user);
				parsereceive.parse0836();
				Thread.sleep(1000);
			}
			if (parsereceive.Header[0] == 0)
			{
				_user.islogined = true;
				_user.logintime = new Date().getTime();
				data = SendPackageFactory.get0828(_user);
				socket.sendMessage(data);
				result = socket.receiveMessage(); 
				parsereceive = new ParseRecivePackage(result, _user.TXProtocol.BufTgtGtKey, _user);
				parsereceive.decrypt_body();
				parsereceive.parse_tlv();
				data = SendPackageFactory.get00ec(_user, QQGlobal.Online);
				socket.sendMessage(data);
				result = socket.receiveMessage();
				parsereceive = new ParseRecivePackage(result, _user.TXProtocol.SessionKey, _user);
				parsereceive.decrypt_body();
				data = SendPackageFactory.get001d(_user);
				socket.sendMessage(data);
				result = socket.receiveMessage();
				parsereceive = new ParseRecivePackage(result, _user.TXProtocol.SessionKey, _user);
				parsereceive.parse001d();
				Thread.sleep(500);
				Util.getquncookie(_user);
				Util.log("成功获取用户信息: Nick: " + _user.NickName + " Age: " + _user.Age + " Sex: " + _user.Gender+" bkn:"+_user.bkn+" Cookie:"+_user.quncookie);
				return true;
			}
			else
			{
				return false;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
