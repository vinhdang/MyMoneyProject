package category.activity;

import publics.Publics;
import main.activity.R;
import model.category.Category;
import model.category.CategoryAdapter;
import model.category.CategoryDataSoure;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ManageCategory extends Activity{
	private ListView lv_categoryAll;
	private Button btn_categoryNew;
	private CategoryAdapter cateAdapter;
	private CategoryDataSoure dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_category);
		dataSource = new CategoryDataSoure(this);
		
		//create on click for 5 top button
		Publics.topFunction(this);
				
		/**Get id and process*/
		lv_categoryAll = (ListView)findViewById(R.id.lv_categoryAll);
		btn_categoryNew = (Button)findViewById(R.id.btn_newCategory);
		cateAdapter = new CategoryAdapter(getApplicationContext(), Publics.list_Category);
		
		/**Set function for component*/
		btn_categoryNew.setOnClickListener(handleAddNew);
		lv_categoryAll.setAdapter(cateAdapter);
		lv_categoryAll.setOnItemClickListener(handleClick);
		lv_categoryAll.setOnItemLongClickListener(handleLongClick);
	}
	
	/**Event click New button */
	OnClickListener handleAddNew = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), AddNewCategory.class);
			Toast.makeText(getApplicationContext(), "Start Add New .....", Toast.LENGTH_SHORT).show();
			startActivity(i);			
		}
	};
	
	/**Event click on item of list view*/
	
	 @Override  
	    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		 	if(v.getId() == R.id.lv_categoryAll)
		 	{
		 		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		 		super.onCreateContextMenu(menu, v, menuInfo);
		 		 menu.setHeaderTitle(Publics.list_Category.get(info.position).categoryName);  
		 		super.onCreateContextMenu(menu, v, menuInfo);     
		        menu.add(0, v.getId(), 0, "Update");
		        menu.add(0, v.getId(), 0, "Delete");
		 	}
	    }  
	 
	 @Override  
	    public boolean onContextItemSelected(MenuItem item) { 
		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		 final Category c = Publics.list_Category.get(info.position);
	        if(item.getTitle()=="Update")
	        {
	        	Intent i = new Intent(getApplicationContext(), UpdateCategory.class);
	        	Bundle data = new Bundle();
	        	data.putInt("POS", info.position);
	        	i.putExtras(data);
	        	startActivity(i);
	        }  
	        else 
	        {
	        	//show message box
	        	AlertDialog.Builder builder = new AlertDialog.Builder(ManageCategory.this);
				builder.setTitle("Delete.......")
				 .setMessage("Are you sure???")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener()
				 {
					public void onClick(DialogInterface dialog, int which)
					{
						
							try{
								//Delete item.
								Publics.list_Category.remove(c);
								dataSource.open();
								dataSource.deleteCategory(c);
								dataSource.close();
								cateAdapter.notifyDataSetChanged();
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
	 
	 OnItemClickListener handleClick = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
//			registerForContextMenu(lv_categoryAll);
//			arg1.showContextMenu();
			Intent i = new Intent(getApplicationContext(), ViewDetailCategory.class);
        	Bundle data = new Bundle();
        	data.putInt("POS", arg2);
        	i.putExtras(data);
        	startActivity(i);
		}
	};
	
	OnItemLongClickListener handleLongClick = new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			registerForContextMenu(lv_categoryAll);
			arg1.showContextMenu();
			return true;
		}
	};
}
