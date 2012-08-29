package model.bill;

import java.util.List;
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
	private List<Bill> data;
	private Context mContext;
	public BillReminderAdapter(Context context, List<Bill> list)
	{
		mContext = context;
		data = list;
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
			tv.setText(data.get(pos).getBillItem());
			
			tv = (TextView)v.findViewById(R.id.tv_trans_date);
			tv.setText(data.get(pos).getBillDueDay());
			
			tv = (TextView)v.findViewById(R.id.tv_trans_balance);
			tv.setText(Publics.formatNumber((data.get(pos).getBillAmount())));

		}
		else
		{
			v = convertView;
		}
		return v;
	}
	
}

