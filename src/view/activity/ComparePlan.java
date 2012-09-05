package view.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ComparePlan extends Activity {
	private Spinner spn_viewCPlan;
	private Spinner spn_viewMonth;
	private TextView tv_viewTarget;
	private TextView tv_viewExpense;
	private TextView tv_viewConlusion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_compare_plan);
		
		/***/
		spn_viewCPlan = (Spinner)findViewById(R.id.spn_viewPlan);
		spn_viewMonth = (Spinner)findViewById(R.id.spn_viewType);
		tv_viewTarget = (TextView)findViewById(R.id.tv_viewTargetAmount);
		tv_viewExpense = (TextView)findViewById(R.id.tv_viewExpenseAmount);
		tv_viewConlusion = (TextView)findViewById(R.id.tv_viewConlusion);
		
//		ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getApplicationContext(), 
//				android.R.layout.simple_spinner_item, Publics.listMonth);
//		ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1,
//				new String[]{"Plan 1","Plan 2"});
		
		
		/***/
//		spn_viewMonth.setAdapter(adapterType);
//		spn_viewCPlan.setAdapter(adap);
		spn_viewMonth.setOnItemSelectedListener(handleItem);
		//Complete plan before
	}
	
	/**Handle event of click spinner*/
	OnItemSelectedListener handleItem = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			String tmp = spn_viewCPlan.getSelectedItem().toString();
			switch(arg2)
			{
				case 0: //Handle event 6
				{
					if(tmp.equals("Plan 1"))
					{
						tv_viewTarget.setText("5,000 VND");
						tv_viewExpense.setText("3,500 VND");
						tv_viewConlusion.setText("Save money very good!!!");
					}
				}break;
				case 1: // Handle event 7
				{
					if(tmp.equals("Plan 2"))
					{
						tv_viewTarget.setText("2,000 VND");
						tv_viewExpense.setText("3,000 VND");
						tv_viewConlusion.setText("Save money very bad!!!");
					}
				}break;
				default :
					{
						tv_viewTarget.setText("");
						tv_viewExpense.setText("");
						tv_viewConlusion.setText("");
					}break;
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}
