package tool.activity;

import java.util.ArrayList;

import publics.Publics;

import main.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ToolBackup extends Activity {
	private Button btn_backupSave;
	private Button btn_backupSendViaEmail;
	private RadioGroup rad;
	private EditText edt_name;
	private Spinner spn_time;
	private TimePicker dpk_date;
	private TextView tv_tmp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_backup);
		
		/** */
		btn_backupSave = (Button)findViewById(R.id.btn_toolSaveBackup);
		btn_backupSendViaEmail = (Button)findViewById(R.id.btn_toolSendViaEmail);
		rad = (RadioGroup)findViewById(R.id.rad_toolBackupGroup);
		tv_tmp = (TextView)findViewById(R.id.tv_toolBackupAt);
		dpk_date = (TimePicker)findViewById(R.id.timePicker_toolScheduler);
		spn_time = (Spinner)findViewById(R.id.spn_toolScheduler);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listDay);
		
		/** */
		btn_backupSave.setOnClickListener(handleSave);
		btn_backupSendViaEmail.setOnClickListener(handleSendViaEmail);		
		rad.setOnCheckedChangeListener(handleCheck);
		spn_time.setAdapter(adapter);
		
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/**Event click save backup*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			Toast.makeText(getApplicationContext(), "Backup And Save .... ", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/**Click send file*/
	OnClickListener handleSendViaEmail = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), SendFileBackup.class);
			Toast.makeText(getApplicationContext(), "Start Send File ......", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/**Click select radio button*/
	OnCheckedChangeListener handleCheck = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio0:
			{
				spn_time.setVisibility(View.GONE);
				dpk_date.setVisibility(View.GONE);
				tv_tmp.setVisibility(View.GONE);
			}break;
			case R.id.radio1:
			{
				spn_time.setVisibility(View.VISIBLE);
				dpk_date.setVisibility(View.VISIBLE);
				tv_tmp.setVisibility(View.VISIBLE);
			}break;
			default:
				break;
			}
		}
	};
}
