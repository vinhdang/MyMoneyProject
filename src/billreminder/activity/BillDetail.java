package billreminder.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BillDetail extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bill_detail);
		
		
		//Update button
		Button btnUpdate = (Button)findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intentUpdateAccount = new Intent(BillDetail.this, UpdateBillReminder.class);
				startActivityForResult(intentUpdateAccount,Publics.REQ_UPDATE_ACCOUNT);
				
			}
		});
		//delete button
		Button btnDelete = (Button)findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Publics.msgBoxDelete(BillDetail.this, "This bill will be deleted from List bill",
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
	}
}
