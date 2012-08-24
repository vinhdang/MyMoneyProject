package model.plan;

import java.util.List;
import main.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlanAdapter extends BaseAdapter {
	private Context context;
	private List<Plan> list_plan;
	
	public PlanAdapter(Context c, List<Plan> list)
	{
		this.context = c;
		this.list_plan = list;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		View v = convertView;
		Plan plan = list_plan.get(position);
		if(v == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = new View(context);
			v = inflater.inflate(R.layout.plan_item, null);
			TextView tv_Name = (TextView)v.findViewById(R.id.tv_planItemName);
			TextView tv_Amount = (TextView)v.findViewById(R.id.tv_planItemAmount);
			tv_Name.setText(plan.getName().toString());
			tv_Amount.setText(String.valueOf(plan.getAmount()));
		}
		return null;
	}

}
