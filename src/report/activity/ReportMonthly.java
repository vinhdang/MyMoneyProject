package report.activity;

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

public class ReportMonthly extends Activity {
	private Spinner spn_reportAccount;
	private Spinner spn_reportMonth;
	private TextView tv_reportExpense;
	private TextView tv_reportIncome;
	private ListView lv_report;
	private List<String> list_acc;
	private List<Transaction> list_show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_monthly);
		list_acc = new ArrayList<String>();
		list_show = new ArrayList<Transaction>();
		
		/***/
		spn_reportAccount = (Spinner)findViewById(R.id.spn_reportMonthlyAccount);
		spn_reportMonth = (Spinner)findViewById(R.id.spn_reportMonth);
		tv_reportExpense = (TextView)findViewById(R.id.tv_reportMonthlyExpense);
		tv_reportIncome = (TextView)findViewById(R.id.tv_reportMonthlyIncome);
		lv_report = (ListView)findViewById(R.id.lv_reportIncomeListTransaction);
		
		/***/
		this.setSpinnerAccount();
		this.setSpinnerMonth();		
		tv_reportIncome.setText("0");
		tv_reportExpense.setText("0");
	}
	
	/***/
	OnItemSelectedListener handleSelectMonth = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String acc = spn_reportAccount.getSelectedItem().toString();
			String m = Publics.listMonth[arg2];
			int k = Integer.parseInt(m);
			switch (k)
			{
				case 7: // all transaction july and account HSBC 
				{
					list_show.clear();
					if((acc.equals("HSBC")== true))
					{
						list_show.add(new Transaction("Xem phim","14/06/2012",(double) 55,"Giai tri", "HSBC"));
						list_show.add(new Transaction("Mua sam","19/06/2012",(double) 180,"Mua sam", "HSBC"));
						list_show.add(new Transaction("Xem phim", "24/6/2012", (double) 150, "Giai tri", "HSBC"));
						list_show.add(new Transaction("Tien nuoc", "24/6/2012", (double) 200, "Phi", "HSBC"));
						list_show.add(new Transaction("Luong Lotteria", "29/6/2012", (double) 1500, "Luong", "HSBC"));
						tv_reportIncome.setText("1,500");
						tv_reportExpense.setText("585");
					}
					else if(acc.equals("Dong A")== true)
					{
						list_show.add(new Transaction("Cafe", "9/7/2012", (double) 10, "An uong", "Dong A"));
						list_show.add(new Transaction("Com trua", "9/7/2012", (double) 30, "An uong", "Dong A"));
						list_show.add(new Transaction("Do xang", "9/7/2012", (double) 50, "Nhien lieu", "Dong A"));
						list_show.add(new Transaction("An sang", "9/7/2012", (double) 15, "An uong", "Dong A"));
						tv_reportIncome.setText("0,0");
						tv_reportExpense.setText("105");
					}
					else
					{
						tv_reportIncome.setText("0,0");
						tv_reportExpense.setText("0,0");
					}
					setListTransaction(list_show);
				}break;
				case 6: // all transaction june
				{
					list_show.clear();
					if((acc.equals("Dong A")== true))
					{
						
					}
					setListTransaction(list_show);
					tv_reportIncome.setText("2,000");
					tv_reportExpense.setText("3,750");
				}break;
				default:
				{
					tv_reportIncome.setText("0");
					tv_reportExpense.setText("0");
				}break;
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/***/
	public void setListTransaction(List<Transaction> list)
	{
		TransactionAdapter adapter = new TransactionAdapter(getApplicationContext(), list);
		this.lv_report.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	/***/
	public void setSpinnerAccount()
	{
		for(int i=0 ; i < Publics.list_Account.size() ; i++ )
		{
			list_acc.add(Publics.list_Account.get(i).getAccountName());
		}
		ArrayAdapter< String> adapterAcc = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, list_acc);
		this.spn_reportAccount.setAdapter(adapterAcc);
	}
	
	/***/
	public void setSpinnerMonth()
	{
		ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, Publics.listMonth);
		this.spn_reportMonth.setAdapter(adapterMonth);
	}
}
