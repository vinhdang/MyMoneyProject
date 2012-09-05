package general.activity;

import java.util.ArrayList;
import billreminder.activity.ManageBillReminder;
import publics.Publics;
import report.activity.GraphicActivity;
import transaction.activity.ManageTransaction;
import main.activity.R;
import model.account.Account;
import model.account.AccountDataSource;
import model.bill.Bill;
import model.bill.BillDataSource;
import model.category.Category;
import model.category.CategoryDataSoure;
import model.setting.Setting;
import model.setting.SettingDataSource;
import model.transaction.Transaction;
import account.activity.ManageAccount;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import model.transaction.TransactionDataSource;

public class General extends TabActivity {
	public static TabSpec mTabCurrent;
	private AccountDataSource dataSourceAcc;
	private CategoryDataSoure dataSourceCate;
	private BillDataSource dataSourceBill;
	private SettingDataSource dataSourceSet;
	private TransactionDataSource dataSourceTrans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		/**processing Transaction*/
		/**Processing Setting*/
		dataSourceSet = new SettingDataSource(this);
		Publics.list_Setting = new ArrayList<Setting>();
		try{
			dataSourceSet.open();
			Publics.list_Setting = dataSourceSet.getAllSettings();
			dataSourceSet.close();
			if(Publics.list_Setting.size() == 0)
			{
				Publics.list_Setting.add(new Setting(1, "UseName", ""));//Save usename
				Publics.list_Setting.add(new Setting(2, "Pass", ""));//Save password
				Publics.list_Setting.add(new Setting(3, "UpdateDate", ""));//Save Update Date of rate
				Publics.list_Setting.add(new Setting(4, "RateSell", "0"));// Save rate sell
				Publics.list_Setting.add(new Setting(5, "RateBuy", "0"));//Save rate buy
				Publics.list_Setting.add(new Setting(6, "DateFormat", "dd/MM/yyyy"));//Save date format
				Publics.list_Setting.add(new Setting(7, "Language", "Vietnamese"));//Save language
				Publics.list_Setting.add(new Setting(8, "Backup", ""));//Save schedule backup auto
				dataSourceSet.open();
				for(Setting st : Publics.list_Setting)
				{
					try{
						dataSourceSet.insertSetting(st);
					}catch(Exception e)
					{
						e.printStackTrace();
						dataSourceSet.close();
						break;
					}
				}
				dataSourceSet.close();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dataSourceSet.close();
		}
		if(checkFirst(1))
		{
			this.showDialog(1);//First use
		}
		else
		{
			this.showDialog(2);//login
		}
		
		/**get all transaction had*/
		Publics.list_Transaction = new ArrayList<Transaction>();
		dataSourceTrans = new TransactionDataSource(this);
		dataSourceTrans.open();
		try{
			Publics.list_Transaction = dataSourceTrans.getAllTransactions();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dataSourceTrans.close();
		}
		if(Publics.list_Transaction.size() == 0)
		{
			Transaction a = new Transaction("An bun bo", "13/07/2012", (double)20000, "An uong", "Dong A", "An ngon wa",  "Tien mat", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Luong Lotteria", "29/07/2012", (double) 1500, "Luong", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Day them", "29/07/2012", (double) 1000, "Luong", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("An sang", "9/07/2012", (double) 15, "An uong", "Dong A", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Do xang", "9/07/2012", (double) 50, "Nhien lieu", "Dong A", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Com trua", "9/07/2012", (double) 30, "An uong", "Dong A", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Cafe", "9/07/2012", (double) 10, "An uong", "Dong A", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Luong Lotteria", "29/06/2012", (double) 1500, "Luong", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Dong tien hoc", "25/06/2012", (double) 2000, "Phi", "ACB", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Tien Dien", "25/06/2012", (double) 800, "Phi", "ACB", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Tien nuoc", "24/06/2012", (double) 200, "Phi", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Xem phim", "24/06/2012", (double) 150, "Giai tri", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Dam cuoi", "22/06/2012", ((double) 500), "Dam tiec", "ACB", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Sua xe", "22/06/2012", (double) 100, "Sua chua", "ACB", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Mua sam","19/06/2012",(double) 180,"Mua sam", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Mua xe","18/07/1991",(double) 25000, "Mua sam", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("An Sang","14/06/2012",(double) 5,"An uong", "Dong A", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			a = new Transaction("Xem phim","14/06/2012",(double) 55,"Giai tri", "HSBC", "", "Card", "Khong lap");
			dataSourceTrans.insertTransaction(a);
			Publics.list_Transaction = dataSourceTrans.getAllTransactions();
		}
		dataSourceTrans.close();
		/**Processing Account*/
		Publics.list_Account = new ArrayList<Account>();
		dataSourceAcc = new AccountDataSource(this);
		dataSourceAcc.open();
		try{
			Publics.list_Account = dataSourceAcc.getAllAccounts();
		}catch(Exception ex)	
		{
			ex.printStackTrace();
			dataSourceAcc.close();
		}
		if(Publics.list_Account.size() == 0)
		{
			Account a = new Account("Dong A", (double)5000, "VND", "");
			dataSourceAcc.insertAccount(a);
			a = new Account("HSBC", (double)10000, "VND", "");
			dataSourceAcc.insertAccount(a);
			a = new Account("ACB", (double)2000, "VND", "");
			dataSourceAcc.insertAccount(a);
			Publics.list_Account = dataSourceAcc.getAllAccounts();
		}
		dataSourceAcc.close();

		/**Processing Category*/
		Publics.list_Category = new ArrayList<Category>();
		dataSourceCate = new CategoryDataSoure(this);
		dataSourceCate.open();
		try{
			Publics.list_Category = dataSourceCate.getAllCategorys();
		}catch(Exception ex)	
		{
			ex.printStackTrace();
			dataSourceCate.close();
		}
		if(Publics.list_Category.size() == 0)
		{
			Category cate = new Category("An uong", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Dam tiec", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Mua sam", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Luong", "Thu nhap", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Giai tri", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Sua chua", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Phi", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			cate = new Category("Nhien lieu", "Chi tieu", "");
			dataSourceCate.insertCategory(cate);
			
			Publics.list_Category = dataSourceCate.getAllCategorys();
		}
		dataSourceCate.close();
		
		/**Processing Bill*/
		dataSourceBill = new BillDataSource(this);
		Publics.list_Bill = new ArrayList<Bill>();
		try{
			dataSourceBill.open();
			Publics.list_Bill = dataSourceBill.getAllBills();
			dataSourceBill.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dataSourceBill.close();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general);
		
		/**Get id and process*/
		Publics.tabHost = getTabHost();
		//Tab Account
		TabSpec specAccount = Publics.tabHost.newTabSpec("Account");
		specAccount.setIndicator("Account", getResources().getDrawable(R.drawable.account_45));
		Intent iAccount = new Intent(getApplicationContext(), ManageAccount.class);
		specAccount.setContent(iAccount);
		//Tab Transaction
		TabSpec specTransaction = Publics.tabHost.newTabSpec("Transaction");
		specTransaction.setIndicator("Transaction",  getResources().getDrawable(R.drawable.transaction_45));
		Intent iTransaction = new Intent(getApplicationContext(), ManageTransaction.class);
		specTransaction.setContent(iTransaction);
		//Tab Menu
		TabSpec specMenu = Publics.tabHost.newTabSpec("Menu");
		specMenu.setIndicator("Menu", getResources().getDrawable(R.drawable.home_45));
		Intent iMenu = new Intent(getApplicationContext(), GeneralMenu.class);
		specMenu.setContent(iMenu);
		
//		Tab Report
		TabSpec specReport = Publics.tabHost.newTabSpec("Report");
		specReport.setIndicator("Report",  getResources().getDrawable(R.drawable.report_48));
		Intent iReport = new Intent(getApplicationContext(), GraphicActivity.class);
		specReport.setContent(iReport);
		
		//Tab Bill
		TabSpec specBill = Publics.tabHost.newTabSpec("Bill");
		specBill.setIndicator("Bill",  getResources().getDrawable(R.drawable.bill_45));
		Intent iBill = new Intent(getApplicationContext(), ManageBillReminder.class);
		specBill.setContent(iBill);
		
		/**Set function for component*/
		// Adding all TabSpec to TabHost
		Publics.tabHost.addTab(specAccount);
		Publics.tabHost.addTab(specTransaction);
		Publics.tabHost.addTab(specBill);
		Publics.tabHost.addTab(specReport);
		Publics.tabHost.addTab(specMenu);
		/**Set color*/
		Publics.tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#9AD966"));
		Publics.tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#9AD966"));
		Publics.tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#9AD966"));
		Publics.tabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.parseColor("#9AD966"));
		Publics.tabHost.getTabWidget().getChildAt(4).setBackgroundColor(Color.parseColor("#9AD966"));
		
		//Switch tab
		Intent it = getIntent();
		int cTab = it.getIntExtra("tab", 0);
		
		if (cTab==1) //Account
		{
			Toast.makeText(this, String.valueOf(cTab), Toast.LENGTH_SHORT).show();
			Publics.tabHost.setCurrentTabByTag("Account");
			
		}
		else if (cTab==2) //transaction
		{
			Toast.makeText(this, String.valueOf(cTab), Toast.LENGTH_SHORT).show();
			Publics.tabHost.setCurrentTabByTag("Transaction");
			
		}
		else if (cTab==3) //bill
		{
			Toast.makeText(this, String.valueOf(cTab), Toast.LENGTH_SHORT).show();
			Publics.tabHost.setCurrentTabByTag("Bill");
			
		}
		else if (cTab==4) //report
		{
			Toast.makeText(this, String.valueOf(cTab), Toast.LENGTH_SHORT).show();
			Publics.tabHost.setCurrentTabByTag("Report");
			
		}
		else if (cTab==5) //menu
		{
			Toast.makeText(this, String.valueOf(cTab), Toast.LENGTH_SHORT).show();
			Publics.tabHost.setCurrentTabByTag("Menu");
			
		}
	}
	
	@Override
	  protected void onResume() {
		dataSourceCate.open();
		dataSourceAcc.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
		dataSourceCate.close();
		dataSourceAcc.close();
	    super.onPause();
	  }
	  
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add("Exit");
			return super.onCreateOptionsMenu(menu);
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getTitle() == "Exit")//exit
			{
				Intent intent = new Intent(Intent.ACTION_MAIN);
			    intent.addCategory(Intent.CATEGORY_HOME);
			    startActivity(intent);
			    finish();
			}
			return super.onOptionsItemSelected(item);
		}
		
		protected Dialog onCreateDialog(int id) {
			switch(id)
			{
				case 1:
				{
					 LayoutInflater factory = LayoutInflater.from(General.this);
			         final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
			         final EditText name = (EditText)textEntryView.findViewById(R.id.edt_UN);
                     final EditText pass = (EditText)textEntryView.findViewById(R.id.edt_P);
			         return new AlertDialog.Builder(General.this)
			             .setTitle("Register please!")
			             .setView(textEntryView)
			             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                 public void onClick(DialogInterface dialog, int whichButton) {
			                     Publics.UseName = name.getText().toString().trim();
			                     Publics.Password = pass.getText().toString().trim();
			                     if(Publics.UseName.equals("")==false)
			                     {
				                    Publics.list_Setting.get(0).setValue(Publics.UseName);
				                    Publics.list_Setting.get(1).setValue(Publics.Password);
			                    	 SettingDataSource sdt = new SettingDataSource(General.this);
				         			try{
				         				sdt.open();
				         				sdt.updateSetting(Publics.list_Setting.get(0));
				         				sdt.updateSetting(Publics.list_Setting.get(1));
				         				sdt.close();
				         			}catch(Exception ex)
				         			{
				         				ex.printStackTrace();
				         				sdt.close();
				         			} 
			                     }
			                 }
			             })
			             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			                 public void onClick(DialogInterface dialog, int whichButton) {
			                	 dialog.cancel();
			                 }
			             })
			             .create();
				}
				case 2:
				{
					LayoutInflater factory = LayoutInflater.from(General.this);
			         final View textEntryView = factory.inflate(R.layout.login, null);
			         return new AlertDialog.Builder(General.this)
			             .setTitle("Login")
			             .setView(textEntryView)
			             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                 public void onClick(DialogInterface dialog, int whichButton) {
			                     EditText pass = (EditText)textEntryView.findViewById(R.id.edt_PLG);
			                     if(checkLogin(pass.getText().toString().trim()) == false)
			                     {
			                    	 finish();
			                    	 General.this.startActivity(getIntent()); 
			                     }
			                 }
			             })
			             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			                 public void onClick(DialogInterface dialog, int whichButton) {
			                	 dialog.cancel();
			                	 Intent intent = new Intent(Intent.ACTION_MAIN);
		         			     intent.addCategory(Intent.CATEGORY_HOME);
		         			     startActivity(intent);
		         			     finish();
			                 }
			             })
			             .create();
				}
			}
			return null;
	    	 
	    }
		
		/**Check first use*/
		private boolean checkFirst(int id)
		{
			SettingDataSource sdt = new SettingDataSource(General.this);
			try{
				sdt.open();
				Setting s = sdt.getSettingById(id);
				sdt.close();
				Publics.UseName = s.getValue();
				if(Publics.UseName.equals(""))
					return true;
				else
					return false;
			}catch(Exception ex)
			{
				ex.printStackTrace();
				sdt.close();
				return true;
			}
		}
		
		
		/**Check login*/
		private boolean checkLogin(String pass)
		{
			SettingDataSource sdt = new SettingDataSource(General.this);
			try{
				sdt.open();
				Publics.Password = sdt.getSettingById(2).getValue();
				sdt.close();
				if(pass.equals(Publics.Password))
				{
					return true;
				}
				else
				{
					return false;
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
				sdt.close();
				return false;
			}
		}
}

