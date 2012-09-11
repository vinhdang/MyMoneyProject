package billreminder.activity;

import general.activity.General;

import java.util.Calendar;

import publics.Publics;

import main.activity.R;
import model.bill.Bill;
import model.bill.BillDataSource;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewBillReminder extends Activity {
	private TextView pDisplayDate;
	private Button pPickDate;
	private int pYear;
	private int pMonth;
	private int pDay;
	static final int DATE_DIALOG_ID = 0;
	private Bill bill;
	private BillDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.new_bill_reminder);
		dataSource = new BillDataSource(this);
		bill = new Bill();
		
		/**Process*/
//		pDisplayDate = (TextView) findViewById(R.id.tv_billAddDate);
	    pPickDate = (Button) findViewById(R.id.btn_billAddDate);
	    /** Get the current date */
	    final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
        
        /**Set function*/
		pPickDate.setOnClickListener(handlePickDate);
		/** Display the current date in the TextView */
        updateDisplay();        
        
        //function for home button
        Publics.topFunction(this);
        
        
        //07/09/2012 mvt add
        
        BillDataSource bds = new BillDataSource(this);
        
        bds.open();
        
        Spinner spnCate = (Spinner)findViewById(R.id.spn_category);
        
        ArrayAdapter<String> arrAdapCate = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, bds.getCategoryList());
        spnCate.setAdapter(arrAdapCate);
        
        
        //repeat
        Spinner spnRepeat = (Spinner)findViewById(R.id.spn_repeat);
        ArrayAdapter<String> adapRepeat = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, Publics.listRepeat);
        spnRepeat.setAdapter(adapRepeat);
        
        
        //notifi
        Spinner spnNotifi = (Spinner)findViewById(R.id.spn_notification);
        ArrayAdapter<String> adapRNotifi= new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, Publics.listNotification);
        spnNotifi.setAdapter(adapRNotifi);
        
        //type
        Spinner spnType = (Spinner)findViewById(R.id.spn_type);
        ArrayAdapter<String> adapType= new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, Publics.listBillType);
        spnType.setAdapter(adapType);
        
        bds.close();
        
        ((Button)findViewById(R.id.btn_save)).setOnClickListener(handleSave);
	}
	
	/**Click button Save*/
	android.view.View.OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			try
			{
				bill.setBillItem(((EditText)findViewById(R.id.edt_itemName)).getText().toString());
				bill.setBillAmount(Double.parseDouble(((EditText)findViewById(R.id.edt_amount)).getText().toString()));
				bill.setBillCategory(((Spinner)findViewById(R.id.spn_category)).getSelectedItem().toString());
				String date = Publics.formatDate(Publics.FormatDate, ((Button)findViewById(R.id.btn_billAddDate)).getText().toString());
				bill.setBillDueDay(date);
				bill.setBillNote(((EditText)findViewById(R.id.edt_note)).getText().toString());
				bill.setBillNotification(((Spinner)findViewById(R.id.spn_notification)).getSelectedItem().toString());
				bill.setBillRepeat(((Spinner)findViewById(R.id.spn_repeat)).getSelectedItem().toString());
				if (((Spinner)findViewById(R.id.spn_type)).getSelectedItem().toString().equals(Publics.listBillType[0]))
					bill.setBillType(0);
				else
					bill.setBillType(1);
			}
			catch (Exception e)
			{
				
			}
			try{
				dataSource.open();
				dataSource.insertBill(bill);
				dataSource.close();
				if (bill.getBillType()==0)
					Publics.list_PaidBill.add(bill);
				else
					Publics.list_UpcomingBill.add(bill);
				Intent intent = new Intent(getApplicationContext(),General.class);
				intent.putExtra("tab", 3);
				startActivity(intent);
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
	};
	
	/**Click pickDate*/
	OnClickListener handlePickDate = new OnClickListener() {
		
		public void onClick(View v) {
			 showDialog(DATE_DIALOG_ID);
		}
	};
	
    /** Callback received when the user "picks" a date in the dialog */
	  private DatePickerDialog.OnDateSetListener pDateSetListener =
	            new DatePickerDialog.OnDateSetListener() {
	 
	                public void onDateSet(DatePicker view, int year, 
	                                      int monthOfYear, int dayOfMonth) {
	                    pYear = year;
	                    pMonth = monthOfYear;
	                    pDay = dayOfMonth;
	                    updateDisplay();
	                }
	            };
	            
	            /** Updates the date in the TextView */
	    	    private void updateDisplay() {
	    	        pPickDate.setText(
	    	            new StringBuilder()
	    	                    // Month is 0 based so add 1    
	    	                    .append(pDay).append("/")
	    	                    .append(pMonth + 1).append("/")
	    	                    .append(pYear));
	    	    }
	    
	    /** Displays a notification when the date is updated */
	    
	    /** Create a new dialog for date picker */
	    @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case DATE_DIALOG_ID:
	            return new DatePickerDialog(this, 
	                        pDateSetListener,
	                        pYear, pMonth, pDay);
	        }
	        return null;
	    }
	
}
