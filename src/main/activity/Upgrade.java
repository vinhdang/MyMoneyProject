package main.activity;

import general.activity.General;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Upgrade extends Activity {
	private Button btn_OK;
	private Button btn_Cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upgrade);
		
		/***/
		btn_Cancel = (Button)findViewById(R.id.btn_upgradeCancel);
		btn_OK = (Button)findViewById(R.id.btn_upgradeOK);
		
		/***/
		btn_Cancel.setOnClickListener(handleCancel);
		btn_OK.setOnClickListener(handleOK);
	}
	
	/***/
	OnClickListener handleOK = new OnClickListener() {
		
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Start Upgrade ....", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getApplicationContext(), General.class);
			startActivity(i);
		}
	};
	
	OnClickListener handleCancel = new OnClickListener() {
		
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Canceled Upgrade ....", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getApplicationContext(), General.class);
			startActivity(i);
		}
	};
}
