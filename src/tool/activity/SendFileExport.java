package tool.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SendFileExport extends Activity {
	private Button btn_exportSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_send_export);
		
		/***/
		btn_exportSend = (Button)findViewById(R.id.btn_toolExportSendFile);
		
		/***/
		btn_exportSend.setOnClickListener(handleSendFile);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/***/
	OnClickListener handleSendFile = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			Toast.makeText(getApplicationContext(), "Send file to email....", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
}
