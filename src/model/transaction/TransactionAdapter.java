package model.transaction;

import java.util.List;

import publics.Publics;

import main.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionAdapter extends BaseAdapter {
	private Context mContext;
	private List<Transaction> listTransaction;
	
	public TransactionAdapter(Context context, List<Transaction> list)
	{
		this.mContext = context;
		this.listTransaction = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listTransaction.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View v = arg1;
		if( v == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = new View(mContext);
			v = inflater.inflate(R.layout.view_transaction_item, null);
			TextView tv_name = (TextView)v.findViewById(R.id.tv_viewItemName);
			TextView tv_unit = (TextView)v.findViewById(R.id.tv_viewItemUnit);
			TextView tv_amount = (TextView)v.findViewById(R.id.tv_viewItemAmount);
			TextView tv_cate = (TextView)v.findViewById(R.id.tv_viewItemCat);
			
			tv_name.setText(listTransaction.get(arg0).getTransactionItem());
			tv_unit.setText("VND");
			tv_amount.setText(Publics.formatNumber(listTransaction.get(arg0).getTransactionAmount()));
			tv_cate.setText(listTransaction.get(arg0).getTransactionCategory());
		}
		return v;
	}

}
