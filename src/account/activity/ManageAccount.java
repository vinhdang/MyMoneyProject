package account.activity;

import publics.Publics;
import main.activity.R;
import model.account.AccountAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ManageAccount extends Activity {
	private ListView lv_acc;
	private Button btnAddNewAcc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.manage_account);
		
		/**Process data*/
		lv_acc = (ListView)findViewById(R.id.lv_account);
		btnAddNewAcc = (Button)findViewById(R.id.btn_addNewAccount);
		AccountAdapter accAdapter = new AccountAdapter(getApplicationContext(), Publics.list_Account);
			
		/**Set function*/
		lv_acc.setAdapter(accAdapter);
		btnAddNewAcc.setOnClickListener(handleNew);
		lv_acc.setOnItemClickListener(handleView);
		lv_acc.setOnItemLongClickListener(handleLongClick);
	}
	
//	 @Override
//	  protected void onResume() {
//		dataSource.open();
//	    super.onResume();
//	  }
//
//	  @Override
//	  protected void onPause() {
//		dataSource.close();
//	    super.onPause();
//	  }
	
	/** Click On ListVIew*/
	OnItemClickListener handleView = new OnItemClickListener()
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 Publics.paramToMngTrans = arg2;
			 Publics.tabHost.setCurrentTabByTag("Transaction");			
		}
	};
	
	/**Long Click On ListView*/
	OnItemLongClickListener handleLongClick = new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) 
		{				
			Intent i = new Intent(getApplicationContext(), AccountDetail.class);
			i.putExtra("POS", arg2);
			startActivity(i);
			return true;
		}
	};
	
	/**Click Add New*/
	OnClickListener handleNew = new OnClickListener() {
		
		public void onClick(View v) {
//			Intent intentAddNewAccount = new Intent(ManageAccount.this, AddNewAccount.class);
//			startActivityForResult(intentAddNewAccount,Publics.REQ_NEW_ACCOUNT);
			Intent i = new Intent(getApplicationContext(), AddNewAccount.class);
			startActivity(i);
		}
	};
	
	/**Activity Result*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
