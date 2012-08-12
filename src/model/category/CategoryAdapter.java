package model.category;

import java.util.List;

import main.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private Context mContext;
	private List<Category> listCategory;
	
	public CategoryAdapter(Context c, List<Category> list)
	{
		this.listCategory = list;
		this.mContext = c;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listCategory.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		View v = convertView;
		Category c = listCategory.get(position);
		if(v == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = new View(mContext);
			v = inflater.inflate(R.layout.list_category_item, null);
			TextView tv_cateName = (TextView)v.findViewById(R.id.tv_categoryListItemName);
			TextView tv_cateType = (TextView)v.findViewById(R.id.tv_categoryListItemType);
			tv_cateName.setText(c.categoryName.toString());
			tv_cateType.setText(c.categoryType.toString());
		}
		return v;
	}
}
