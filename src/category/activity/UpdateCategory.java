package category.activity;

import publics.Publics;
import main.activity.R;
import model.category.Category;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateCategory extends Activity {
	private Spinner spn_categoryType;
	private Button btn_categorySave;
	private EditText edt_categoryName;
	private EditText edt_categoryNote;
	private Category cate;
	private int pos = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_category);

		//create on click for 5 top button
		Publics.topFunction(this);
		/**Get id and process*/
		btn_categorySave = (Button)findViewById(R.id.btn_updateCategorySave);
		spn_categoryType = (Spinner)findViewById(R.id.spn_updateCategoryType);
		edt_categoryName = (EditText)findViewById(R.id.edt_updateCategoryName);
		edt_categoryNote = (EditText)findViewById(R.id.edt_updateCategoryDescription);
		ArrayAdapter<String> adapterCategoryType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Publics.listCategoryType);
		spn_categoryType.setAdapter(adapterCategoryType);
		
		Intent i = getIntent();
		if(i != null)
		{
			Bundle getData = i.getExtras();
			pos = getData.getInt("POS");
			cate = Publics.list_Category.get(pos);
			edt_categoryName.setText(cate.getCategoryName());
			edt_categoryNote.setText(cate.getCategoryDescription());
			int spinnerPosition = adapterCategoryType.getPosition(cate.getCategoryType());
			spn_categoryType.setSelection(spinnerPosition);	
		}
		
		/**Set function for component*/
		btn_categorySave.setOnClickListener(handleSave);
		
	}
	
	/**Event of click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			cate.setCategoryName(edt_categoryName.getText().toString());
			cate.setCategoryDescription(edt_categoryNote.getText().toString());
			cate.setCategoryType(spn_categoryType.getSelectedItem().toString());
			Publics.list_Category.set(pos, cate);
			//Back to Listview
			Intent i = new Intent(getApplicationContext(), ManageCategory.class);
			Toast.makeText(getApplicationContext(), "Save Update....", Toast.LENGTH_SHORT).show();
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
