package setting.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ChangePassword extends Activity {
	private Button btn_settingSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pass);

		/**Get id and process*/
		btn_settingSave = (Button)findViewById(R.id.btn_settingSaveNewPass);
		
		/**Set function for component*/
		btn_settingSave.setOnClickListener(handleSave);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/**Event of click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), ManageSetting.class);
			Toast.makeText(getApplicationContext(), "Changed Password.....", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK, i);
		}
	};
}
