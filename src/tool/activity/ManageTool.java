package tool.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageTool extends Activity {
	GridView gv_menuTool;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_menu);
		
		/**Get id and process*/
		gv_menuTool = (GridView)findViewById(R.id.gv_toolMenu);
		gv_menuTool.setAdapter(new GridViewAdapter(this));
		
		/***/
		gv_menuTool.setOnItemClickListener(handleClick);
	}
	
	/**Event of click icon*/
	OnItemClickListener handleClick = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			switch (pos) {
			case 0: // Handle event Click icon Backup
			{
				Intent i = new Intent(getApplicationContext(), ToolBackup.class);
				startActivity(i);
			}break;
			case 3: // Handle event Click icon Export
			{
				Intent i = new Intent(getApplicationContext(), ToolExport.class);
				startActivity(i);
			}break;
			case 4: // Handle event Click icon Exchange
			{
				Intent i = new Intent(getApplicationContext(), ToolExchange.class);
				startActivity(i);
			}break;
			case 2: // Handle event Click icon Send file
			{
				Intent i = new Intent(getApplicationContext(), SendFileExport.class);
				startActivity(i);
			}break;
			case 1: // Handle event Click icon Restore
			{
				Intent i = new Intent(getApplicationContext(), ToolRestore.class);
				startActivity(i);
			}break;
			default: break;
			}
		}
	};
	
	public class GridViewAdapter extends BaseAdapter
	{
		private Context mContext;
		
		public GridViewAdapter(Context c)
		{
			this.mContext = c;		
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int pos, View convertView, ViewGroup parent) {
			View v = convertView;
			if(v == null)
			{
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = new View(mContext);
				v = inflater.inflate(R.layout.item_gridview_tool, null);	
				TextView tv = (TextView)v.findViewById(R.id.tv_toolItem);
				tv.setText(Publics.listGridMenuTool[pos]);
				ImageView iv = (ImageView)v.findViewById(R.id.iv_toolItem);
				String mobile = Publics.listGridMenuTool[pos];
				if (mobile.equals("Backup")) {
					iv.setImageResource(R.drawable.backup);
				} else if (mobile.equals("Export")) {
					iv.setImageResource(R.drawable.export);
				} else if (mobile.equals("Exchange")) {
					iv.setImageResource(R.drawable.exchange);
				} else if (mobile.equals("Import")){
					iv.setImageResource(R.drawable.import_);
				} else
					iv.setImageResource(R.drawable.mailbox);
			}
			else
			{
				v= convertView;
			}
			return v;
		}
	
	}
	
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add("Exit");
			return super.onCreateOptionsMenu(menu);
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getTitle() == "Exit")//exit
			{
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			    finish();
			    System.exit(0);
			}
			return super.onOptionsItemSelected(item);
		}
}
