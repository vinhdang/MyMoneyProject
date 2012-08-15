package transaction.activity;

import java.util.ArrayList;
import java.util.List;
import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.transaction.ExpandTransAdapter;
import model.transaction.Transaction;
import model.transaction.TransactionGroup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ManageTransaction extends Activity {
	private ExpandableListView elv_transaction;
	private Button btn_transactionNew;
	private Button btn_transactionNext;
	private Button btn_transactionPrev;
	private Spinner spn_Month;
	private ToggleButton tgl_Expand;
	private List<Transaction> list_trans;
	private List<Transaction> list_transMonth;
	private ArrayList<TransactionGroup> ExpListItems;
	private ExpandTransAdapter ExpAdapter;
	private ArrayAdapter<String> monthAdapter;
	private List<String> daily ;
	private List<String> month ;
	private int pos = -1;
	private static int indexMonth = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_list);
		list_trans = new ArrayList<Transaction>();
		list_transMonth = new ArrayList<Transaction>();
//		if(daily == null)
		daily = new ArrayList<String>();
		if(month == null)
			month = new ArrayList<String>();
		pos = Publics.paramToMngTrans;
		
		/***/
		elv_transaction = (ExpandableListView)findViewById(R.id.elv_transList);
		btn_transactionNew = (Button)findViewById(R.id.btn_transactionAddNew);
		btn_transactionNext = (Button)findViewById(R.id.btn_transNext);
		btn_transactionPrev = (Button)findViewById(R.id.btn_transPrev);
		spn_Month = (Spinner)findViewById(R.id.spn_transMonth);
		tgl_Expand = (ToggleButton)findViewById(R.id.toggleButton1);
		filterData(pos);// filter and set adapter		
		handleMonth();
		
		/**Set function*/
		btn_transactionNew.setOnClickListener(handleNew);
		elv_transaction.setAdapter(ExpAdapter);
		elv_transaction.setOnChildClickListener(handleClick);
		btn_transactionNext.setOnClickListener(handleNext);
		btn_transactionPrev.setOnClickListener(handlePrev);
		spn_Month.setOnItemSelectedListener(handleSelect);
		tgl_Expand.setOnClickListener(handleCheck);
	}
	
	/**Click Add New*/
	OnClickListener handleNew = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), TransactionNew.class);			
			Toast.makeText(getApplicationContext(), "Start Add New ....", Toast.LENGTH_SHORT).show();
			startActivityForResult(i, Publics.REQ_NEW_TRANSACTION);
		}
	};
	
	/**Click to view*/
	OnChildClickListener handleClick = new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v,
		int groupPosition, int childPosition, long id) {
			Intent i = new Intent(getApplicationContext(), TransactionDetail.class);			
			Toast.makeText(getApplicationContext(), "Start Add New ....", Toast.LENGTH_SHORT).show();
			i.putExtra("POS", childPosition);
			startActivity(i);
			return false;
		}
	};	
	
	/**Click next*/
	OnClickListener handleNext = new OnClickListener() {
		
		public void onClick(View v) {
			if(indexMonth < month.size()-1)
			{
				indexMonth ++;
				btn_transactionPrev.setVisibility(v.VISIBLE);
			}
			else
			{
				btn_transactionNext.setVisibility(v.GONE);
				btn_transactionPrev.setVisibility(v.VISIBLE);
			}
			spn_Month.setSelection(indexMonth);
			//process list view
			String key = spn_Month.getSelectedItem().toString();
			handleChoose(key);
			int len = ExpAdapter.getGroupCount();
			if(tgl_Expand.getText().toString().equals("Expand"))
			{
				for(int i=0; i<len; i++) 
		        {		            
		                elv_transaction.expandGroup(i);	      
		        }
			}
		}
	};
	
	/**Click previous*/
	OnClickListener handlePrev = new OnClickListener() {
		
		public void onClick(View v) {
			if(indexMonth > 0)
			{
				indexMonth --;
				btn_transactionNext.setVisibility(v.VISIBLE);
			}
			else
			{
				btn_transactionPrev.setVisibility(v.GONE);
				btn_transactionNext.setVisibility(v.VISIBLE);
			}
			spn_Month.setSelection(indexMonth);
			String key = spn_Month.getSelectedItem().toString();
			handleChoose(key);
			int len = ExpAdapter.getGroupCount();
			if(tgl_Expand.getText().toString().equals("Expand"))
			{
				for(int i=0; i<len; i++) 
		        {		            
		                elv_transaction.expandGroup(i);	      
		        }
			}
		}
	};
	
	/**Click button*/
	OnClickListener handleCheck = new OnClickListener() {
		
		public void onClick(View v) {
				int len = ExpAdapter.getGroupCount();
				if(tgl_Expand.getText().toString().equals("Expand"))
				{
					for(int i=0; i<len; i++) 
			        {		            
			                elv_transaction.expandGroup(i);	      
			        }
				}
		        else
				{
					for(int i=0; i<len; i++)
					{
			                elv_transaction.collapseGroup(i);
			        }
				}
		}
	};
	
	/**Click expand all*/
	OnGroupExpandListener handleExpand = new OnGroupExpandListener() {
		
		public void onGroupExpand(int groupPosition) {
			int len = ExpAdapter.getGroupCount();
			if(tgl_Expand.getText().toString().equals("Expand"))
			{
		        for(int i=0; i<len; i++) {
		            if(i != groupPosition) {
		                elv_transaction.expandGroup(i);
		            }
		        }
			}
			else
			{
				for(int i=0; i<len; i++) {
		            if(i != groupPosition) {
		                elv_transaction.collapseGroup(i);
		            }
		        }
			}
		}
	};
	
	/**Set group for expandalelistview*/
	  public ArrayList<TransactionGroup> SetStandardGroups(List<Transaction> listIn) {
		  ArrayList<TransactionGroup> list = new ArrayList<TransactionGroup>(); 
		  for(int i=0 ; i< daily.size() ; i++)
		  {
			  TransactionGroup gru = new TransactionGroup();
			  gru.setName(daily.get(i));
			  ArrayList<Transaction> list2 = new ArrayList<Transaction>();
			  for(int j=0 ; j<listIn.size() ; j++)
			  {
				  	Transaction tmp = listIn.get(j);
					if(tmp.getTransactionDate().equals(daily.get(i)))
					{
						list2.add(tmp);
					}
			  }
			  gru.setItems(list2);
			  list.add(gru);
		  }
		  return list;
	  }
	 
	/**Select item in spinner*/
	  OnItemSelectedListener handleSelect = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			indexMonth = spn_Month.getSelectedItemPosition();			
			//process
			String key = spn_Month.getSelectedItem().toString();
			handleChoose(key);
			int len = ExpAdapter.getGroupCount();
			if(tgl_Expand.getText().toString().equals("Expand"))
			{
				for(int i=0; i<len; i++) 
		        {		            
		                elv_transaction.expandGroup(i);	      
		        }
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	  
	  
	/***/
	@Override
	protected void onResume() {
		if (Publics.paramToMngTrans!=-1)
		{
			//exe code here
			Toast.makeText(this, String.valueOf(Publics.paramToMngTrans), Toast.LENGTH_SHORT).show();
			filterData(Publics.paramToMngTrans);
			handleMonth();
			elv_transaction.setAdapter(ExpAdapter);
		}
		//restore param
		Publics.paramToMngTrans = -1;
		super.onResume();
	}
	
	/** Wait for result from add new activity*/
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == Activity.RESULT_CANCELED) {
			 //
		 }
		 else if(requestCode == Publics.REQ_NEW_TRANSACTION )
		 {
			 Toast.makeText(getApplicationContext(), "Update list.....", Toast.LENGTH_SHORT).show();
		 }
	}
	
	private void filterData(int p)
	{
		if(p != -1)
		{
			Account acc = Publics.list_Account.get(p);
			if(acc.getAccountName().equals("Dong A"))
			{
				list_trans.clear();
				for(int j=0 ; j<Publics.list_Transaction.size() ; j++)
				{
					Transaction t = Publics.list_Transaction.get(j);
					if(t.getTransactionAccount().equals("Dong A"))
						list_trans.add(t);
				}
			}
			else if(acc.getAccountName().equals("HSBC"))
			{
				list_trans.clear();
				for(int j=0 ; j<Publics.list_Transaction.size() ; j++)
				{
					Transaction t = Publics.list_Transaction.get(j);
					if(t.getTransactionAccount().equals("HSBC")==true)
						list_trans.add(t);
				}
			}
			else
			{
				list_trans.clear();
				for(int j=0 ; j<Publics.list_Transaction.size() ; j++)
				{
					Transaction t = Publics.list_Transaction.get(j);
					if(t.getTransactionAccount().equals("ACB"))
						list_trans.add(t);
				}
			}
		}
		else
		{
			list_trans.clear();
			list_trans = Publics.list_Transaction;
		}
		List<String> dayTemp = new ArrayList<String>();
		for(int k=0 ; k<list_trans.size() ; k++)
		{
			dayTemp.add(list_trans.get(k).getTransactionDate());
		}
		daily.clear();
		daily = Publics.getUniqueDate(dayTemp);
		ExpListItems = SetStandardGroups(list_trans);
		ExpAdapter = new ExpandTransAdapter(ManageTransaction.this, ExpListItems);
	}
	
	/**process next and previous*/
	private void handleMonth()
	{
		month.clear();
		month = Publics.getUniqueMonth(daily);
		monthAdapter = new ArrayAdapter<String>(getApplicationContext()
				, android.R.layout.simple_spinner_item, month);
		spn_Month.setAdapter(monthAdapter);
		spn_Month.setSelection(indexMonth);
	}
	
	/**process listview follow month*/
	private void handleChoose(String key)
	{
		list_transMonth.clear();
		for(Transaction t : list_trans)
		{
			if(t.getTransactionDate().contains(key))
			{
				list_transMonth.add(t);
			}
		}
		List<String> dayTemp = new ArrayList<String>();
		for(int k=0 ; k<list_transMonth.size() ; k++)
		{
			dayTemp.add(list_transMonth.get(k).getTransactionDate());
		}
		daily.clear();
		daily = Publics.getUniqueDate(dayTemp);
		ExpListItems = SetStandardGroups(list_transMonth);
		ExpAdapter = new ExpandTransAdapter(ManageTransaction.this, ExpListItems);
		elv_transaction.setAdapter(ExpAdapter);
	}
}
