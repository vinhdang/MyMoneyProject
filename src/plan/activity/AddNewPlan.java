package plan.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddNewPlan extends Activity {
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_plan);
		
		//Action when click "save" button
		Button btnSave = (Button)findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//action add new plan here
				
				
				AddNewPlan.this.finish();
			}
		});
		//end action
		
		//function for home button
        Publics.bottomFunction(this);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
}
