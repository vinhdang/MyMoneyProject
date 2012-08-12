package account.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpdateAccount extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_account);
		
		//create function for 4 image button on bottom
		Publics.bottomFunction(this);	
		
		//create on click for 5 top button
		Publics.topFunction(this);
		
		//Action when click "save" button
		Button btnSave = (Button)findViewById(R.id.btn_UpdateAccountSave);
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