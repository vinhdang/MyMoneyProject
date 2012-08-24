package plan.activity;

import java.util.ArrayList;
import java.util.List;

import publics.Publics;
import main.activity.R;
import model.plan.Plan;
import model.plan.PlanAdapter;
import model.plan.PlanDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ManagePlan extends Activity {
	private List<Plan> list_plan;
	private PlanDataSource dataSource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_plan);
		list_plan = new ArrayList<Plan>();
		Publics.list_Plan = new ArrayList<Plan>();
		dataSource = new PlanDataSource(getApplicationContext());
		try{
			dataSource.open();
			list_plan = dataSource.getAllPlans();
			dataSource.close();
			Publics.list_Plan = list_plan;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
		//Fill listview content (fake data)
		ListView lv_plan = (ListView)findViewById(R.id.lv_listPlan);
		PlanAdapter adap = new PlanAdapter(getApplicationContext(), list_plan);
		lv_plan.setAdapter(adap);
		
		
		//Action when click "add" button
		Button btnAddNewPlan = (Button)findViewById(R.id.btn_addNewPlan);
		btnAddNewPlan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(ManagePlan.this, AddNewPlan.class);
				startActivityForResult(i, 1);
			}
		});
		//end action
		
		
		//end build
		//Test popup menu
		
		lv_plan.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(ManagePlan.this, AddNewPlan.class);
				i.putExtra("POS", arg2);
				startActivity(i);
			}
		});
		//end test
	}
}
