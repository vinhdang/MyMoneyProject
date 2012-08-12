package model.account;

import java.util.List;

import publics.Publics;

import main.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountAdapter extends BaseAdapter {
	private Context mContext;
	private List<Account> listAccount;
	
	public AccountAdapter(Context context, List<Account> list)
	{
		this.listAccount = list;
		this.mContext = context;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listAccount.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View convertView, ViewGroup arg2) {
		View v = convertView;
		Account acc = listAccount.get(pos);
		if(v == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = new View(mContext);
			v = inflater.inflate(R.layout.list_account_general, null);
			TextView tv_accName = (TextView)v.findViewById(R.id.tv_generalAccName);
			TextView tv_accBalance = (TextView)v.findViewById(R.id.tv_generalAccBalance);
			TextView tv_accunit = (TextView)v.findViewById(R.id.tv_generalAccUnit);
			tv_accName.setText(acc.accountName.toString());
			tv_accBalance.setText(Publics.formatNumber(acc.finalBalance));
			tv_accunit.setText(acc.getUnit());
		}
		return v;
	}
	

}
