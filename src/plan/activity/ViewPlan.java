package plan.activity;

import publics.Publics;
import main.activity.R;
import model.plan.Plan;
import model.plan.PlanDataSource;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewPlan extends Activity {
	private PlanDataSource dataSource;
	private int pos = -1;
	private Plan plan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_plan);
		dataSource = new PlanDataSource(getApplicationContext());
		plan = new Plan();
		Intent i = getIntent();
		if(i != null)
		{
			pos = i.getIntExtra("POS", -1);
			if(pos != -1)
			{
				plan = Publics.list_Plan.get(pos);
				//set textview
			}
		}
		
		
		//Action when click "update" button
		Button btnAddNewPlan = (Button)findViewById(R.id.btn_plan_update);
		btnAddNewPlan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(ViewPlan.this, UpdatePlan.class);
				intent.putExtra("POS", pos);
				startActivityForResult(intent,1);
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
								try{
									dataSource.open();
									dataSource.deletePlan(plan);
									dataSource.close();
									Intent intent = new Intent(getApplicationContext(), ManagePlan.class);
									startActivity(intent);
									finish();
								}catch(Exception ex)
								{
									ex.printStackTrace();
								}

							}
						}, new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// cancel action
								
							}
						});
			}
		});
	
		//create on click for 5 top button
		Publics.topFunction(this);
	}
}
