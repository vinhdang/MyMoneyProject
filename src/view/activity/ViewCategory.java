package view.activity;

import java.util.ArrayList;
import java.util.List;

import publics.Publics;

import main.activity.R;
import model.transaction.Transaction;
import model.transaction.TransactionAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewCategory extends Activity {
	private Spinner spn_viewList;
	private TextView tv_viewTotal;
	private ListView lv_viewTransaction;
	private TransactionAdapter transAdapter;
	private List<Transaction> listShow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_category);
		listShow = new ArrayList<Transaction>();
		
		/***/
		spn_viewList = (Spinner)findViewById(R.id.spn_viewCategoryType);
		tv_viewTotal = (TextView)findViewById(R.id.tv_viewCategoryTotal);
		lv_viewTransaction = (ListView)findViewById(R.id.lv_viewCategoryTransaction);
		String[] category = {"An uong", "Nhien lieu", "Giai tri","Sua chua", "Dam tiec", "Phi"};
		ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
		
		/***/
		spn_viewList.setAdapter(adapterCategory);
		spn_viewList.setOnItemSelectedListener(handleItem);
		
        //function for home button
        Publics.bottomFunction(this);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/***/
	OnItemSelectedListener handleItem = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			switch(arg2)
			{
				case 0: //Handle event
				{
					listShow.clear();
					for(int j=0 ; j < Publics.list_Transaction.size() ; j++)
					{
						Transaction tmp = Publics.list_Transaction.get(j);
						if(tmp.getTransactionCategory().equals("An uong"))
						{
							listShow.add(tmp);
						}
					}
					Show(listShow);
					tv_viewTotal.setText("55");
				}break;
				case 1:
				{
					listShow.clear();
					for(int j=0 ; j < Publics.list_Transaction.size() ; j++)
					{
						Transaction tmp = Publics.list_Transaction.get(j);
						if(tmp.getTransactionCategory().equals("Nhien lieu"))
						{
							listShow.add(tmp);
						}
					}
					Show(listShow);
					tv_viewTotal.setText("50");
				}break;
				case 2:
				{
					listShow.clear();
					for(int j=0 ; j < Publics.list_Transaction.size() ; j++)
					{
						Transaction tmp = Publics.list_Transaction.get(j);
						if(tmp.getTransactionCategory().equals("Giai tri"))
						{
							listShow.add(tmp);
						}
					}
					Show(listShow);
					tv_viewTotal.setText("150");
				}break;
				case 3:
				{
					listShow.clear();
					for(int j=0 ; j < Publics.list_Transaction.size() ; j++)
					{
						Transaction tmp = Publics.list_Transaction.get(j);
						if(tmp.getTransactionCategory().equals("Sua chua"))
						{
							listShow.add(tmp);
						}
					}
					Show(listShow);
					tv_viewTotal.setText("100");
				}break;
				case 4:
				{
					listShow.clear();
					for(int j=0 ; j < Publics.list_Transaction.size() ; j++)
					{
						Transaction tmp = Publics.list_Transaction.get(j);
						if(tmp.getTransactionCategory().equals("Dam tiec"))
						{
							listShow.add(tmp);
						}
					}
					Show(listShow);
					tv_viewTotal.setText("500");
				}break;
				case 5:
				{
					listShow.clear();
					for(int j=0 ; j < Publics.list_Transaction.size() ; j++)
					{
						Transaction tmp = Publics.list_Transaction.get(j);
						if(tmp.getTransactionCategory().equals("Phi"))
						{
							listShow.add(tmp);
						}
					}
					Show(listShow);
					tv_viewTotal.setText("3,000");
				}break;
				default : break;
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/***/
	public void Show(List<Transaction> list)
	{
		transAdapter = new TransactionAdapter(getApplicationContext(), list);
		lv_viewTransaction.setAdapter(transAdapter);
		transAdapter.notifyDataSetChanged();
	}	
}
