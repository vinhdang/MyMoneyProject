package plan.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpdatePlan extends Activity {
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_plan);
		
		//Action when click "save" button
		Button btnSave = (Button)findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//action here
				
				
				UpdatePlan.this.finish();
			}
		});

		//create on click for 5 top button
		Publics.topFunction(this);
	}
}
