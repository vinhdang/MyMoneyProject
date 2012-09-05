package tool.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class SendFileExport extends Activity {
	private Button btn_exportSend;
	private RadioGroup rg;
	private Spinner spn_File;
	private EditText edt_content;
	private EditText edt_email;
	private boolean bkf_status = false;
	private List<String> listFile;
	private String key;
	private String fileName;
	private String content;
	private String email; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_send_export);
		listFile = new ArrayList<String>();
		
		/**Process*/
		btn_exportSend = (Button)findViewById(R.id.btn_toolExportSendFile);
		rg = (RadioGroup)findViewById(R.id.radioGroup_sendFile);
		spn_File = (Spinner)findViewById(R.id.spn_toolExportSendFile);
		edt_content = (EditText)findViewById(R.id.edt_toolExportSendContent);
		edt_email = (EditText)findViewById(R.id.edt_toolExportSendEmail);
		key = ".db";
		getFile(key);
		setSpinner(listFile);
		
		/**Set function*/
		btn_exportSend.setOnClickListener(handleSendFile);
		rg.setOnCheckedChangeListener(hanldeSelect);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/**Send file*/
	OnClickListener handleSendFile = new OnClickListener() {
		
		public void onClick(View v) {
			fileName = spn_File.getSelectedItem().toString();
			content = edt_content.getText().toString().trim();
			email = edt_email.getText().toString().trim();
			sendMail_CSV(email, fileName, content);
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			Toast.makeText(getApplicationContext(), "Send file to email....", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/**Select type of file*/
	OnCheckedChangeListener hanldeSelect = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) 
			{
				case R.id.rad_sendBackup:
				{
					key = ".db";
					getFile(key);
					setSpinner(listFile);
				}break;
				case R.id.rad_sendExport:
				{
					key = ".csv";
					getFile(key);
					setSpinner(listFile);
				}break;
				default: break;
			}
		}
	};
	/**Send file to email*/
	 public void sendMail_CSV(String email, String filename, String content)
	 {
		 if(bkf_status == true) 
		 {
          //CSV file is created so we need to Export that ...
           final File CSVFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename);
           Log.i("SEND EMAIL TESTING", "Email sending");
           Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
           emailIntent.setType("text/csv");
           emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});
           emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Backup contacts CSV");           
           emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\n" + content);
           emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + CSVFile.getAbsolutePath()));
           emailIntent.setType("message/rfc822"); // Shows all application that supports SEND activity 
           try {
               startActivity(Intent.createChooser(emailIntent, "Send mail..."));
           } catch (android.content.ActivityNotFoundException ex) {
               Toast.makeText(getApplicationContext(), "Email client : " + ex.toString(), Toast.LENGTH_SHORT);
           }
       }
		else 
		{
           Toast.makeText(getApplicationContext(), "File not found.....", Toast.LENGTH_SHORT).show();
	    }
	 }
	 
	 /**Get file from sdcard*/
	 private void getFile(String keey)
	 {
		 File mfile=new File("/mnt/sdcard/MyMoney/");
		 File[] list=mfile.listFiles();

		 listFile.clear();
		 Toast.makeText(getApplicationContext(), "list"+mfile.listFiles().length, Toast.LENGTH_SHORT).show();
		 for(int i=0 ; i < mfile.listFiles().length; i++)
		 {
		     if(list[i].isFile())
		     {
		    	 String tmp = list[i].getName();
		    	 if(tmp.endsWith(keey))
		    	 {
		    		 listFile.add(tmp);
		    	 }
		         Toast.makeText(getApplicationContext(), "files.."+list[i].getName(), Toast.LENGTH_SHORT).show();
		     }
		 }
	 }
	 
	 /**Set new list for spinner*/
	 private void setSpinner(List<String> list)
	 {
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				 android.R.layout.simple_spinner_item, list);
		 spn_File.setAdapter(adapter);
	 }
}
