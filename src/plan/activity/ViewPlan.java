package plan.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewPlan extends Activity {
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_plan);
		
		//Action when click "update" button
		Button btnAddNewPlan = (Button)findViewById(R.id.btn_plan_update);
		btnAddNewPlan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intentAddNewAccount = new Intent(ViewPlan.this, UpdatePlan.class);
				startActivityForResult(intentAddNewAccount,1);
			}
		});
		//end action
		
		//Action when click "delete" button
		Button btnDelete = (Button)findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Publics.msgBoxDelete(ViewPlan.this, "This plan will be deleted from List plan",
						new DialogInterface.OnClickListener() {
		
							public void onClick(DialogInterface dialog, int which) {
								// delete action
							}
						}, new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// cancel action
								
							}
						});
			}
		});
		//end action
		
        //function for home button
        Publics.bottomFunction(this);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
}
