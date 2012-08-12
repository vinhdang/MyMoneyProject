package plan.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ManagePlan extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.manage_plan);
		
		//Fill listview content (fake data)
		ListView lv_plan = (ListView)findViewById(R.id.lv_listPlan);
		ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
										android.R.layout.simple_list_item_1,
										new String[]{"Plan 1","Plan 2","plan 3","plan 4"});
		lv_plan.setAdapter(adap);
		
		
		//Action when click "add" button
		Button btnAddNewPlan = (Button)findViewById(R.id.btn_addNewPlan);
		btnAddNewPlan.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intentAddNewAccount = new Intent(ManagePlan.this, AddNewPlan.class);
				startActivityForResult(intentAddNewAccount,1);
			}
		});
		//end action
		
		//Build popup menu
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[]{"Update","View","Delete"}, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				if (which==0) //Update
				{
					Intent intentUpdateAccount = new Intent(ManagePlan.this, UpdatePlan.class);
					startActivityForResult(intentUpdateAccount,Publics.REQ_UPDATE_ACCOUNT);
				}
				else if (which==1) //view
				{
					Intent intentUpdateAccount = new Intent(ManagePlan.this, ViewPlan.class);
					startActivityForResult(intentUpdateAccount,Publics.REQ_VIEW_ACCOUNT);
				}
				else if (which==2) //delete
				{
					Publics.msgBoxDelete(ManagePlan.this, "This plan will be deleted from List plan",
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

			}
		});
		//end build
		//Test popup menu
		
		lv_plan.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				builder.create().show();
				
			}
		});
		//end test
		
		
		//function for home button
        Publics.bottomFunction(this);
	}
}
