package billreminder.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.os.Bundle;

public class UpdateBillReminder extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.update_bill_reminder);
		
		//function for home button
        Publics.bottomFunction(this);
        
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	

	
}
