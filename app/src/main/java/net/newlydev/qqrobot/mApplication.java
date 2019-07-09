package net.newlydev.qqrobot;
import android.app.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class mApplication extends Application
{

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		try
		{
			Util.trustAllHttpsCertificates();
		}
		catch (Exception e)
		{}
	}
	
}
