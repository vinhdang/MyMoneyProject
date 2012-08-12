package transaction.activity;

import java.util.Calendar;

import publics.Publics;
import main.activity.R;
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

public class TransactionNew extends Activity {
	private Button btn_transactionSave;
	private Button btn_transactionCancel;
	private TextView pDisplayDate;
	private Button pPickDate;
	private int pYear;
	private int pMonth;
	private int pDay;
	static final int DATE_DIALOG_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_add);
		
		//create function for 4 image button on bottom
		Publics.bottomFunction(this); 
		
		//create on click for 5 top button
		Publics.topFunction(this);
		
		/**Process*/
		btn_transactionCancel = (Button)findViewById(R.id.btn_transactionAddCancel);
		btn_transactionSave = (Button)findViewById(R.id.btn_transactionAddOK);
		pDisplayDate = (TextView) findViewById(R.id.tv_transAddDate);
	    pPickDate = (Button) findViewById(R.id.btn_transAddDate);
	    /** Get the current date */
	    final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
		
		/**Set function*/
		btn_transactionCancel.setOnClickListener(handleCancel);
		btn_transactionSave.setOnClickListener(handleSave);
		pPickDate.setOnClickListener(handlePickDate);
		/** Display the current date in the TextView */
        updateDisplay();
	}
	
	/**Click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i= new Intent(getApplicationContext(), ManageTransaction.class);
			Toast.makeText(getApplicationContext(), "Save New Transaction.....", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK, i);
			finish();
		}
	};
	
	/**Click Cancel*/
	OnClickListener handleCancel = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i= new Intent(getApplicationContext(), ManageTransaction.class);
			Toast.makeText(getApplicationContext(), "Canceled.....", Toast.LENGTH_SHORT).show();
			startActivity(i);			
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
	    	        pDisplayDate.setText(
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