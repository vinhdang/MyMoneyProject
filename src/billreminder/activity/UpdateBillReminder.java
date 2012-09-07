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

public class UpdateBillReminder extends Activity {
	private TextView pDisplayDate;
	private Button pPickDate;
	private int pYear;
	private int pMonth;
	private int pDay;
	static final int DATE_DIALOG_ID = 0;
	private Bill bill;
	private BillDataSource bds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.new_bill_reminder);
		        
		bds = new BillDataSource(this);
		bill = new Bill();
		
		Intent theIntent = getIntent();
		if (theIntent!=null)
			bill.setBillId(theIntent.getIntExtra("POS", 0));
		/**Process*/
		//pDisplayDate = (TextView) findViewById(R.id.tv_billAddDate);
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
        
        //07/09/2012 mvt add
        
        
        
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
        
        
        
        //function for home button
        Publics.topFunction(this);
        
        ((Button)findViewById(R.id.btn_save)).setOnClickListener(handleSave);
        
        bill = bds.getBillById(bill.getBillId());
        ((EditText)findViewById(R.id.edt_itemName)).setText(bill.getBillItem());
		((EditText)findViewById(R.id.edt_amount)).setText(String.valueOf(bill.getBillAmount()));
		((Spinner)findViewById(R.id.spn_category)).setSelection(arrAdapCate.getPosition(bill.getBillCategory()));
		((Button)findViewById(R.id.btn_billAddDate)).setText(bill.getBillDueDay());
		((EditText)findViewById(R.id.edt_note)).setText(bill.getBillNote());
		((Spinner)findViewById(R.id.spn_notification)).setSelection(adapRepeat.getPosition(bill.getBillNotification()));
		((Spinner)findViewById(R.id.spn_repeat)).setSelection(adapRepeat.getPosition(bill.getBillRepeat()));
		((Spinner)findViewById(R.id.spn_type)).setSelection((bill.getBillType()));
		
		
		bds.close();
	}
	
	/**Click button Save*/
	android.view.View.OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			try
			{
				bill.setBillItem(((EditText)findViewById(R.id.edt_itemName)).getText().toString());
				bill.setBillAmount(Double.parseDouble(((EditText)findViewById(R.id.edt_amount)).getText().toString()));
				bill.setBillCategory(((Spinner)findViewById(R.id.spn_category)).getSelectedItem().toString());
				bill.setBillDueDay(((Button)findViewById(R.id.btn_billAddDate)).getText().toString());
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
				bds.open();
				bds.updateBill(bill);
				
				Publics.list_PaidBill = bds.getAllPaidBills();
			
				Publics.list_UpcomingBill = bds.getAllUpcomingBills();
				bds.close();
				Intent i = new Intent(getApplicationContext(), General.class);
				i.putExtra("tab", 3);
				startActivity(i);
				
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
	    	                    .append(pYear).append(" "));
	    	    }
	    
	    
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
