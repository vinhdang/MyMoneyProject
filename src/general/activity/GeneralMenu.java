package general.activity;

import java.util.LinkedList;
import plan.activity.ManagePlan;
import report.activity.ManageReport;
import setting.activity.ManageSetting;
import synchronize.activity.Synchronize;
import tool.activity.ManageTool;
import view.activity.ManageView;
import category.activity.ManageCategory;
import main.activity.About;
import main.activity.R;
import main.activity.Upgrade;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GeneralMenu extends Activity {
	private GridView gv_menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_general);
		
		gv_menu = (GridView)findViewById(R.id.gv_generalMenu);
		
		/***/
		gv_menu.setAdapter(new ImageAdapter(this));
		gv_menu.setOnItemClickListener(handleClick);
	}
	
	/***/
	OnItemClickListener handleClick = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			Intent intent;
			switch (pos)
			{
//			case 0:
//			{//Account
//				intent = new Intent(GeneralMenu.this,ManageAccount.class);
//				Toast.makeText(getApplicationContext(), "Start Manage Account ...", Toast.LENGTH_SHORT).show();
//				GeneralMenu.this.startActivity(intent);
//			}break;
//			case 1: //Transaction
//			{
//				intent = new Intent(GeneralMenu.this,ManageTransaction.class);
//				Toast.makeText(getApplicationContext(), "Start Manage Transaction ...", Toast.LENGTH_SHORT).show();
//				GeneralMenu.this.startActivity(intent);
//			}break;
			
//			case 3: //Bill
//			{
//				intent = new Intent(GeneralMenu.this,ManageBillReminder.class);
//				Toast.makeText(getApplicationContext(), "Start Manage Transaction ...", Toast.LENGTH_SHORT).show();
//				GeneralMenu.this.startActivity(intent);
//			}break;
//			case 4: //Report
//				{
//					intent = new Intent(GeneralMenu.this,ManageReport.class);
//					Toast.makeText(getApplicationContext(), "Start Manage Report ...", Toast.LENGTH_SHORT).show();
//					GeneralMenu.this.startActivity(intent);
//				}break;
			case 0: //Category
			{
				intent = new Intent(GeneralMenu.this,ManageCategory.class);
				Toast.makeText(getApplicationContext(), "Start Manage Category ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
		/*	case 5: //Transfer
			{
				intent = new Intent(General.this,ManageAccount.class);
				Toast.makeText(getApplicationContext(), "Start Manage Transfer ...", Toast.LENGTH_SHORT).show();
				General.this.startActivity(intent);
			}break; */
			
			case 1: //View
			{
				intent = new Intent(GeneralMenu.this,ManageView.class);
				Toast.makeText(getApplicationContext(), "Start Manage View ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
			case 2: //Setting
			{
				intent = new Intent(GeneralMenu.this,ManageSetting.class);
				Toast.makeText(getApplicationContext(), "Start Manage Setting ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
			case 3: //Plan
			{
				intent = new Intent(GeneralMenu.this,ManagePlan.class);
				Toast.makeText(getApplicationContext(), "Start Manage Plan ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
//		
			case 4: //Tools
			{
				intent = new Intent(GeneralMenu.this,ManageTool.class);
				Toast.makeText(getApplicationContext(), "Start Manage Tool ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
			case 5: //About
			{
				intent = new Intent(GeneralMenu.this,About.class);
				Toast.makeText(getApplicationContext(), "Start About ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
			case 6: //Upgrade
			{
				intent = new Intent(GeneralMenu.this,Upgrade.class);
				Toast.makeText(getApplicationContext(), "Start Upgrade ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
			case 7: //Synchronize
			{
				intent = new Intent(GeneralMenu.this,Synchronize.class);
				Toast.makeText(getApplicationContext(), "Start Synchronize ...", Toast.LENGTH_SHORT).show();
				GeneralMenu.this.startActivity(intent);
			}break;
			}
		}
	};

	
////////////////////////////////////////////////////////////////////////////////////////////
	class ItemData
	{
		public int icon;
		public String caption;
		public ItemData(int ico, String cap){icon = ico; caption = cap;}
	}
	class ImageAdapter extends BaseAdapter
	{
		
		public ImageAdapter(Context c)
		{
			context = c;
			
			//data.add(new ItemData(R.drawable.account,"Account"));
			//data.add(new ItemData(R.drawable.transaction,"Transaction"));
			data.add(new ItemData(R.drawable.category_48,"Category"));
			data.add(new ItemData(R.drawable.view_48,"View"));
			data.add(new ItemData(R.drawable.setting_48,"Setting"));
			data.add(new ItemData(R.drawable.plan_48,"Plan"));
			//data.add(new ItemData(R.drawable.bill,"Bill"));
//			data.add(new ItemData(R.drawable.report_48,"Report"));
			data.add(new ItemData(R.drawable.tools_48,"Tools"));
			data.add(new ItemData(R.drawable.about_48,"About"));
			data.add(new ItemData(R.drawable.upgrade48,"Upgrade"));
			data.add(new ItemData(R.drawable.sync_48,"Synchronize"));
		}
		@SuppressWarnings("unused")
		private Context context;
		public LinkedList<ItemData> data = new LinkedList<ItemData>();
		
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(final int pos, View convertView, ViewGroup arg2) {
			View v;
			if (convertView==null)
			{
				LayoutInflater li = getLayoutInflater();
				v = li.inflate(R.layout.icon, null);
				
				ImageView iv = (ImageView)v.findViewById(R.id.iv_icon_image);
				iv.setImageResource(data.get(pos).icon);
				
				TextView tv = (TextView)v.findViewById(R.id.tv_icon_text);
				tv.setText(data.get(pos).caption);

			}
			else
			{
				v = convertView;
			}
			return v;
		}
		
	}
}
