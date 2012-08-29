package billreminder.activity;

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
import android.widget.Button;
import android.widget.DatePicker;
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
	private BillDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.update_bill_reminder);
		        
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
	}
	
	/**Click button Save*/
	android.view.View.OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			if(bill !=null )
			{
				try{
					dataSource.open();
					dataSource.insertBill(bill);
					dataSource.close();
					Publics.list_Bill.add(bill);
					Intent i = new Intent(getApplicationContext(), ManageBillReminder.class);
					startActivity(i);
					finish();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
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
	                    displayToast();
	                }
	            };
	            
	            /** Updates the date in the TextView */
	    	    private void updateDisplay() {
	    	        pPickDate.setText(
	    	            new StringBuilder()
	    	                    // Month is 0 based so add 1
	    	                    .append(pMonth + 1).append("/")
	    	                    .append(pDay).append("/")
	    	                    .append(pYear).append(" "));
	    	    }
	    
	    /** Displays a notification when the date is updated */
	    private void displayToast() {
	        Toast.makeText(this, new StringBuilder().append("Date choosen is ").append(pDisplayDate.getText()),  Toast.LENGTH_SHORT).show();
	             
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
