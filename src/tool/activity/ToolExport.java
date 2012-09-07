package tool.activity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import publics.CSVWriter;
import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.bill.Bill;
import model.transaction.Transaction;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class ToolExport extends Activity{
	private Button btn_export;
	private CheckBox chk_all;
	private Spinner spn_table;
	String[] table = {"Account", "Transaction", "Bill"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_export);
		
		btn_export = (Button)findViewById(R.id.btn_toolExport);
		spn_table = (Spinner)findViewById(R.id.spn_table);
		chk_all = (CheckBox)findViewById(R.id.chk_All);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, table);
		
		/***/
		btn_export.setOnClickListener(handleExport);
		spn_table.setAdapter(adapter);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/***/
	OnClickListener handleExport = new OnClickListener() {
		
		public void onClick(View v) 
		{
			if(chk_all.isChecked())
			{
				List<String> header = new ArrayList<String>();
				header.add("Id"); header.add("Name"); header.add("FinalBalance");
				header.add("Unit"); header.add("Description");
				createCSV(Publics.list_Account, header, 0);
				
				header = new ArrayList<String>();
				header.add("Id"); header.add("Name"); header.add("Date"); header.add("Category");
				header.add("Account"); header.add("Amount"); header.add("PayMode"); header.add("Note");  
				createCSV(Publics.list_Transaction, header, 1);
				
				header = new ArrayList<String>();
				header.add("Id"); header.add("Name"); header.add("Category");
				header.add("Amount"); header.add("DueDate"); header.add("Note");  
				createCSV(Publics.list_Bill, header, 2);
			}
			else
			{
				int pos = spn_table.getSelectedItemPosition();
				switch (pos) 
				{
					case 0://account
					{
						List<String> header = new ArrayList<String>();
						header.add("Id"); header.add("Name"); header.add("FinalBalance");
						header.add("Unit"); header.add("Description");
						createCSV(Publics.list_Account, header, pos);
					}break;
					case 1://transaction
					{
						List<String> header = new ArrayList<String>();
						header.add("Id"); header.add("Name"); header.add("Date"); header.add("Category");
						header.add("Account"); header.add("Amount"); header.add("PayMode"); header.add("Note");  
						createCSV(Publics.list_Transaction, header, pos);
					}break;
					case 2://bill
					{
						List<String> header = new ArrayList<String>();
						header.add("Id"); header.add("Name"); header.add("Category");
						header.add("Amount"); header.add("DueDate"); header.add("Note");  
						createCSV(Publics.list_Bill, header, pos);
					}break;
					default: break;
				}
			}
			Intent i = new Intent(getApplicationContext(), ManageTool.class);
			Toast.makeText(getApplicationContext(), "Exported", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	//Export contact to file
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createCSV(List<?> list, List<String> header, int tp) {
		String[] t = Publics.getCurrentDay().split("/");
		String date = t[0] + t[1] + t[2];
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory().
            		getAbsolutePath() + "/MyMoney/" + "_" + table[tp] + "_" + date + ".csv"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        writer.writeColumnNames(header); // Write column header
        if(list.size() > 0)
        {
            for (int i = 0 ; i < list.size() ; i++ ) 
            {
            	List<String> tmp = new ArrayList<String>();
            	tmp = getAtrr(list.get(i), tp);
            	writer.writeNext(tmp);
			}    
        }
       try {
            if(writer != null)
                writer.close();
        } catch (IOException e) {
           Log.w("createCSV", e.toString());
        }
   }
	
	/**Get attribute of entity*/
	private List<String> getAtrr(Object object, int type)
	{
		List<String> rs = new ArrayList<String>();
		switch(type)
		{
			case 0: //account
			{
				Account a = (Account) object;
				rs.add(String.valueOf(a.getId()));
				rs.add(a.getAccountName());
				rs.add(String.valueOf(a.getFinalBalance()));
				rs.add(a.getUnit());
				rs.add(a.getDescript());
			}break;
			case 1: //transaction
			{
				Transaction tr = (Transaction) object;
				rs.add(String.valueOf(tr.getTransactionId()));
				rs.add(tr.getTransactionItem());
				rs.add(tr.getTransactionDate());
				rs.add(tr.getTransactionCategory());
				rs.add(tr.getTransactionAccount());
				rs.add(String.valueOf(tr.getTransactionAmount()));
				rs.add(tr.getTransactionPaymode());
				rs.add(tr.getTransactionNote());
			}break;
			case 2: //bill
			{
				Bill b = (Bill) object;
				rs.add(String.valueOf(b.getBillId()));
				rs.add(b.getBillItem());
				rs.add(b.getBillCategory());
				rs.add(String.valueOf(b.getBillAmount()));
				rs.add(b.getBillDueDay());
				rs.add(b.getBillNote());
			}break;
			default : break;
		}
		return rs;
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
			    finish();
			    System.exit(0);
			}
			return super.onOptionsItemSelected(item);
		}
}
