package model.bill;

import java.util.LinkedList;

import publics.Publics;

import main.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BillReminderAdapter extends BaseAdapter
{
	private LinkedList<BillReminderData> data = new LinkedList<BillReminderData>();
	private Context mContext;
	public BillReminderAdapter(Context context)
	{
		mContext = context;
		data.add(new BillReminderData("HD Dien","14/01/1991",5));
		data.add(new BillReminderData("HD Nuoc","14/02/1991",55));
		data.add(new BillReminderData("HD Net","19/02/1991",180));
		data.add(new BillReminderData("HD Bao hiem","14/02/1991",999));
		data.add(new BillReminderData("HD Dien Thoai","18/07/1991",25000));
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
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
			v = li.inflate(R.layout.billreminder_inflate, null);
			
			TextView tv = (TextView)v.findViewById(R.id.tv_trans_name);
			tv.setText(data.get(pos).name);
			
			tv = (TextView)v.findViewById(R.id.tv_trans_date);
			tv.setText(data.get(pos).date);
			
			tv = (TextView)v.findViewById(R.id.tv_trans_balance);
			tv.setText(Publics.formatNumber((data.get(pos).balance)));

		}
		else
		{
			v = convertView;
		}
		return v;
	}
	
}
class BillReminderData
{
	public String name;
	public String date;
	public int balance;
	public BillReminderData(String name, String date, int balance)
	{
		this.name = name;
		this.date = date;
		this.balance = balance;
	}
}