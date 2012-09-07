package setting.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.setting.SettingDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity {
	private Button btn_settingSave;
	private EditText edt_cur;
	private EditText edt_new;
	private EditText edt_conf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pass);

		/**Get id and process*/
		btn_settingSave = (Button)findViewById(R.id.btn_settingSaveNewPass);
		edt_cur = (EditText)findViewById(R.id.edt_settingCurrentPass);
		edt_new = (EditText)findViewById(R.id.edt_settingNewPass);
		edt_conf = (EditText)findViewById(R.id.edt_settingConfirmPass);
		
		/**Set function for component*/
		btn_settingSave.setOnClickListener(handleSave);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/**Event of click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			String cur = edt_cur.getText().toString().trim();
			String newp = edt_new.getText().toString().trim();
			String conf = edt_conf.getText().toString().trim();
			if(Publics.Password.equals(cur))
			{
				if(newp.equals(conf))
				{
					Publics.list_Setting.get(Publics.Pass_num).setValue(newp);
					SettingDataSource ds = new SettingDataSource(getApplicationContext());
					try{
						ds.open();
						Publics.list_Setting.get(Publics.Pass_num).setValue("");	
						ds.updateSetting(Publics.list_Setting.get(Publics.Pass_num));
						ds.close();
						Intent i = new Intent(getApplicationContext(), ManageSetting.class);
						Toast.makeText(getApplicationContext(), "Changed Password.....", Toast.LENGTH_SHORT).show();
						setResult(RESULT_OK, i);
					}catch(Exception ex)
					{
						ex.printStackTrace();
						ds.close();
					}
				}
			}
			else
			{
				edt_conf.setText("");
				edt_cur.setText("");
				edt_new.setText("");
			}
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
