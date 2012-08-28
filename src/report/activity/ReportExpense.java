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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ReportExpense extends Activity {
	private Spinner spn_reportAccount;
	private Spinner spn_reportCategory;
	private Spinner spn_reportMonth;
	private TextView tv_reportTotal;
	private ListView lv_report;
	private List<String> list_acc;
	private List<String> list_cate;
	private List<Transaction> list_show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_expense);
		list_acc = new ArrayList<String>();
		list_cate = new ArrayList<String>();
		list_show = new ArrayList<Transaction>();
		
		/***/
		spn_reportAccount = (Spinner)findViewById(R.id.spn_reportExpenseListAccount);
		spn_reportCategory = (Spinner)findViewById(R.id.spn_reportExpenseListCategory);
		spn_reportMonth = (Spinner)findViewById(R.id.spn_reportExpenseMonth);
		tv_reportTotal = (TextView)findViewById(R.id.tv_reportExpenseTotal);
		lv_report = (ListView)findViewById(R.id.lv_reportExpenseListTransaction);
		
		
		/***/
		setSpinnerAccount();
		setSpinnerCategory();
		setSpinnerMonth();
		spn_reportMonth.setOnItemSelectedListener(handleSelectMonth);
		tv_reportTotal.setText("0");
	}
	
	/***/
	OnItemSelectedListener handleSelectMonth = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String acc = spn_reportAccount.getSelectedItem().toString();
//			String cate = spn_reportCategory.getSelectedItem().toString();
			String m = Publics.listMonth[arg2];
			int k = Integer.parseInt(m);
			switch (k)
			{
				case 7: // all transaction july
				{
					list_show.clear();
					if((acc.equals("HSBC")== true))
					{
						
						list_show.add(new Transaction("Mua xe","18/07/1991",(double) 25000, "Mua sam", "HSBC"));
						list_show.add(new Transaction("Mua sam","19/06/2012",(double) 180,"Mua sam", "HSBC"));
						list_show.add(new Transaction("Tien nuoc", "24/6/2012", (double) 200, "Phi", "HSBC"));
						list_show.add(new Transaction("Xem phim","14/06/2012",(double) 55,"Giai tri", "HSBC"));
						list_show.add(new Transaction("Xem phim", "24/6/2012", (double) 150, "Giai tri", "HSBC"));
						tv_reportTotal.setText("55");
					}
					setListTransaction(list_show);
				}break;
				case 6: // all transaction june
				{
					list_show.clear();
					if((acc.equals("Dong A")== true))
					{
						list_show.add(new Transaction("An sang", "9/7/2012", (double) 15, "An uong", "Dong A"));
						list_show.add(new Transaction("Do xang", "9/7/2012", (double) 50, "Nhien lieu", "Dong A"));
						list_show.add(new Transaction("Com trua", "9/7/2012", (double) 30, "An uong", "Dong A"));
						list_show.add(new Transaction("Cafe", "9/7/2012", (double) 10, "An uong", "Dong A"));
						list_show.add(new Transaction("An Sang","14/06/2012",(double) 5,"An uong", "Dong A"));
						tv_reportTotal.setText("3,000");
					}
					setListTransaction(list_show);
				}break;
				default:
				{
					tv_reportTotal.setText("0");
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
//		for(int i=0 ; i < Publics.list_Account.size() ; i++ )
//		{
//			list_acc.add(Publics.list_Account.get(i).getAccountName());
//		}
		list_acc.add("HSBC");
		list_acc.add("Dong A");
		list_acc.add("ABC");
		ArrayAdapter< String> adapterAcc = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, list_acc);
		this.spn_reportAccount.setAdapter(adapterAcc);
	}
	
	public void setSpinnerCategory()
	{
		for(int i=0 ; i < Publics.list_Category.size() ; i++ )
		{
			list_cate.add(Publics.list_Category.get(i).getCategoryName());
		}
		ArrayAdapter< String> adapterCate = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, list_cate);
		this.spn_reportCategory.setAdapter(adapterCate);
	}
	
	public void setSpinnerMonth()
	{
		ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, Publics.listMonth);
		this.spn_reportMonth.setAdapter(adapterMonth);
	}
}
