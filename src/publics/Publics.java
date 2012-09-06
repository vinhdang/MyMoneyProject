package publics;

import general.activity.General;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import main.activity.R;
import model.account.Account;
import model.bill.Bill;
import model.category.Category;
import model.plan.Plan;
import model.setting.Setting;
import model.transaction.Transaction;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

public class Publics {
	public final static  String[]  listSpinnerView = {"All", "Daily","Category"};
	public final static  String[]  listGridMenuTool = {"Backup","Restore","SendFileToEmail","Export","Exchange"}; 
	public final static  String[]  listCurrency = {"VND", "USD-US"};
	public final static  String[]  listLanguage = {"Vietnamese", "English"};
	public final static  String[]  listDateFormat = {"dd/MM/yyyy", "MM/dd/yyyy"};
	public final static  String[]  listCategoryType = {"Chi tieu", "Thu nhap"};
//	public final static	 String[]  listDay = {"1 day", "2 day", "1 week", "1 month"};
	public final static  String[]  listColor = {"-1843419","-14300190","-1044291","-11994766",
		"-14922743","-7424497","-2417081","-8073554","-7229610","-2005138","-4192165", 
		"-13481860","-4693234","-3475868","-15652540"};
	
	public final static	 String[]  listPayMode = {"Cash", "Credit Card", "Check"};
	public final static	 String[]  listRepeat = {"None", "Daily","Weekly","Monthly"};
	
	public static List<Transaction> list_Transaction;
	public static List<Category> list_Category; 
	public static List<Account> list_Account;
	public static List<Plan>	list_Plan;
	public static List<Bill>	list_Bill;
	public static List<Setting> list_Setting;
	public static TabHost tabHost;
	
	public static int paramToMngTrans = -1;
	public static String UseName = "";
	public static String Password = "";
	public static int login = -1;
	public static String FormatDate = "dd/MM/yyyy";
	
	public static int Pass_num = 1;
	public static int UpdateDate_num = 2;
	public static int RateSell_num = 3;
	public static int RateBuy_num = 4;
	public static int DateFormat_num = 5;
	public static int Language_num = 6;
	public static int Backup_num = 7;
	public static int DateAccess_num = 8;
	
	//VuTuyen
	public static final int REQ_NEW_ACCOUNT = 				1;
	public static final int CODE_SUCCESS = 					2;
	public static final int REQ_UPDATE_ACCOUNT = 			3;
	public static final int REQ_VIEW_ACCOUNT = 				4;
	public static final int REQ_VIEW_TRANSACTION = 			5;
	public static final int REQ_NEW_TRANSACTION = 			6;
	public static final int REQ_CHANGE_PASS = 				7;
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
	
	/**Get unique date from list*/
	public static List<String> getUniqueDate(List<String> dates)
	{
		List<String> rs = new ArrayList<String>();
		ArrayList al = new ArrayList();
		for(String t : dates)
		{
			if(!rs.contains(t))
			{
				rs.add(t);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FormatDate);
		for(String r : rs)
		{
			Date master = sdf.parse(r, new ParsePosition(0));
			al.add(master);
		}
		if(al.size() > 0)
		{
			Object[] arr = al.toArray();
			Arrays.sort(arr);
			rs.clear();
			sdf = new SimpleDateFormat(FormatDate);
	        for (int i = 0; i < arr.length; i++) {
	            Date d = (Date)arr[i];
	            String formattedDate = sdf.format(d);
	            rs.add(formattedDate);
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
	
	/**Check number*/
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);
	    if(d >= 0)
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	}
	
	/**Get current date*/
	public static  String getCurrentDay()
	{
		 String date;
		 final Calendar cal = Calendar.getInstance();
	     int  pYear = cal.get(Calendar.YEAR);
	     int  pMonth = cal.get(Calendar.MONTH);
	     int pDay = cal.get(Calendar.DAY_OF_MONTH);
	     StringBuilder builder = new StringBuilder()
				         .append(pDay).append("/")
				         .append(pMonth + 1).append("/")
				         .append(pYear);
	     date = builder.toString();
	     date = formatDate(FormatDate, date);
		return date;
	}
	
	/**format date*/
	public static String formatDate(String type, String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		Date d = new Date(date);
		return sdf.format(d);
	}
	
	/**Parse string to double*/
	public static double ParseStringToDouble(String number)
	{
		double rs = 0;
		String[] t = number.split(",");
		String tmp = "";
		for(String k : t)
		{
			tmp += k;
		}
		try{
			rs = Double.parseDouble(tmp.trim());
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return rs;
	}
	
	/**Calculate date*/
	public static long CalculateDate(String Now, String Past)
	{
		long ddays = 0 ;
		try{
			Calendar calendar1 = Calendar.getInstance();
		    Calendar calendar2 = Calendar.getInstance();
		    String[] tmp1 = Past.split("/");
		    calendar1.set(Calendar.YEAR, Integer.parseInt(tmp1[2]));
		    calendar1.set(Calendar.MONTH, (Integer.parseInt(tmp1[1]) - 1));
		    calendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp1[0]));
		    
		    String[] tmp2 = Now.split("/");
		    calendar2.set(Calendar.YEAR, Integer.parseInt(tmp2[2]));
		    calendar2.set(Calendar.MONTH, (Integer.parseInt(tmp2[1]) - 1));
		    calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp2[0]));
		    
		    long milsecs1= calendar1.getTimeInMillis();
		    long milsecs2 = calendar2.getTimeInMillis();
		    long diff = milsecs2 - milsecs1;
		    ddays = diff / (24 * 60 * 60 * 1000);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	    return ddays;
	}
	
	/**Auto backup*/
	public static void AutoBackup()
	{
		String[] t = Publics.getCurrentDay().split("/");
		String date = t[0] + t[1] + t[2];
		try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();
	        if (sd.canWrite()) {
	            String currentDBPath = "//data//main.activity//databases//Database.db";
	            String tmp = "Database" + "_" +date + ".db";
	            String backupDBPath = tmp;
	            File newFolder = new File("mnt/sdcard/MyMoney/");
//	            newFolder.mkdirs();
	            File currentDB = new File(data, currentDBPath);
	            File backupDB = new File(newFolder, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
	        }
	    } catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }		    
	}
	
	/**Make new folder if not exits*/
	public static void makeFolder()
	{
		File f = new File(Environment.getExternalStorageDirectory() + "/MyMoney");
		if(f.isDirectory() == false && f.exists() == false) 
		{
			f.mkdirs();
		}
	}
}
