package view.activity;

import publics.Publics;
import main.activity.R;
import model.transaction.TransactionAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ManageView extends Activity {
	private Spinner spn_viewList;
	private TextView tv_viewTotal;
	private ListView lv_viewTransaction;
	private TransactionAdapter transAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_menu);
		
		/**Get id and process*/
		spn_viewList = (Spinner)findViewById(R.id.spn_reportListType);
		tv_viewTotal = (TextView)findViewById(R.id.tv_viewTotalMenu);
		lv_viewTransaction = (ListView)findViewById(R.id.lv_viewTransaction);
		ArrayAdapter<String> adapterListType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Publics.listSpinnerView);
		transAdapter = new TransactionAdapter(getApplicationContext(), Publics.list_Transaction);
		
		/**Set function for component*/
		spn_viewList.setAdapter(adapterListType);
		spn_viewList.setOnItemSelectedListener(handleItem);
		lv_viewTransaction.setAdapter(transAdapter);
		tv_viewTotal.setText("3,855");
	}
	
	/**Event of click item*/
	OnItemSelectedListener handleItem = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			switch(arg2)
			{
				case 0: //Handle event of Daily 
				{
					Toast.makeText(getApplicationContext(), "Choose Report Daily .....", Toast.LENGTH_SHORT).show();
					Intent i = new  Intent(getApplicationContext(), ViewDaily.class);
					startActivity(i);
				}break;
				case 1: // Handle event of Category
				{
					Toast.makeText(getApplicationContext(), "Choose Report Category .....", Toast.LENGTH_SHORT).show();
					Intent i = new  Intent(getApplicationContext(), ViewCategory.class);
					startActivity(i);
				}break;
				case 2: // Handle event of Compare Plan
				{
					Toast.makeText(getApplicationContext(), "Choose Report Compare Plan .....", Toast.LENGTH_SHORT).show();
					Intent i = new  Intent(getApplicationContext(), ComparePlan.class);
					startActivity(i);
				}break;
				default: break;
			}	
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			Toast.makeText(getApplicationContext(), "Choose Report Daily As Default.....", Toast.LENGTH_SHORT).show();
		}
	}; 

}
