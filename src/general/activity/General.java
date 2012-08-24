package general.activity;

import java.util.ArrayList;
import billreminder.activity.ManageBillReminder;
import publics.Publics;
import report.activity.GraphicActivity;
import transaction.activity.ManageTransaction;
import main.activity.R;
import model.account.Account;
import model.account.AccountDataSource;
import model.category.Category;
import model.category.CategoryDataSoure;
import model.transaction.Transaction;
import account.activity.ManageAccount;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class General extends TabActivity {
	public static TabSpec mTabCurrent;
	private AccountDataSource dataSourceAcc;
	private CategoryDataSoure dataSourceCate;
//	private TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/**Create data*/
		Publics.list_Transaction = new ArrayList<Transaction>();		
		Publics.list_Transaction.add(new Transaction("Luong Lotteria", "29/7/2012", (double) 1500, "Luong", "HSBC"));
		Publics.list_Transaction.add(new Transaction("Day them", "29/7/2012", (double) 1000, "Luong", "HSBC"));
		Publics.list_Transaction.add(new Transaction("An sang", "9/7/2012", (double) 15, "An uong", "Dong A"));
		Publics.list_Transaction.add(new Transaction("Do xang", "9/7/2012", (double) 50, "Nhien lieu", "Dong A"));
		Publics.list_Transaction.add(new Transaction("Com trua", "9/7/2012", (double) 30, "An uong", "Dong A"));
		Publics.list_Transaction.add(new Transaction("Cafe", "9/7/2012", (double) 10, "An uong", "Dong A"));
		Publics.list_Transaction.add(new Transaction("Luong Lotteria", "29/6/2012", (double) 1500, "Luong", "HSBC"));	
		Publics.list_Transaction.add(new Transaction("Dong tien hoc", "25/6/2012", (double) 2000, "Phi", "ACB"));
		Publics.list_Transaction.add(new Transaction("Tien Dien", "25/6/2012", (double) 800, "Phi", "ACB"));
		Publics.list_Transaction.add(new Transaction("Tien nuoc", "24/6/2012", (double) 200, "Phi", "HSBC"));
		Publics.list_Transaction.add(new Transaction("Xem phim", "24/6/2012", (double) 150, "Giai tri", "HSBC"));
		Publics.list_Transaction.add(new Transaction("Dam cuoi", "22/6/2012", ((double) 500), "Dam tiec", "ACB"));
		Publics.list_Transaction.add(new Transaction("Sua xe", "22/6/2012", (double) 100, "Sua chua", "ACB"));
		Publics.list_Transaction.add(new Transaction("Mua sam","19/6/2012",(double) 180,"Mua sam", "HSBC"));
		Publics.list_Transaction.add(new Transaction("Mua xe","18/7/1991",(double) 25000, "Mua sam", "HSBC"));
		Publics.list_Transaction.add(new Transaction("An Sang","14/6/2012",(double) 5,"An uong", "Dong A"));
		Publics.list_Transaction.add(new Transaction("Xem phim","14/6/2012",(double) 55,"Giai tri", "HSBC"));
		
		Publics.list_Account = new ArrayList<Account>();
		dataSourceAcc = new AccountDataSource(this);
		dataSourceAcc.open();
		try{
			Publics.list_Account = dataSourceAcc.getAllAccounts();
		}catch(Exception ex)	
		{
			ex.printStackTrace();
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
//		Publics.list_Account.add(new Account("Dong A", 5000, "VND"));
//		Publics.list_Account.add(new Account("HSBC", 10000, "VND"));
//		Publics.list_Account.add(new Account("ACB", 2000, "VND"));
		
		Publics.list_Category = new ArrayList<Category>();
		dataSourceCate = new CategoryDataSoure(this);
		dataSourceCate.open();
		try{
			Publics.list_Category = dataSourceCate.getAllCategorys();
		}catch(Exception ex)	
		{
			ex.printStackTrace();
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
//		Publics.list_Category.add(new Category("An uong", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Dam tiec", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Mua sam", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Giai tri", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Sua chua", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Phi", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Nhien lieu", "Chi tieu", ""));
//		Publics.list_Category.add(new Category("Luong", "Thu nhap", ""));
		
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
		
		//Tab Report
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
}

