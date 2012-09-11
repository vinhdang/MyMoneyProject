package account.activity;

import java.util.ArrayList;
import java.util.List;

import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.account.AccountAdapter;
import model.category.Category;
import model.transaction.Transaction;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
	private List<Account> listNew;
	private AccountAdapter accAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.manage_account);
		
		/**Process data*/
		lv_acc = (ListView)findViewById(R.id.lv_account);
		btnAddNewAcc = (Button)findViewById(R.id.btn_addNewAccount);
		CalculateBalance();
		accAdapter = new AccountAdapter(getApplicationContext(), listNew);
			
		/**Set function*/
		lv_acc.setAdapter(accAdapter);
		btnAddNewAcc.setOnClickListener(handleNew);
		lv_acc.setOnItemClickListener(handleView);
		lv_acc.setOnItemLongClickListener(handleLongClick);
	}
	
	/** Click On ListVIew*/
	OnItemClickListener handleView = new OnItemClickListener()
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 Publics.paramToMngTrans = arg2;
			 Publics.tabHost.setCurrentTabByTag("Transaction");			
		}
	};
	
	@Override
	protected void onResume() {
		CalculateBalance();
		accAdapter = new AccountAdapter(getApplicationContext(), listNew);
		lv_acc.setAdapter(accAdapter);
		super.onResume();
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
			Intent i = new Intent(getApplicationContext(), AddNewAccount.class);
			startActivity(i);
		}
	};
	
	/**Activity Result*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
		
	/**Calculate balance*/
	private void CalculateBalance()
	{
		listNew = new ArrayList<Account>();
		double totalExpense = 0;
		double totalIncome = 0;
		double rs = 0;
		if(Publics.list_Account.size() > 0)
		{
			for(Account a : Publics.list_Account)
			{
				totalExpense = 0;
				totalIncome = 0;
				for(Transaction t : Publics.list_Transaction)
				{
					if(t.getTransactionAccount().equals(a.getAccountName()))
					{
						String type = CheckCategory(t.getTransactionCategory());
						if(type.equals("Chi tieu"))
						{
							totalExpense += t.getTransactionAmount();
						}
						else if(type.equals("Thu nhap"))
						{
							totalIncome += t.getTransactionAmount();
						}
					}
				}
				double b = a.getFinalBalance();
				if(totalExpense > totalIncome)
				{
					rs = totalExpense - totalIncome;
					a.setFinalBalance(b-rs);
				}
				else
				{
					rs = totalIncome - totalExpense;
					a.setFinalBalance(b + rs);
				}
				listNew.add(a);
			}
		}
	}
	
	private String CheckCategory(String CategoryName)
	{
		String rs = "";
		for(Category c : Publics.list_Category)
		{
			if(c.getCategoryName().equals(CategoryName))
			{
				rs = c.getCategoryType();
				return rs;
			}
		}
		return rs;
	}
}
