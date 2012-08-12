package account.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class AddNewAccount extends Activity {
	private Spinner spn_settingCurrency;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.new_account);
		
		//create function for 4 image button on bottom
		Publics.bottomFunction(this);	
		
		//Action when click "save" button
		Button btnSave = (Button)findViewById(R.id.btn_AddNewAccountSave);
		spn_settingCurrency = (Spinner)findViewById(R.id.spn_accNewAdd);
		ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listCurrency);
		
		spn_settingCurrency.setAdapter(adapterCurrency);
		btnSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//execute add new here...
				
				
				//result code for ManageAccount activity
				setResult(Publics.CODE_SUCCESS);
				finish();
				
			}
		});
	}
}
