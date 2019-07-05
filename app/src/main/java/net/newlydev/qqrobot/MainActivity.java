package net.newlydev.qqrobot;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.widget.*;
import dalvik.system.*;
import java.io.*;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;
import net.newlydev.qqrobot.PCTIM.sdk.*;
import android.widget.AdapterView.*;
import android.view.*;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		ListView lv=new ListView(this);
		setContentView(lv);
		final ArrayList<String> pluginlist=new ArrayList<String>();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		final File pluginlistfile=new File(getDataDir(), "pluginlist.cfg");
		ArrayList<String> activiedplugin=new ArrayList<String>();
		try
		{
			if (!pluginlistfile.exists())
			{
				pluginlistfile.createNewFile();
			}
			DataInputStream fr=new DataInputStream(new FileInputStream(pluginlistfile));
			String s;
			while ((s = fr.readLine()) != null)
			{
				activiedplugin.add(s);
			}
			fr.close();
		}
		catch (Exception e)
		{}
		PackageManager packageManager= getPackageManager();
        List<PackageInfo> list=packageManager.getInstalledPackages(0);
        for (PackageInfo p:list)
		{
			if (getPackageManager().checkPermission("net.newlydev.qqrobot.permission.plugin", p.applicationInfo.packageName) == PackageManager.PERMISSION_GRANTED)
			{
				boolean isenable=false;
				for (String eplugin:activiedplugin)
				{
					if (eplugin.equals(p.applicationInfo.packageName))
					{
						isenable = true;
						break;
					}
				}
				pluginlist.add(p.applicationInfo.packageName + "(" + (isenable ?"enable": "disable") + ")");
			}
        }
		final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pluginlist);
		lv.setAdapter(aa);
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					if (pluginlist.get(p3).endsWith("(disable)"))
					{
						pluginlist.set(p3, pluginlist.get(p3).substring(0, pluginlist.get(p3).length() - 9) + "(enable)");
					}
					else
					{
						pluginlist.set(p3, pluginlist.get(p3).substring(0, pluginlist.get(p3).length() - 8) + "(disable)");
					}
					try
					{
						FileWriter fw=new FileWriter(pluginlistfile);
						for (String tmp:pluginlist)
						{
							if (tmp.endsWith("(enable)"))
							{
								fw.write(pluginlist.get(p3).substring(0, pluginlist.get(p3).length() - 8) + "\n");
							}
						}
						fw.close();
					}
					catch (IOException e)
					{}
					aa.notifyDataSetChanged();
					// TODO: Implement this method
				}
			});
	}

}
