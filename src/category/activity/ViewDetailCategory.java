package category.activity;

import publics.Publics;
import main.activity.R;
import model.category.Category;
import model.category.CategoryDataSoure;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDetailCategory extends Activity {
	private Button btn_categoryUpdate;
	private Button btn_categoryDelete;
	private TextView tv_categoryName;
	private TextView tv_categoryType;
	private TextView tv_categoryDescription;
	private TextView tv_categoryItemName;
	private Category cate;
	private int pos = 0;
	private CategoryDataSoure dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_detail_category);
		dataSource = new CategoryDataSoure(this);
		
		//create on click for 5 top button
		Publics.topFunction(this);
		
		/**Get id and process*/
		btn_categoryDelete = (Button)findViewById(R.id.btn_viewCategoryDelete);
		btn_categoryUpdate = (Button)findViewById(R.id.btn_viewCategoryUpdate);
		tv_categoryName = (TextView)findViewById(R.id.tv_viewCategoryName);
		tv_categoryType = (TextView)findViewById(R.id.tv_viewCategoryType);
		tv_categoryDescription = (TextView)findViewById(R.id.tv_viewCategoryDescription);
		tv_categoryItemName = (TextView)findViewById(R.id.tv_viewCategoryDetail);
		
		Intent intent = getIntent();
		if(intent != null)
		{
			Bundle getData = intent.getExtras();
			pos = getData.getInt("POS");
			cate = Publics.list_Category.get(pos);
			tv_categoryItemName.setText(cate.getCategoryName());
			tv_categoryName.setText(cate.getCategoryName());
			tv_categoryType.setText(cate.getCategoryType());
			tv_categoryDescription.setText(cate.getCategoryDescription());
		}
		
		/**Set function for component*/
		btn_categoryDelete.setOnClickListener(handleDelete);
		btn_categoryUpdate.setOnClickListener(handleUpdate);
	}
	
	/**Event of click Delete*/
	OnClickListener handleDelete  = new OnClickListener() {
		
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Delete item .....", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewDetailCategory.this);
			builder.setTitle("Deleting....")
			 .setMessage("Are you sure???")
			 .setPositiveButton("OK", new DialogInterface.OnClickListener()
			 {
				public void onClick(DialogInterface dialog, int which)
				{
					
						try{
							Intent i = new Intent(getApplicationContext(), ManageCategory.class);
							Publics.list_Category.remove(pos);
							dataSource.open();
							dataSource.deleteCategory(cate);
							dataSource.close();
							startActivity(i);
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

	};
	
	/**Event of click Update*/
	OnClickListener handleUpdate = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), UpdateCategory.class);
			Toast.makeText(getApplicationContext(), "Update item .....", Toast.LENGTH_SHORT).show();
			Bundle data = new Bundle();
        	data.putInt("POS", pos);
        	i.putExtras(data);
			startActivity(i);
		}
	};
	
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
