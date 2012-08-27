package model.transaction;

import java.util.ArrayList;

import publics.Publics;

import main.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandTransAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<TransactionGroup> groups;
	
	public ExpandTransAdapter (Context c, ArrayList<TransactionGroup> g)
	{
		this.context = c;
		this.groups = g;
	}

	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<Transaction> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}


	public int getChildrenCount(int groupPosition) {
		ArrayList<Transaction> chList = groups.get(groupPosition).getItems();

		return chList.size();
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}


	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void addItem(Transaction item, TransactionGroup group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
		int index = groups.indexOf(group);
		ArrayList<Transaction> ch = groups.get(index).getItems();
		ch.add(item);
		groups.get(index).setItems(ch);
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Transaction child = (Transaction) getChild(groupPosition, childPosition);
		View v = convertView;
		if (v == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = new View(context);
			v = infalInflater.inflate(R.layout.view_transaction_item, null);
		}
		TextView tv_cate = (TextView) v.findViewById(R.id.tv_viewItemCat);
		TextView tv_name = (TextView)v.findViewById(R.id.tv_viewItemName);
		TextView tv_unit = (TextView)v.findViewById(R.id.tv_viewItemUnit);
		TextView tv_amount = (TextView)v.findViewById(R.id.tv_viewItemAmount);
		tv_name.setText(child.getTransactionItem());
		tv_unit.setText("VND");
		tv_cate.setText(child.getTransactionCategory());
		tv_amount.setText(Publics.formatNumber(child.getTransactionAmount()));
		return v;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TransactionGroup group = (TransactionGroup) getGroup(groupPosition);
		View vi = convertView;
		if(convertView == null)
		{
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = new View(context);
			vi = inf.inflate(R.layout.group_row_transaction, null);
		}
		TextView tv = (TextView) vi.findViewById(R.id.tv_transGroupName);
		tv.setText(group.getName());
		return vi;
	}
}
