package tool.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ToolExport extends Activity{
	private Button btn_export;
	private Button btn_exportSendFile;
	private RadioGroup rad;
	private EditText edt_name;
	private Spinner spn_time;
	private TimePicker dpk_date;
	private TextView tv_tmp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_export);
		
		btn_export = (Button)findViewById(R.id.btn_toolExport);
		btn_exportSendFile = (Button)findViewById(R.id.btn_toolExportSendViaEmail);
		rad = (RadioGroup)findViewById(R.id.rad_toolBackupGroup1);
		tv_tmp = (TextView)findViewById(R.id.tv_toolExportAt);
		dpk_date = (TimePicker)findViewById(R.id.timePicker_toolExportScheduler);
		spn_time = (Spinner)findViewById(R.id.spn_toolExportScheduler);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listDay);
		
		/***/
		btn_export.setOnClickListener(handleExport);
		btn_exportSendFile.setOnClickListener(handleSendFile);
		rad.setOnCheckedChangeListener(handleCheck);
		spn_time.setAdapter(adapter);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/***/
	OnClickListener handleExport = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			Toast.makeText(getApplicationContext(), "Exported ...........", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/***/
	OnClickListener handleSendFile = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), SendFileExport.class);
			Toast.makeText(getApplicationContext(), "Send File Via Email...........", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/**Click select radio button*/
	OnCheckedChangeListener handleCheck = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio01:
			{
				spn_time.setVisibility(View.GONE);
				dpk_date.setVisibility(View.GONE);
				tv_tmp.setVisibility(View.GONE);
			}break;
			case R.id.radio1_1:
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
