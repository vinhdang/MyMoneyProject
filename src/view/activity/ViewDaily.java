package view.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import publics.Publics;
import main.activity.R;
import model.transaction.Transaction;
import model.transaction.TransactionAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDaily extends Activity {
	private TextView tv_viewTotal;
	private ListView lv_viewTransaction;
	private TransactionAdapter transAdapter;
	private List<Transaction> listShow;
	private TextView pDisplayDate;
	private Button pPickDate;
	private int pYear;
	private int pMonth;
	private int pDay;
	private String Date;
	static final int DATE_DIALOG_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_daily);
		listShow = new ArrayList<Transaction>();
		
		/**Process*/
		tv_viewTotal = (TextView)findViewById(R.id.tv_viewDailyTotal);
		lv_viewTransaction = (ListView)findViewById(R.id.lv_viewDailyTransaction);
		pDisplayDate = (TextView) findViewById(R.id.tv_viewDailyDate);
	    pPickDate = (Button) findViewById(R.id.btn_viewDailyDate);
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
        Publics.bottomFunction(this);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/***/
	public void Show(List<Transaction> list)
	{
		transAdapter = new TransactionAdapter(getApplicationContext(), list);
		lv_viewTransaction.setAdapter(transAdapter);
		transAdapter.notifyDataSetChanged();
	}
	
	/**Click pickDate*/
	OnClickListener handlePickDate = new OnClickListener() {
		
		public void onClick(View v) {
			 showDialog(DATE_DIALOG_ID);
			 //Process Date
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
	    	                    .append(pDay).append("/")
	    	                    .append(pMonth + 1).append("/")
	    	                    .append(pYear));
	    	    	Date = pDisplayDate.getText().toString();
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
