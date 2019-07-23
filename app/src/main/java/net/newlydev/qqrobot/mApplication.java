package net.newlydev.qqrobot;
import android.app.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import android.content.Context;

public class mApplication extends Application
{
	private static mApplication mApp;

	public static Context getContext()
	{
		return mApp.getApplicationContext();
	}
	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		mApp=this;
		try
		{
			Util.trustAllHttpsCertificates();
		}
		catch (Exception e)
		{}
	}
	
}
