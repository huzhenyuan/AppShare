package com.zhenyuan.appshare;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhenyuan.appshare.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";
	private ListView listView;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		listView = (ListView) this.findViewById(R.id.listView1);
		list = new ArrayList<Map<String, Object>>();
		List<PackageInfo> appListInfo = this.getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo p : appListInfo) {
			if (!p.applicationInfo.sourceDir.startsWith("/data/app/")) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Drawable icon = null;
			String appName = "";
			try {
				appName = this.getPackageManager()
						.getApplicationLabel(p.applicationInfo).toString();
				icon = this.getPackageManager().getApplicationIcon(
						p.applicationInfo.packageName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("name", appName);
			map.put("package", p.applicationInfo.packageName);
			map.put("sourceDir", p.applicationInfo.sourceDir);
			map.put("icon", icon);
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.app_list_item, new String[] { "name", "icon" },
				new int[] { R.id.tv_name, R.id.iv_icon });
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Drawable) {
					ImageView iv = (ImageView) view;
					iv.setImageDrawable((Drawable) data);
					return true;
				} else
					return false;
			}
		});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (list.get(position).get("sourceDir") != null) {
					File f = new File(list.get(position).get("sourceDir")
							.toString());
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_SEND);
					intent.setType("*/*");
					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
					startActivity(intent);
				}
			}
		});
	}

}