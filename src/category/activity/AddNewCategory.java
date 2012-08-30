package category.activity;
import publics.Publics;
import main.activity.R;
import model.category.Category;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddNewCategory extends Activity {
	private Spinner spn_categoryType;
	private Button btn_categorySave;
	private EditText edt_categoryName;
	private EditText edt_categoryNote;
	private Category cate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_category);
		
		//create on click for 5 top button
		Publics.topFunction(this);
		cate = new Category("", "", "");
		
		/**Get id and process*/
		btn_categorySave = (Button)findViewById(R.id.btn_addCategorySave);
		spn_categoryType = (Spinner)findViewById(R.id.spn_addCategoryType);
		edt_categoryName = (EditText)findViewById(R.id.edt_addCategoryName);
		edt_categoryNote = (EditText)findViewById(R.id.edt_addCategoryDescription);
		ArrayAdapter<String> adapterCategoryType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Publics.listCategoryType);
				
		/**Set function for component*/
		btn_categorySave.setOnClickListener(handleSave);
		spn_categoryType.setAdapter(adapterCategoryType);
	}
	
	/**Event of click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			cate.setCategoryName(edt_categoryName.getText().toString());
			cate.setCategoryDescription(edt_categoryNote.getText().toString());
			cate.setCategoryType(spn_categoryType.getSelectedItem().toString());
			Publics.list_Category.add(cate);
			Intent i = new Intent(getApplicationContext(), ManageCategory.class);
			Toast.makeText(getApplicationContext(), "Save ....", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
}
