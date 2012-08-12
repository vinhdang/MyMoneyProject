package tool.activity;

import java.util.ArrayList;
import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToolImport extends Activity {
	private EditText edt_filterName;
	private ListView lv_file;
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_import);
		Publics.list_File = new ArrayList<String>();
		Publics.list_File.add("BK07072012");
		Publics.list_File.add("BK28062012");
		Publics.list_File.add("BK25062012");
		Publics.list_File.add("BK22062012");
		Publics.list_File.add("BK19062012");
		
		/***/
		lv_file = (ListView)findViewById(R.id.lv_toolImportFile);
		edt_filterName = (EditText)findViewById(R.id.edt_toolImportFilterName);
		adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_list_item_1, Publics.list_File);
		
		/***/
		lv_file.setAdapter(adapter);
		lv_file.setOnItemClickListener(handleClick);
		lv_file.setTextFilterEnabled(true);
		lv_file.getContext();
		edt_filterName.addTextChangedListener(filterTextWatcher);
		
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/***/
	OnItemClickListener handleClick = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			registerForContextMenu(lv_file);
			arg1.showContextMenu();		
		}
	};
	
	/***/
	 @Override  
	    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		 	if(v.getId() == R.id.lv_toolImportFile)
		 	{
		 		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		 		super.onCreateContextMenu(menu, v, menuInfo);
		 		 menu.setHeaderTitle(Publics.list_File.get((info.position)));  
		 		super.onCreateContextMenu(menu, v, menuInfo);     
		        menu.add(0, v.getId(), 0, "Import");
		        menu.add(0, v.getId(), 0, "Delete");
		 	}
	    }  
	 
	 @Override  
	    public boolean onContextItemSelected(MenuItem item) { 
		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		 final String tmp = Publics.list_File.get((info.position));
	        if(item.getTitle()=="Import")
	        {
	        	//show message box
	        	AlertDialog.Builder builder = new AlertDialog.Builder(ToolImport.this);
				builder.setTitle("Warning!!!")
				 .setMessage("Are you sure to import???")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener()
				 {
					public void onClick(DialogInterface dialog, int which)
					{
						
							try{
								Toast.makeText(getApplicationContext(), "Import file ....", Toast.LENGTH_SHORT).show();
							}catch(Exception ex)
							{
								ex.printStackTrace();
							}
					}
				 })
				 .setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
				 {
				 	public void onClick(DialogInterface dialog, int id) {
				 		dialog.cancel();
				 	}
				 });
				AlertDialog alert = builder.create();
				alert.show();
	        }  
	        else 
	        {
	        	//show message box
	        	AlertDialog.Builder builder = new AlertDialog.Builder(ToolImport.this);
				builder.setTitle("Warning!!!")
				 .setMessage("Are you sure???")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener()
				 {
					public void onClick(DialogInterface dialog, int which)
					{
						
							try{
								//Delete item.
								Publics.list_File.remove(tmp);
								adapter.notifyDataSetChanged();
							}catch(Exception ex)
							{
								ex.printStackTrace();
							}
					}
				 })
				 .setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
				 {
				 	public void onClick(DialogInterface dialog, int id) {
				 		dialog.cancel();
				 	}
				 });
				AlertDialog alert = builder.create();
				alert.show();
	        }  
	    return true;  
	    }
	 
	 /***/
	 private TextWatcher filterTextWatcher = new TextWatcher() {

		    public void afterTextChanged(Editable s) {
		    	if(s.length() == 0)
		    	{
		    		 adapter = new ArrayAdapter<String>(getApplicationContext(), 
		    				 android.R.layout.simple_list_item_1, Publics.list_File);
		             lv_file.setAdapter(adapter);
		    	}
		    }

		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {
		    }

		    public void onTextChanged(CharSequence s, int start, int before,
		            int count) {
		        if(s!= "" || count != 0)
		        {
		        	adapter.getFilter().filter(s);
		        }
		    }
		};
}
