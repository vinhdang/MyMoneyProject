package account.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.account.AccountDataSource;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AccountDetail extends Activity {
	private TextView tv_name;
	private TextView tv_currency;
	private TextView tv_descript;
	private TextView tv_balance;
	private Button btnUpdate;
	private Button btnDelete;
	private int pos;
	private Account acc = null;
	private AccountDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_detail);
		dataSource = new AccountDataSource(this);
		
		tv_name = (TextView)findViewById(R.id.tv_accName);
		tv_currency = (TextView)findViewById(R.id.tv_currency);
		tv_balance = (TextView)findViewById(R.id.tv_startingBalance);
		tv_descript = (TextView)findViewById(R.id.tv_description);
		btnUpdate = (Button)findViewById(R.id.btn_update);
		btnDelete = (Button)findViewById(R.id.btn_delete);
		Intent i = getIntent();
		if(i != null)
		{
			pos = i.getIntExtra("POS", -1);
			if(pos != -1)
			{
				acc = Publics.list_Account.get(pos);
				tv_name.setText(acc.getAccountName());
				tv_currency.setText(acc.getUnit());
				tv_balance.setText(String.valueOf(acc.getFinalBalance()));
				tv_descript.setText(acc.getDescript());
			}
		}
		//Action when click "update" button	
		btnUpdate.setOnClickListener(handleUpdate);
		//Action when click "delete" button		
		btnDelete.setOnClickListener(hanldeDelete);
		//end action
	}
	
	/**Delete*/
	OnClickListener hanldeDelete = new OnClickListener() {
		
		public void onClick(View v) {
			Publics.msgBoxDelete(AccountDetail.this, acc.getAccountName(),
					new DialogInterface.OnClickListener() {
	
						public void onClick(DialogInterface dialog, int which) {
							dataSource.open();
							dataSource.deleteAccount(acc);
							dataSource.close();
							Intent intent = new Intent(getApplicationContext(),General.class);
							intent.putExtra("tab", 1);
							startActivity(intent);
						}
					}, 
					new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// cancel action
						}
					});
		}
	};
	
	/**Update*/
	OnClickListener handleUpdate = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intentUpdateAccount = new Intent(AccountDetail.this, UpdateAccount.class);
			intentUpdateAccount.putExtra("update", pos);
			startActivity(intentUpdateAccount);
		}
	};
}