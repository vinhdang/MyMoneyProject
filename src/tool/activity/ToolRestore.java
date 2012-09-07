package tool.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class ToolRestore extends Activity {
	private EditText edt_filterName;
	private ListView lv_file;
	ArrayAdapter<String> adapter;
	private List<String> listFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_restore);
		listFile = new ArrayList<String>();
		
		/**Process*/
		lv_file = (ListView)findViewById(R.id.lv_toolFileRestore);
		edt_filterName = (EditText)findViewById(R.id.edt_toolFilterFile);
		getFile();
		adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_list_item_1, listFile);
		
		/**Set function*/
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
	
	/** Create menu*/
	 @Override  
	    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		 	if(v.getId() == R.id.lv_toolFileRestore)
		 	{
		 		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		 		super.onCreateContextMenu(menu, v, menuInfo);
		 		 menu.setHeaderTitle(listFile.get((info.position)));  
		 		super.onCreateContextMenu(menu, v, menuInfo);     
		        menu.add(0, v.getId(), 0, "Restore");
		        menu.add(0, v.getId(), 0, "Delete");
		 	}
	    }  
	 
	 
	 /**Process menu item selected*/
	 @Override  
	    public boolean onContextItemSelected(MenuItem item) { 
		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		 final String tmp = listFile.get((info.position));
	        if(item.getTitle()=="Restore")
	        {
	        	//show message box
	        	AlertDialog.Builder builder = new AlertDialog.Builder(ToolRestore.this);
				builder.setTitle("Warning!!!")
				 .setMessage("Are you sure to restore???")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener()
				 {
					public void onClick(DialogInterface dialog, int which)
					{
						
							try{
								Toast.makeText(getApplicationContext(), "Restoring file ....", Toast.LENGTH_SHORT).show();
								restore(tmp);
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
	        	AlertDialog.Builder builder = new AlertDialog.Builder(ToolRestore.this);
				builder.setTitle("Warning!!!")
				 .setMessage("Are you sure???")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener()
				 {
					public void onClick(DialogInterface dialog, int which)
					{
						
							try{
								//Delete item.
								File file = new File("/mnt/sdcard/MyMoney/" + tmp);
								file.delete();
								listFile.remove(tmp);
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
	 
	 /**filter text*/
	 private TextWatcher filterTextWatcher = new TextWatcher() {

		    public void afterTextChanged(Editable s) {
		    	if(s.length() == 0)
		    	{
		    		 adapter = new ArrayAdapter<String>(getApplicationContext(), 
		    				 android.R.layout.simple_list_item_1, listFile);
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
		
		/**restore database*/
		private void restore(String DATABASE_NAME)
		{
			OutputStream myOutput;			 
			try {
	 
				myOutput = new FileOutputStream("/data/data/main.activity/databases/Database.db");
	 	 
			    // Set the folder on the SDcard
			    File directory = new File("/mnt/sdcard/MyMoney/");
			    // Set the input file stream up:
	 
			    InputStream myInputs = new FileInputStream(directory.getPath()+ "/" + DATABASE_NAME);
	 
			    // Transfer bytes from the input file to the output file
			    byte[] buffer = new byte[1024];
			    int length;
			    while ((length = myInputs.read(buffer))>0)
			    {
			        myOutput.write(buffer, 0, length);
			    }
			    // Close and clear the streams
			    myOutput.flush();	 
			    myOutput.close();	 
			    myInputs.close();	
			    renameFile(DATABASE_NAME);
	 
			} catch (FileNotFoundException e) {
				Toast.makeText(getApplicationContext(), "Restore Unsuccesfull!", Toast.LENGTH_LONG).show(); 
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {	
				Toast.makeText(getApplicationContext(), "Restore Unsuccesfull!", Toast.LENGTH_LONG).show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(), "Restore Done Succesfully!", Toast.LENGTH_LONG).show();
		}
		
		/**Get file from sdcard*/
		 private void getFile()
		 {
			 File mfile=new File("/mnt/sdcard/MyMoney/");
			 File[] list=mfile.listFiles();

			 listFile.clear();
			 for(int i=0 ; i < mfile.listFiles().length; i++)
			 {
			     if(list[i].isFile())
			     {
			    	 String tmp = list[i].getName();
			    	 if(tmp.endsWith(".db"))
			    	 {
			    		 listFile.add(tmp);
			    	 }
			         Toast.makeText(getApplicationContext(), "files.."+list[i].getName(), Toast.LENGTH_SHORT).show();
			     }
			 }
		 }
		 
		 /**rename file*/
		 private boolean renameFile(String filename)
		 {
			 File from = new File("/data/data/main.activity/databases/", filename);
			 File to = new File("/data/data/main.activity/databases/", "Database.db");
			 try{
				 from.renameTo(to);
				 return true;
			 }catch(Exception ex)
			 {
				 ex.printStackTrace();
				 return false;
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
