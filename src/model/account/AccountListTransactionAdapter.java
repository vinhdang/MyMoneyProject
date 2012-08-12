package model.account;

import java.util.List;
import main.activity.R;
import model.transaction.Transaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountListTransactionAdapter extends BaseAdapter
{
	private Context mContext;
	private List<Transaction> list_trans;
	
	public AccountListTransactionAdapter(Context context, List<Transaction> list)
	{
		mContext = context;
		this.list_trans = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list_trans.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		View v;
		if (convertView==null)
		{
			LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
			v = li.inflate(R.layout.account_transaction_list_inflate, null);
			
			TextView tv = (TextView)v.findViewById(R.id.tv_trans_name);
			tv.setText(list_trans.get(pos).getTransactionName());
			
			tv = (TextView)v.findViewById(R.id.tv_trans_date);
			tv.setText(list_trans.get(pos).getTransactionDate());
			
			tv = (TextView)v.findViewById(R.id.tv_trans_balance);
			tv.setText(String.valueOf(list_trans.get(pos).getTransactionAmount()));

		}
		else
		{
			v = convertView;
		}
		return v;
	}
}