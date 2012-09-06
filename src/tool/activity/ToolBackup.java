package tool.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import publics.Publics;
import main.activity.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ToolBackup extends Activity {
	private Button btn_backupSave;
	private Button btn_back;
	private RadioGroup rad;
	private EditText edt_name;
	private Spinner spn_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_backup);
		
		/** Process*/
		btn_backupSave = (Button)findViewById(R.id.btn_toolSaveBackup);
		btn_back = (Button)findViewById(R.id.btn_toolBackupBack);
		rad = (RadioGroup)findViewById(R.id.rad_toolBackupGroup);
		spn_time = (Spinner)findViewById(R.id.spn_toolScheduler);
		edt_name = (EditText)findViewById(R.id.edt_toolNameFile);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listRepeat);
		
		/**Set function */
		btn_backupSave.setOnClickListener(handleSave);	
		btn_back.setOnClickListener(handleBack);
		rad.setOnCheckedChangeListener(handleCheck);
		spn_time.setAdapter(adapter);
		
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/**Event click save backup*/
	OnClickListener handleSave = new OnClickListener() {
		
		@SuppressLint("SdCardPath")
		@SuppressWarnings("resource")
		public void onClick(View v) {
			
//			processcing();
			try {
		        File sd = Environment.getExternalStorageDirectory();
		        File data = Environment.getDataDirectory();

		        if (sd.canWrite()) {
		            String currentDBPath = "//data//main.activity//databases//Database.db";
		            String tmp = edt_name.getText().toString().trim() + ".db";
		            String backupDBPath = tmp;
		            File newFolder = new File("/sdcard/MyMoney/");
		            File currentDB = new File(data, currentDBPath);
		            File backupDB = new File(newFolder, backupDBPath);
	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
		        }
		    } catch (Exception e) 
		    {
		    	e.printStackTrace();
		    }		    
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			Toast.makeText(getApplicationContext(), "Backup And Save .... ", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/**Click send file*/
	OnClickListener handleBack = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			startActivity(i);
			finish();
		}
	};
	
	/**Click select radio button*/
	OnCheckedChangeListener handleCheck = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio0:
			{
				spn_time.setVisibility(View.GONE);
			}break;
			case R.id.radio1:
			{
				spn_time.setVisibility(View.VISIBLE);
			}break;
			default:
				break;
			}
		}
	};
}
