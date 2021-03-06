package general.activity;

import java.util.ArrayList;
import java.util.List;
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
import model.repeat.Repeat;
import model.repeat.RepeatDataSource;
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
import android.widget.TabHost.TabSpec;

import model.transaction.TransactionDataSource;

public class General extends TabActivity {
	public static TabSpec mTabCurrent;
	private AccountDataSource dataSourceAcc;
	private CategoryDataSoure dataSourceCate;
	private BillDataSource dataSourceBill;
	private SettingDataSource dataSourceSet;
	private TransactionDataSource dataSourceTrans;
	private String curDate = "";
	private String past = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		curDate = Publics.getCurrentDay(); // get current date
		Publics.makeFolder(); //create folder MyMoney if not yet
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
				Publics.list_Setting.add(new Setting(7, "Language", "English"));//Save language
				Publics.list_Setting.add(new Setting(8, "Backup", ""));//Save schedule backup auto
				Publics.list_Setting.add(new Setting(9, "DateAccess", curDate));//Save schedule backup auto
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
			else
			{
				dataSourceSet.open();
				past = Publics.list_Setting.get(Publics.DateAccess_num).getValue(); //get old value
				Publics.list_Setting.get(Publics.DateAccess_num).setValue(curDate); // set new value
				dataSourceSet.updateSetting(Publics.list_Setting.get(Publics.DateAccess_num));
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
			String p = Publics.list_Setting.get(1).getValue();
			if(p.equals("") == false)
				if(Publics.login == -1)
					this.showDialog(2);//login
		}
		
		/**check and autobackup if have*/
		int c = checkBackup(Publics.list_Setting);
		autoB(c); // auto backup
		autoAddTransactionRepeat();
		
		/**get all transaction had*/
		Publics.list_Transaction = new ArrayList<Transaction>();
		dataSourceTrans = new TransactionDataSource(this);
		try{
			dataSourceTrans.open();
			Publics.list_Transaction = dataSourceTrans.getAllTransactions();
			if(Publics.list_Transaction.size() == 0)
			{
//				Transaction a = new Transaction("An bun bo", "13/07/2012", (double)20000, "An uong", "Dong A", "An ngon wa",  "Tien mat", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Luong Lotteria", "29/07/2012", (double) 1500, "Luong", "HSBC", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Cafe", "9/07/2012", (double) 10, "An uong", "Dong A", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Luong Lotteria", "29/06/2012", (double) 1500, "Luong", "HSBC", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Dong tien hoc", "25/06/2012", (double) 2000, "Phi", "ACB", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Tien Dien", "25/06/2012", (double) 800, "Phi", "ACB", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Mua sam","19/06/2012",(double) 180,"Mua sam", "HSBC", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				a = new Transaction("Mua xe","18/07/1991",(double) 25000, "Mua sam", "HSBC", "", "Card", "None");
//				dataSourceTrans.insertTransaction(a);
//				Publics.list_Transaction = dataSourceTrans.getAllTransactions();
			}
			dataSourceTrans.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dataSourceTrans.close();
		}
		/**Processing Account*/
		Publics.list_Account = new ArrayList<Account>();
		dataSourceAcc = new AccountDataSource(this);
		try{
			dataSourceAcc.open();
			Publics.list_Account = dataSourceAcc.getAllAccounts();
			if(Publics.list_Account.size() == 0)
			{
//				Account a = new Account("Dong A", (double)5000, "VND", "");
//				dataSourceAcc.insertAccount(a);
//				a = new Account("HSBC", (double)10000, "VND", "");
//				dataSourceAcc.insertAccount(a);
//				a = new Account("ACB", (double)2000, "VND", "");
//				dataSourceAcc.insertAccount(a);
//				Publics.list_Account = dataSourceAcc.getAllAccounts();
			}
			dataSourceAcc.close();
		}catch(Exception ex)	
		{
			ex.printStackTrace();
			dataSourceAcc.close();
		}
		/**Processing Category*/
		Publics.list_Category = new ArrayList<Category>();
		dataSourceCate = new CategoryDataSoure(this);
		dataSourceCate.open();
		try{
			Publics.list_Category = dataSourceCate.getAllCategorys();
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
		}catch(Exception ex)	
		{
			ex.printStackTrace();
			dataSourceCate.close();
		}
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
			Publics.tabHost.setCurrentTabByTag("Account");
		}
		else if (cTab==2) //transaction
		{
			Publics.tabHost.setCurrentTabByTag("Transaction");
		}
		else if (cTab==3) //bill
		{
			Publics.tabHost.setCurrentTabByTag("Bill");
		}
		else if (cTab==4) //report
		{
			Publics.tabHost.setCurrentTabByTag("Report");
		}
		else if (cTab==5) //menu
		{
			Publics.tabHost.setCurrentTabByTag("Menu");
		}
	}
	
	@Override
	  protected void onResume() {
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
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
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			    General.this.finish();
			    System.exit(0);
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
				         				Publics.login = 1; 
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
			                	 Intent intent = new Intent(Intent.ACTION_MAIN);
			                	 intent.addCategory(Intent.CATEGORY_HOME);
			                	 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			                	 startActivity(intent);
		         			     finish();
		         			     System.exit(0);
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
			                	 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			                	 startActivity(intent);
		         			     finish();
		         			     System.exit(0);
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
				if(Publics.Password.toString().equals(pass.toString()) == true)
				{
					Publics.login = 1;
					return true;
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
				sdt.close();
			}
			return false;
		}
		
		/**Check autobackup*/
		private int checkBackup(List<Setting> lt)
		{
			if(lt.size() > 0)
			{
				Setting s = lt.get(Publics.Backup_num);
				if(s.getValue().equals("None"))
					return 0;
				else if(s.getValue().equals("Daily"))
					return 1;
				else if(s.getValue().equals("Weekly"))
					return 2;
				else if(s.getValue().equals("Monthly"))
					return 3;
				else
					return 0;
			}
			return 0;
		}
		
		/**Process backup*/
		private void autoB(int flag)
		{
			long rs = 0;
//			String past = Publics.list_Setting.get(Publics.DateAccess_num).getValue();
			rs = Publics.CalculateDate(curDate, past);
			switch (flag) {
				case 0 : break;
				case 1: // daily
				{
					if(rs == 1)
					{
						Publics.AutoBackup();
					}
				}break;
				case 2: // weekly
				{
					if(rs == 7)
					{
						Publics.AutoBackup();
					}
				}break;
				case 3: // monthly
				{
					if(rs == 29)
					{
						Publics.AutoBackup();
					}
				}break;
				default: break;
			}
		}
		
		/**Auto insert transaction if have repeat*/
		private void autoAddTransactionRepeat()
		{
			List<Repeat> list = new ArrayList<Repeat>();
			RepeatDataSource rds = new RepeatDataSource(General.this);
			try{
				rds.open();
				list = rds.getAllRepeats(); // get all to process transaction repeat
				rds.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
				rds.close();
			}
			if(list.size() > 0)
			{
				TransactionDataSource tds = new TransactionDataSource(General.this);
				long rs = 0;
				try{
					tds.open();
					rds.open();
					for(Repeat r : list) // processs each transaction repeat
					{
						Transaction temp = tds.getTransactionById(r.getItem_id());
						if(temp != null)
						{
							if(temp.getTransactionRepeat().equals("Daily"))
							{
								rs = Publics.CalculateDate(curDate, r.getSetupDate());
								if(rs == 1)
								{
									temp = tds.createTransaction(temp);//
									r.setItem_id(temp.getTransactionId());//
									r.setSetupDate(curDate);
									rds.updateRepeat(r);
								}
							}
							else if(temp.getTransactionRepeat().equals("Weekly"))
							{
								rs = Publics.CalculateDate(curDate, r.getSetupDate());
								if(rs == 7)
								{
									tds.insertTransaction(temp);
									r.setSetupDate(curDate);
									rds.updateRepeat(r);
								}
							}
							else if(temp.getTransactionRepeat().equals("Monthly"))
							{
								rs = Publics.CalculateDate(curDate, r.getSetupDate());
								if(rs == 29)
								{
									tds.insertTransaction(temp);
									r.setSetupDate(curDate);
									rds.updateRepeat(r);
								}
							}
						}
					}
					tds.close();
					rds.close();
				}catch(Exception ex)
				{
					ex.printStackTrace();
					tds.close();
					rds.close();
				}
			}
		}
}

