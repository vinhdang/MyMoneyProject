package publics;

import general.activity.General;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import main.activity.R;
import model.account.Account;
import model.category.Category;
import model.transaction.Transaction;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

public class Publics {
	public final static  String[]  listSpinnerView = {"All", "Daily","Category","Compare Plan"};
	public final static  String[]  listGridMenuTool = {"Backup","Restore","Import","Export","Exchange"}; 
	public final static  String[]  listCurrency = {"VND", "USD-US", "USD-AUS", "USD-CAN", "EURO", "YUAN"};
	public final static  String[]  listLanguage = {"Vietnamese", "English", "France"};
	public final static  String[]  listDateFormat = {"dd/mm/yyyy", "mm/dd/yyyy"};
	public final static  String[]  listCategoryType = {"Chi tieu", "Thu nhap"};
	public final static  String[]  listMonth = {"6", "7"};
	public final static	 String[]  listDay = {"1 day", "2 day", "1 week", "1 month"};
	
	public static  List<String>  list_File;
	public static List<Transaction> list_Transaction;
	public static List<Category> list_Category; 
	public static List<Account> list_Account;
	public static List<String>	list_Plan;
	public static TabHost tabHost;
	
	public static int paramToMngTrans = -1;
	public static int Protect = -1;
	//VuTuyen
	public static final int REQ_NEW_ACCOUNT = 				1;
	public static final int CODE_SUCCESS = 					2;
	
	public static final int REQ_UPDATE_ACCOUNT = 			3;
	public static final int REQ_VIEW_ACCOUNT = 				4;
	public static final int REQ_VIEW_TRANSACTION = 			5;
	public static final int REQ_NEW_TRANSACTION = 			6;
	public static final int REQ_CHANGE_PASS = 				7;
	
	public static final String[] POPUP_MENU = {"Update", "View", "Delete", "View transaction"};
////////////////////////////////////////// functions //////////////////////////////////////////////////////
	/**Message box*/
	public static void msgBoxDelete(Context context, String msg, DialogInterface.OnClickListener evtOK, DialogInterface.OnClickListener evtCancel)
	{
		//Builder MessageBox
				final AlertDialog.Builder buildMSG = new AlertDialog.Builder(context);
				buildMSG.setTitle("Delete...");
				buildMSG.setMessage(msg);
				buildMSG.setPositiveButton("OK",evtOK);
				buildMSG.setNegativeButton("Cancel", evtCancel);
				buildMSG.create().show();
				//end builder
	}
	
	/**function in the top*/
	public static void topFunction(final Context c)
	{
		Button acc = (Button)((Activity)c).findViewById(R.id.btn_account);
		Button trans = (Button)((Activity)c).findViewById(R.id.btn_transaction);
		Button bill = (Button)((Activity)c).findViewById(R.id.btn_bill);
		Button report = (Button)((Activity)c).findViewById(R.id.btn_report);
		Button menu = (Button)((Activity)c).findViewById(R.id.btn_menu);
		
		acc.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(c,General.class);
				intent.putExtra("tab", 1);
				c.startActivity(intent);
				
			}
		});
		
		trans.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(c,General.class);
				intent.putExtra("tab", 2);
				c.startActivity(intent);
				
			}
		});
		
		bill.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(c,General.class);
				intent.putExtra("tab", 3);
				c.startActivity(intent);
				
			}
		});
		
		report.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(c,General.class);
				intent.putExtra("tab", 4);
				c.startActivity(intent);
				
			}
		});
		
		menu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(c,General.class);
				intent.putExtra("tab", 5);
				c.startActivity(intent);
				
			}
		});
	}
	
	/**return home*/
	public static void bottomFunction(final Context c)
	{
		
//		//home
//		((Activity)c).findViewById(R.id.ib_home).setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				Intent intent = new Intent(c, General.class);
//				Toast.makeText(c.getApplicationContext(), "Start Manage Home ...", Toast.LENGTH_SHORT).show();
//				c.startActivity(intent);
//				
//			}
//		});
//		//category
//		((Activity)c).findViewById(R.id.ib_category).setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				Intent intent = new Intent(c,ManageCategory.class);
//				Toast.makeText(c.getApplicationContext(), "Start Manage Category ...", Toast.LENGTH_SHORT).show();
//				//General.mTabCurrent.setContent(intent);
//				c.startActivity(intent);
//			}
//		});
//		//settings
//		((Activity)c).findViewById(R.id.ib_settings).setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				Intent intent = new Intent(c,ManageSetting.class);
//				Toast.makeText(c.getApplicationContext(), "Start Manage Settings ...", Toast.LENGTH_SHORT).show();
//				//General.mTabCurrent.setContent(intent);
//				c.startActivity(intent);
//			}
//		});
//		
//		//transaction
//		((Activity)c).findViewById(R.id.ib_transaction).setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				
//				Intent intent = new Intent(c,General.class);
//				//Toast.makeText(c.getApplicationContext(), "Start Manage Transaction ...", Toast.LENGTH_SHORT).show();
//				//General.mTabCurrent.setContent(intent);
//				//((Activity)c).finish();
//				//Publics.tabHost.setCurrentTabByTag("Transaction");
//				intent.putExtra("tab", 2);
//				c.startActivity(intent);
//				
//			}
//		});
	}
	
	/**Get unique date from list*/
	public static List<String> getUniqueDate(List<String> dates)
	{
		List<String> rs = new ArrayList<String>();
		for(String t : dates)
		{
			if(!rs.contains(t))
			{
				rs.add(t);
			}
		}
		return rs;
	}
		
	/**get unique month*/
	public static List<String> getUniqueMonth(List<String> month)
	{
		List<String> rs = new ArrayList<String>();
		for(String t : month)
		{
			String[] tmp = t.split("/");
			String k = tmp[1]+"/" + tmp[2];
			if(!rs.contains(k))
			{
				rs.add(tmp[1] + "/" + tmp[2]);
			}
		}
		return rs;
	}
	
	/**format number*/
	public static String formatNumber(double s)
	{
		DecimalFormat nf = new DecimalFormat("###,###");
		return nf.format(s);
	}
}
