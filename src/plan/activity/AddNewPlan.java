package plan.activity;

import publics.Publics;
import main.activity.R;
import model.plan.Plan;
import model.plan.PlanDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewPlan extends Activity {
	private Spinner spn_acc;
	private Spinner spn_cate;
	private EditText edt_name;
	private EditText edt_amount;
	private Button btn_save;
	private PlanDataSource dataSource;
	private Plan plan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_plan);
		dataSource = new PlanDataSource(getApplicationContext());
		plan = new Plan();
		
		spn_acc = (Spinner)findViewById(R.id.spn_planAddaccName);
		spn_cate = (Spinner)findViewById(R.id.spn_planAddcategory);
		edt_amount = (EditText)findViewById(R.id.edt_planAddtargetAmount);
		edt_name = (EditText)findViewById(R.id.edt_planAdditemName);			
		//create on click for 5 top button
		Publics.topFunction(this);
		btn_save.setOnClickListener(hanldeSave);
	}
	
	/**Click Save*/
	OnClickListener hanldeSave = new OnClickListener() {
		
		public void onClick(View v) {
			if(getData())
			{
				try{
					dataSource.open();
					dataSource.insertPlan(plan);
					dataSource.close();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	};
	
	/**Get data*/
	private boolean getData()
	{
		if(plan != null)
		{
			plan.setName(edt_name.getText().toString());
			plan.setAccount(spn_acc.getSelectedItem().toString());
			plan.setCategory(spn_cate.getSelectedItem().toString());
			String tmp = edt_amount.getText().toString();
			if(Publics.isNumeric(tmp))
			{
				plan.setAmount(Double.parseDouble(tmp));
				return true;
			}
			else
			{
				edt_amount.setText("");
				edt_amount.setFocusable(true);
				return false;
			}
		}
		return false;
	}
}
