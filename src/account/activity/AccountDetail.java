package account.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccountDetail extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_detail);
		
		//create function for 4 image button on bottom
		Publics.bottomFunction(this);	
		
		//Action when click "update" button
		Button btnUpdate = (Button)findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intentAddNewAccount = new Intent(AccountDetail.this, UpdateAccount.class);
				startActivityForResult(intentAddNewAccount,Publics.REQ_UPDATE_ACCOUNT);
			}
		});
		//end action
		
		//Action when click "delete" button
		Button btnDelete = (Button)findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Publics.msgBoxDelete(AccountDetail.this, "This plan will be deleted from List plan",
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
	}
}