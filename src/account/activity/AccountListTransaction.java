package account.activity;


import publics.Publics;
import main.activity.R;
import model.account.AccountListTransactionAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AccountListTransaction extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.account_transaction_list);
		ListView lv_trans = (ListView)findViewById(R.id.lv_acc_list_transaction);
		lv_trans.setAdapter(new AccountListTransactionAdapter(this, Publics.list_Transaction));
		
		//create function for 4 image button on bottom
		Publics.bottomFunction(this);			
	}
	
	
}
