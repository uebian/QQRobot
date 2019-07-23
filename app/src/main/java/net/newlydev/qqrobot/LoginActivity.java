package net.newlydev.qqrobot;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class LoginActivity extends Activity 
{
	ServiceConnection conn;
	IBinder ibinder;
	Messenger loginhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		final ProgressDialog pb=new ProgressDialog(this);
		pb.setCancelable(false);
		pb.setMessage(getResources().getString(R.string.wait));
		Intent i=new Intent(LoginActivity.this,MainService.class);
		startService(i);
		loginhandler=new Messenger(new Handler(){
				@Override
				public void handleMessage(Message msg)
				{
					switch(msg.getData().getString("type"))
					{
						case "islogined":
							finish();
							startActivity(new Intent(LoginActivity.this,MainActivity.class));
							break;
						case "verifyCode":
							{
								AlertDialog.Builder ab=new AlertDialog.Builder(LoginActivity.this);
								LinearLayout ll=new LinearLayout(LoginActivity.this);
								ImageView iv=new ImageView(LoginActivity.this);
								InputStream verifyCodeStream = new ByteArrayInputStream(msg.getData().getByteArray("image"));
								iv.setImageBitmap( BitmapFactory.decodeStream(verifyCodeStream));
								ll.addView(iv);
								ll.setOrientation(LinearLayout.VERTICAL);
								final EditText et_verifyCode=new EditText(LoginActivity.this);
								et_verifyCode.setHint("如显示不完全请直接点击确定按钮加载剩余图像");
								ab.setView(ll);
								ll.addView(et_verifyCode);
								ab.setPositiveButton("确定", new DialogInterface.OnClickListener(){

										@Override
										public void onClick(DialogInterface p1, int p2)
										{
											Message msg=new Message();
											msg.replyTo=loginhandler;
											Bundle data=new Bundle();
											data.putString("type","verifyCode");
											data.putString("code",et_verifyCode.getText().toString());
											msg.setData(data);
											try
											{
												new Messenger(ibinder).send(msg);
											}
											catch (RemoteException e)
											{}
											// TODO: Implement this method
										}
									});
								ab.setCancelable(false);
								ab.show();
							}
							break;
						case "error":
							pb.dismiss();
							AlertDialog.Builder ab=new AlertDialog.Builder(LoginActivity.this);
							ab.setMessage(msg.getData().getString("info"));
							ab.show();
							break;
						case "ok":
							Toast.makeText(LoginActivity.this,"Welcome:"+msg.getData().getString("name"),Toast.LENGTH_SHORT).show();
							finish();
							startActivity(new Intent(LoginActivity.this,MainActivity.class));
							break;
					}
				}
			});
		final EditText et_qq=findViewById(R.id.et_qq);
		final EditText et_pwd=findViewById(R.id.et_pwd);
		Button btn_login=findViewById(R.id.btn_login);
		conn=new ServiceConnection(){

			@Override
			public void onServiceConnected(ComponentName p1, IBinder p2)
			{
				ibinder=p2;
				Message msg=new Message();
				msg.replyTo=loginhandler;
				Bundle data=new Bundle();
				data.putString("type","islogined");
				msg.setData(data);
				try
				{
					new Messenger(ibinder).send(msg);
				}
				catch (RemoteException e)
				{}
			}

			@Override
			public void onServiceDisconnected(ComponentName p1)
			{
				// TODO: Implement this method
			}
		};
		btn_login.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					pb.show();
					Message msg=new Message();
					msg.replyTo=loginhandler;
					Bundle data=new Bundle();
					data.putString("type","login");
					data.putLong("qq",Long.parseLong(et_qq.getText().toString()));
					data.putByteArray("pwd",Util.MD5(et_pwd.getText().toString()));
					msg.setData(data);
					try
					{
						new Messenger(ibinder).send(msg);
					}
					catch (RemoteException e)
					{}
				}
			});
		bindService(i,conn, Context.BIND_AUTO_CREATE);
    }

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unbindService(conn);
	}
	
}
