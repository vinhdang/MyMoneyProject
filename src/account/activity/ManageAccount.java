package account.activity;

import publics.Publics;
import main.activity.R;
import model.account.AccountAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
//		registerForContextMenu(lv_acc);
	}
	
	/** Click On ListVIew*/
	OnItemClickListener handleView = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 Publics.paramToMngTrans = arg2;
			 Publics.tabHost.setCurrentTabByTag("Transaction");			
		}
	};
	
	/**Long Click On ListView*/
	OnItemLongClickListener handleLongClick = new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int arg2, long arg3) {			
			//Build popup menu
			final AlertDialog.Builder builder = new AlertDialog.Builder(ManageAccount.this);
			builder.setItems(new String[]{"View Detail"}, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(getApplicationContext(), AccountDetail.class);
					i.putExtra("POS", arg2);
					startActivity(i);
				}
			});
			builder.create().show();
			
			return true;
		}
	};
	
	/**Click Add New*/
	OnClickListener handleNew = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intentAddNewAccount = new Intent(ManageAccount.this, AddNewAccount.class);
			startActivityForResult(intentAddNewAccount,Publics.REQ_NEW_ACCOUNT);
		}
	};
	
	/**Activity Result*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**Context Menu*/
	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
	 	if(v.getId() == R.id.lv_account)
	 	{
	 		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	 		super.onCreateContextMenu(menu, v, menuInfo);
	 		 menu.setHeaderTitle(Publics.list_Account.get(info.position).getAccountName());  
	 		super.onCreateContextMenu(menu, v, menuInfo);   
	        menu.add(0, v.getId(), 0, "View transaction");  
	 	}
    }  
 
	@Override  
    public boolean onContextItemSelected(MenuItem item) { 
		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		 Publics.paramToMngTrans = info.position;
		 Publics.tabHost.setCurrentTabByTag("Transaction");
		return true;  
    }
}
