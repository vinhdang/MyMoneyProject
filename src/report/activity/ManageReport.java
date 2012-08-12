package report.activity;

import java.util.ArrayList;

import publics.Publics;
import main.activity.R;
import model.category.Category;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ManageReport extends TabActivity {
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_all);
		Publics.list_Category = new ArrayList<Category>();
		Publics.list_Category.add(new Category("Giai tri", "Chi tieu", ""));
		Publics.list_Category.add(new Category("Luong", "Thu nhap", ""));
		Publics.list_Category.add(new Category("Nhien lieu", "Chi tieu", ""));
		Publics.list_Category.add(new Category("An uong", "Chi tieu", ""));
		Publics.list_Category.add(new Category("Sua chua", "Chi tieu", ""));
		Publics.list_Category.add(new Category("Dam tiec", "Chi tieu", ""));
		
		/**Get id and process*/
		tabHost = getTabHost();
		//Tab Expense
		TabSpec specExpense = tabHost.newTabSpec("Expense");
		specExpense.setIndicator("Expense");
		Intent iExpense = new Intent(getApplicationContext(), ReportExpense.class);
		specExpense.setContent(iExpense);
		//Tab Income
		TabSpec specIncome = tabHost.newTabSpec("Income");
		specIncome.setIndicator("Income");
		Intent iIncome = new Intent(getApplicationContext(), ReportIncome.class);
		specIncome.setContent(iIncome);
		//Tab Income
		TabSpec specMonth = tabHost.newTabSpec("Monthly");
		specMonth.setIndicator("Monthly");
		Intent iMonth = new Intent(getApplicationContext(), ReportMonthly.class);
		specMonth.setContent(iMonth);
		
		
		/**Set function for component*/
		// Adding all TabSpec to TabHost
		tabHost.addTab(specExpense);
		tabHost.addTab(specIncome);
		tabHost.addTab(specMonth);
	}
}
