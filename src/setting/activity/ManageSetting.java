package setting.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class ManageSetting extends Activity {
	private Button btn_settingSave;
	private Button btn_settingChangePass;
	private Spinner spn_settingLanguage;
	private Spinner spn_settingDateFormat;
	private CheckBox chk_settingPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_setting);
		//create function for 4 image button on bottom
		Publics.bottomFunction(this);
				
		/**Get id and process*/
		btn_settingSave = (Button)findViewById(R.id.btn_settingSave);
		btn_settingChangePass = (Button)findViewById(R.id.btn_settingChangePass);
		spn_settingLanguage = (Spinner)findViewById(R.id.spn_settingLanguage);
		spn_settingDateFormat = (Spinner)findViewById(R.id.spn_settingDateFormat);
		chk_settingPass = (CheckBox)findViewById(R.id.chk_settingProtectByPass);
		ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Publics.listLanguage);
		ArrayAdapter<String> adapterDateFormate = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Publics.listDateFormat);
		if(Publics.Protect != -1)
		{
			btn_settingChangePass.setVisibility(0);
		}
		else
		{
			btn_settingChangePass.setVisibility(0);
		}
		
		/**Set function for component*/
		btn_settingSave.setOnClickListener(handleSave);
		btn_settingChangePass.setOnClickListener(handleChangePass);
		spn_settingLanguage.setAdapter(adapterLanguage);
		spn_settingDateFormat.setAdapter(adapterDateFormate);
		chk_settingPass.setOnClickListener(handleCheck);
	}
	
	/**Event of click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			//Intent i = new Intent(getApplicationContext(), General.class);
			Toast.makeText(getApplicationContext(), "Save All Change .....", Toast.LENGTH_SHORT).show();
		}
	};
	
	/**Event of click ChangePass*/
	OnClickListener handleChangePass = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), ChangePassword.class);
			Toast.makeText(getApplicationContext(), "Start Change Password ...", Toast.LENGTH_SHORT).show();
			startActivityForResult(i, Publics.REQ_CHANGE_PASS);
		}
	};
	
	/***/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == Activity.RESULT_CANCELED) {
			 //
		 }
		 else if(requestCode == Publics.REQ_CHANGE_PASS )
		 {
			 Toast.makeText(getApplicationContext(), "Updated Pass.....", Toast.LENGTH_SHORT).show();
		 }
	}
	
	/***/
	OnClickListener handleCheck = new OnClickListener() {
		
		public void onClick(View v) {
			if(chk_settingPass.isChecked())
			{
				btn_settingChangePass.setVisibility(v.VISIBLE);
				Publics.Protect = 1;
			}
			else
			{
				btn_settingChangePass.setVisibility(v.INVISIBLE);
				Publics.Protect = -1;
			}
		}
	};
}
