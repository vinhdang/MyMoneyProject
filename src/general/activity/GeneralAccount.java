package general.activity;

import java.util.ArrayList;
import java.util.List;
import publics.Publics;
import transaction.activity.TransactionNew;
import main.activity.R;
import model.account.Account;
import model.account.AccountAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class GeneralAccount extends Activity {
	private ListView lv_acc;
	private List<Account> list_acc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_general);
		Publics.bottomFunction(this);
		
		list_acc = new ArrayList<Account>();
		list_acc.add(new Account("Dong A", 5000, "VND",""));
		list_acc.add(new Account("HSBC", 10000, "VND",""));
		list_acc.add(new Account("ACB", 2000, "VND",""));
		Publics.list_Account = new ArrayList<Account>();
		Publics.list_Account = list_acc;
		
		lv_acc = (ListView)findViewById(R.id.lv_generalAccount);
		AccountAdapter accAdapter = new AccountAdapter(getApplicationContext(), list_acc);
		
		/***/
		lv_acc.setAdapter(accAdapter);
		
	}
	
	/***/
	OnItemClickListener handleAcc = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			registerForContextMenu(lv_acc);
			arg1.showContextMenu();	
		}
	};
	
	 @Override  
	    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		 	if(v.getId() == R.id.lv_generalAccount)
		 	{
		 		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		 		super.onCreateContextMenu(menu, v, menuInfo);
		 		 menu.setHeaderTitle(list_acc.get(info.position).getAccountName());  
		 		super.onCreateContextMenu(menu, v, menuInfo);   
		        menu.add(0, v.getId(), 0, "Add new transaction");  
		 	}
	    }  
	 
	 @Override  
	    public boolean onContextItemSelected(MenuItem item) { 
        	Intent intent = new Intent(this,TransactionNew.class);
			startActivity(intent);
	    return true;  
	    }
	 

}
