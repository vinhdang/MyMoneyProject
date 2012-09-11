package report.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.category.Category;
import model.transaction.Transaction;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class GraphicActivity extends Activity {
	
	public class PieDetailsItem {
		public String title;
		public double    Count;
		public String Label;
		public float  Percent;
		public int    Color;
	}
	
	public class ChartData{
		public String name;
		public double amount;
		public String type;
		public String accountName;
		
		public ChartData(Category c, double a, String accN)
		{
			this.name = c.categoryName;
			this.amount = a;
			this.type = c.categoryType;
			this.accountName = accN;
		}
	}
	
	public class TempTranCate{
		public String nameCate;
		public String accTran;
		public TempTranCate(String n, String a)
		{
			this.nameCate = n;
			this.accTran = a;
		}
	}
	
	List<PieDetailsItem> PieData;
	private Spinner spn_month;
	private RadioGroup rg1;
	private RadioGroup rg2;
	private List<String> month ;
	private List<Transaction> list_transMonth;
	private List<Transaction> list_trans;//de luu cac transaction cua acc
	private List<ChartData> chartData;
	private String select = null;//luu du lieu lua chon la category hay account
	private String type = "Chi tieu"; // luu du lieu la in come hay expense
	private String account = null; //luu account duoc chon
	private ViewPieChart PieChartView;
	private PieDetailsItem Item;
	private ArrayAdapter<String> adapter;
	Bitmap mBackgroundImage;
	ImageView mImageView;
	private Canvas can;
	private LinearLayout TargetPieView;
	private int HeighOfBg = 300;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_report);
        list_transMonth = new ArrayList<Transaction>();
        list_trans = new ArrayList<Transaction>();
        chartData = new ArrayList<GraphicActivity.ChartData>();
        PieChartView = new ViewPieChart( this );
        
        ////////////////////Bit map
        mBackgroundImage = Bitmap.createBitmap(230, HeighOfBg, Bitmap.Config.RGB_565); 
        
        ///////////////////////
        spn_month = (Spinner)findViewById(R.id.spn_reportSelect);
        rg1 = (RadioGroup)findViewById(R.id.radioGroup1);
        rg2 = (RadioGroup)findViewById(R.id.radioGroup2);
        TargetPieView =  (LinearLayout) findViewById(R.id.pie_container);
        can = new Canvas();
        mImageView = new ImageView(this);
        //Spinner month
        month = new ArrayList<String>();
        //getMonth();
        getAllMonth();
        adapter = new ArrayAdapter<String>(getApplicationContext(),
        		android.R.layout.simple_spinner_item, month);
        spn_month.setAdapter(adapter);
        //spn_month.setSelection(0);
        spn_month.setOnItemSelectedListener(handleSelect);
        //radio group
        rg1.setOnCheckedChangeListener(handleCheck1);
        rg2.setOnCheckedChangeListener(handleCheck2);
        select = "Category";
        
    }
	
	/**Select month from spinner */
	OnItemSelectedListener handleSelect = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			DrawPieChart();
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
	
	public void DrawPieChart()
	{
		String key = spn_month.getSelectedItem().toString();
		//Toast.makeText(getApplicationContext(), key	, Toast.LENGTH_SHORT).show();
		//
		//xu ly cac transaction da loc va add vao list_transMonth
		if(key != null)
		{
			if(select.equals("Account"))
			{
				handleChoose(key, list_trans);
				//filterCategory(list_transMonth);
				getUniqueCategory(list_transMonth);
				// loai bo cac type theo yeu cau
				List<ChartData> tmp = new ArrayList<GraphicActivity.ChartData>();
				if(chartData.size() > 0)
				{
					for(ChartData c : chartData)
					{
						if(c.type.equals(type))
							tmp.add(c);
					}
					chartData.clear();
					chartData = tmp;
					for(ChartData c: chartData)
					{
						for(Transaction t: list_transMonth)
						{
							if(c.name.equals(t.getTransactionCategory()))
							{
								c.amount = c.amount + t.getTransactionAmount();
							}
						}
					}
					if(chartData.size() > 0)
					{
						try {
							//////////////Reset heighofbg for bitmap
							HeighOfBg = setHeight(chartData);
							
							mBackgroundImage = Bitmap.createBitmap(230, HeighOfBg, Bitmap.Config.RGB_565);
							drawChart(chartData, key, type);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						if(type.equals("Thu nhap"))
							Toast.makeText(getApplicationContext(), "No Income in this month"	, Toast.LENGTH_SHORT).show();
		        		if(type.equals("Chi tieu"))
		        			Toast.makeText(getApplicationContext(), "No Expense in this month"	, Toast.LENGTH_SHORT).show();
					}
				}
			}
			else if(select.equals("Category"))
			{
				handleChoose(key, Publics.list_Transaction);
				getUniqueCategory(list_transMonth);
				// loai bo cac type theo yeu cau
				List<ChartData> tmp = new ArrayList<GraphicActivity.ChartData>();
				if(chartData.size() > 0)
				{
					for(ChartData c : chartData)
					{
						if(c.type.equals(type))
							tmp.add(c);
					}
					chartData.clear();
					chartData = tmp;
					for(ChartData c: chartData)
					{
						for(Transaction t: list_transMonth)
						{
							if(c.name.equals(t.getTransactionCategory()))
							{
								c.amount = c.amount + t.getTransactionAmount();
							}
						}
					}
					if(chartData.size() > 0)
					{
						try {
							HeighOfBg = setHeight(chartData);
							mBackgroundImage = Bitmap.createBitmap(230, HeighOfBg, Bitmap.Config.RGB_565);
							drawChart(chartData, key, type);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						if(type.equals("Thu nhap"))
							Toast.makeText(getApplicationContext(), "No Income in this month"	, Toast.LENGTH_SHORT).show();
		        		if(type.equals("Chi tieu"))
		        			Toast.makeText(getApplicationContext(), "No Expense in this month"	, Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}
	
	/**Click select radio button1*/
	OnCheckedChangeListener handleCheck1 = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio_reportCategory:
			{
				select = "Category";
				getAllMonth();
		        adapter = new ArrayAdapter<String>(getApplicationContext(),
		        		android.R.layout.simple_spinner_item, month);
		        spn_month.setAdapter(adapter);
			}break;
			case R.id.radio_reportAccount:
			{
				select = "Account";
				registerForContextMenu(group);
				group.showContextMenu();
			}break;
			default: break;
			}
		}
	};
	
	/**Click select radio button2*/
	OnCheckedChangeListener handleCheck2 = new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_reportExpense:
				{
					type = "Chi tieu";
					if(month.size() > 0)
						DrawPieChart();
				}break;
				case R.id.radio_reportIncome:
				{
					type = "Thu nhap";
					if(month.size() > 0)
						DrawPieChart();
				}break;
				default: break;
				}
			}
		};
	
	//Get unique month
	public void getMonth()
	{
		if(list_trans.size() > 0)
		{
			month.clear();
			List<String> dayTemp = new ArrayList<String>();
			for(int k=0 ; k < list_trans.size() ; k++)
			{
				dayTemp.add(list_trans.get(k).getTransactionDate());
			}
			List<String> daily = new ArrayList<String>();
			daily = Publics.getUniqueDate(dayTemp);
			month = Publics.getUniqueMonth(daily);
		}
	}
	
	//Get all month that have transaction
	public void getAllMonth()
	{
		if(Publics.list_Transaction.size() > 0)
		{
			month.clear();
			List<String> dayTemp = new ArrayList<String>();
			for(int k = 0; k < Publics.list_Transaction.size() ; k++)
			{
				dayTemp.add(Publics.list_Transaction.get(k).getTransactionDate());
			}
			List<String> daily = new ArrayList<String>();
			daily = Publics.getUniqueDate(dayTemp);
			month = Publics.getUniqueMonth(daily);	
		}
	}
	
	/**process listview follow month*/
	private void handleChoose(String key, List<Transaction> list)
	{
		if(list.size() > 0)
		{
			list_transMonth.clear();
			for(Transaction t : list)
			{
				if(t.getTransactionDate().contains(key))
				{
					list_transMonth.add(t);
				}
			}
		}
	}
	
	/**Event click on item of list view*/
	
	 @Override  
	 public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
	 {  
		 	if(Publics.list_Account.size() > 0)
		 	{
		 		menu.setHeaderTitle("Select account, please!");  
		 		super.onCreateContextMenu(menu, v, menuInfo);
		 		for(Account a : Publics.list_Account)
		 		{
		 			 menu.add(0, v.getId(), 0, a.getAccountName());
		 		}
		 	}
	 }
	 
	 @Override  
	 public boolean onContextItemSelected(MenuItem item) 
	 { 
		 	account = item.toString();
//	        loc lai list transaction theo account duoc chon
		 	if(Publics.list_Transaction.size() > 0)
		 	{
		 	filterAccount(Publics.list_Transaction);
		 	setSpinner();
		 	}
	    	return false;  
	 }
	 
	 //loc list transaction theo acc
	 public List<Transaction> filterAccount(List<Transaction> list)
	 {
		 if(list.size() > 0)
		 {
			 list_trans.clear();
			 List<Transaction> tmp = new ArrayList<Transaction>();
			 for(Transaction t : list)
			 {
				 if(t.getTransactionAccount().equals(account))
				 {
					 tmp.add(t);
				 }
			 }
			 list_trans = tmp;
			 return list_trans;
		 }
		 return null;
	 }
	 
	 public List<ChartData> getUniqueCategory(List<Transaction> list){
		 if(list.size() > 0)
		 {
			 chartData.clear();
			 List<String> listTemp = new ArrayList<String>();
			 for( Transaction t : list)
			 {	
				 String tmp = t.getTransactionCategory();
				 if(!listTemp.contains(tmp))
					 listTemp.add(tmp);
			 }
			 
			 for(String c : listTemp)
			 {
				 for(Category cate : Publics.list_Category)
				 {
					 if(c.equals(cate.categoryName))
						 chartData.add(new ChartData( cate, 0,""));
				 }
			 }
			 return chartData;
		 }
		 return null;
	 }
	 
	 //reload spinner
	 public void setSpinner()
	 {
		 getMonth();
		 adapter = new ArrayAdapter<String>(getApplicationContext(), 
	        		android.R.layout.simple_spinner_item, month);
	     spn_month.setAdapter(adapter);
	 }
	 
	 /**
	 * @throws IOException */
	 public void drawChart(List<ChartData> c, String m, String t) throws IOException
	 {
		 	if(c.size() > 0 && m != null && t != null )
		 	{
			 	//------------------------------------------------------------------------------------------
		        // Used vars declaration
		        //------------------------------------------------------------------------------------------
			 	PieData = new ArrayList<PieDetailsItem>();
		        //Random mNumGen  = new Random();
		        int MaxPieItems = c.size();
		        int MaxCount  = 0;
		        //------------------------------------------------------------------------------------------
		        // Generating data by a random loop
		        //------------------------------------------------------------------------------------------
		        for (int i = 0 ; i <MaxPieItems ; i++)
		        {
		        	Item       = new PieDetailsItem();
		        	if(i == 0)
		        	{
		        		if(t.equals("Thu nhap") && select.equals("Category"))
		        			Item.title = "The Category chart of Income in " + m;
		        		if(t.equals("Thu nhap") && select.equals("Account"))
		        			Item.title = account + " account chart of Income in " + m;
		        		if(t.equals("Chi tieu") && select.equals("Category"))
		        			Item.title = "The Category chart of Expense in " + m;
		        		if(t.equals("Chi tieu") && select.equals("Account"))
		        			Item.title = account + " account chart of Expense in " + m;
		        	}
		        	Item.Count = c.get(i).amount;
		        	Item.Label = c.get(i).name;
		        	//Item.Color = 0xff000000 + 256*256*mNumGen.nextInt(256) + 256*mNumGen.nextInt(256) + mNumGen.nextInt(256); 	
		        	Item.Color = Integer.parseInt(Publics.listColor[i]);
		        	MaxCount += c.get(i).amount;
		        	PieData.add(Item);       	
		        }
		        //------------------------------------------------------------------------------------------
		        // OverlayId  => Image to be drawn on top of pie to make it more beautiful!
		        //------------------------------------------------------------------------------------------
		        //int OverlayId = R.drawable.cam_overlay_big;
		        //------------------------------------------------------------------------------------------
		        // Size => Pie size
		        //------------------------------------------------------------------------------------------
		        int Size = 146;
		        //------------------------------------------------------------------------------------------
		        // BgColor  => The background Pie Color
		        //------------------------------------------------------------------------------------------
		        int BgColor = Color.WHITE;
		        
		        //------------------------------------------------------------------------------------------
		        // Generating Pie views
		        //------------------------------------------------------------------------------------------
		        PieChartView = new ViewPieChart( this );
		        PieChartView.setLayoutParams(new LayoutParams(Size, Size));
		        PieChartView.setGeometry(Size, Size, 1, 1, 1, 1, R.drawable.cam_overlay_big);
		        PieChartView.setSkinParams(BgColor);
		        PieChartView.setData(PieData, MaxCount);
		        PieChartView.invalidate();
		        
		        //------------------------------------------------------------------------------------------
		        // Draw Pie Vien on Bitmap canvas
		        //------------------------------------------------------------------------------------------
		      
		        //Bitmap temp = convertToMutable(mBackgroundImage);
		        can.setBitmap(mBackgroundImage);
		        PieChartView.draw(can);
		        PieChartView = null;
		        //------------------------------------------------------------------------------------------
		        // Create a new ImageView to add to main layout 
		        //------------------------------------------------------------------------------------------
		        	      
		        TargetPieView.removeView(mImageView);
		    	mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		    	mImageView.setBackgroundColor(BgColor);
		        mImageView.setImageBitmap(mBackgroundImage);
			    
			    //------------------------------------------------------------------------------------------
		        // Finaly add Image View to target view !!!
		        //------------------------------------------------------------------------------------------
			    TargetPieView.addView(mImageView);
			    TargetPieView.setBackgroundColor(BgColor);
		 	}
	 }
	 public int setHeight(List<ChartData> c)
	 {
		 int count = 350;
		 if(c.size() >= 5)
			 count += (c.size() - 5)*30;
		 else
			 count = 380;
		 return count;
	 }
}