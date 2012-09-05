package setting.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.setting.SettingDataSource;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_setting);
		//create function for 4 image button on bottom
		Publics.topFunction(this);
				
		/**Get id and process*/
		btn_settingSave = (Button)findViewById(R.id.btn_settingSave);
		btn_settingChangePass = (Button)findViewById(R.id.btn_settingChangePass);
		spn_settingLanguage = (Spinner)findViewById(R.id.spn_settingLanguage);
		spn_settingDateFormat = (Spinner)findViewById(R.id.spn_settingDateFormat);
		chk_settingPass = (CheckBox)findViewById(R.id.chk_settingProtectByPass);
		ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Publics.listLanguage);
		ArrayAdapter<String> adapterDateFormate = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Publics.listDateFormat);
		if(Publics.list_Setting.get(Publics.Pass_num).getValue() != "" )
		{
			chk_settingPass.setChecked(true);
			btn_settingChangePass.setVisibility(View.VISIBLE);
			flag = 1;
		}
		else
		{
			chk_settingPass.setChecked(false);
			btn_settingChangePass.setVisibility(View.GONE);
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
			
			SettingDataSource ds = new SettingDataSource(getApplicationContext());
			try{
				ds.open();
				if(flag == 0)
				{
					Publics.list_Setting.get(Publics.Pass_num).setValue("");	
					ds.updateSetting(Publics.list_Setting.get(Publics.Pass_num));
				}
				Publics.list_Setting.get(Publics.Language_num).setValue(spn_settingLanguage.getSelectedItem().toString());
				Publics.list_Setting.get(Publics.DateFormat_num).setValue(spn_settingDateFormat.getSelectedItem().toString());
				ds.updateSetting(Publics.list_Setting.get(Publics.Language_num));
				ds.updateSetting(Publics.list_Setting.get(Publics.DateFormat_num));
				ds.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
				ds.close();
			}			
			Intent i = new Intent(getApplicationContext(), General.class);
			startActivity(i);
			finish();
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
		
		@SuppressWarnings("static-access")
		public void onClick(View v) {
			if(chk_settingPass.isChecked())
			{
				btn_settingChangePass.setVisibility(v.VISIBLE);
				flag = 1;
			}
			else
			{
				Publics.list_Setting.get(Publics.Pass_num).setValue("");
				AlertDialog.Builder builder = new AlertDialog.Builder(ManageSetting.this);
				builder.setTitle("Warning!!!")
				 .setMessage("Are you sure remove your password???")
				 .setPositiveButton("OK", new DialogInterface.OnClickListener()
				 {
					public void onClick(DialogInterface dialog, int which)
					{
						flag = 0;
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
				btn_settingChangePass.setVisibility(View.GONE);
			}
		}
	};
}
