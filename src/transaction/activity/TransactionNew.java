package transaction.activity;

import general.activity.General;

import java.util.Calendar;
import publics.Publics;
import main.activity.R;
import model.repeat.RepeatDataSource;
import model.repeat.Repeat;
import model.transaction.Transaction;
import model.transaction.TransactionDataSource;
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

public class TransactionNew extends Activity {
	private Button btn_transactionSave;
	private TextView pDisplayDate;
	private Button pPickDate;
	private int pYear;
	private int pMonth;
	private int pDay;
	static final int DATE_DIALOG_ID = 0;
	private boolean update;
	private int id;
	private Transaction data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_add);
		data = new Transaction();
		//create on click for 5 top button
		Publics.topFunction(this);
		
		//Check the update activity
		
		try{
		update = getIntent().getExtras().getBoolean("update");
		}
		catch (Exception ex)
		{
			update = false;
		}
		if (update)
			Toast.makeText(this, "Update action", Toast.LENGTH_SHORT).show();
		
		
		/**Process*/
		btn_transactionSave = (Button)findViewById(R.id.btn_transactionAddOK);
		pDisplayDate = (TextView) findViewById(R.id.tv_transAddDate);
	    pPickDate = (Button) findViewById(R.id.btn_transAddDate);
	    /** Get the current date */
	    final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
		
		/**Set function*/
		btn_transactionSave.setOnClickListener(handleSave);
		pPickDate.setOnClickListener(handlePickDate);
		/** Display the current date in the TextView */
        updateDisplay();
        
        //Fill data for component
        Spinner spnAcc = (Spinner)findViewById(R.id.spn_transactionAddAccount);
        TransactionDataSource tds = new TransactionDataSource(this);
		try{
	        tds.open();			
	        ArrayAdapter<String> arrAdapAcc = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_spinner_item, tds.getAccountList());
	        spnAcc.setAdapter(arrAdapAcc);
	        
	        Spinner spnCate = (Spinner)findViewById(R.id.spn_transactionAddCategory);
	        
	        ArrayAdapter<String> arrAdapCate = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_spinner_item, tds.getCategoryList());
	        spnCate.setAdapter(arrAdapCate);
	       
	        Spinner spnPay = (Spinner)findViewById(R.id.spn_transactionAddPayMode);
	        ArrayAdapter<String> adapPay = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_spinner_item, Publics.listPayMode);
	        spnPay.setAdapter(adapPay);
	        
	        Spinner spnRepeat = (Spinner)findViewById(R.id.spn_transactionAddRepeat);
	        ArrayAdapter<String> adapRepeat = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_spinner_item, Publics.listRepeat);
	        spnRepeat.setAdapter(adapRepeat);
	        
	       if (update)
	       {
	    	   id = getIntent().getExtras().getInt("POS");   	   
	    	   data = tds.getTransactionById(id);
	    	   
	    	   ((EditText)findViewById(R.id.edt_transactionAddNote)).setText(data.getTransactionNote());
	    	   ((EditText)findViewById(R.id.edt_transactionItemName)).setText(data.getTransactionItem());
	    	   ((TextView)findViewById(R.id.tv_transAddDate)).setText(data.getTransactionDate());
	    	   ((EditText)findViewById(R.id.edt_transactionAddAmount)).setText(String.valueOf(data.getTransactionAmount()));
	    	   ((Spinner)findViewById(R.id.spn_transactionAddAccount)).setSelection(arrAdapAcc.getPosition(data.getTransactionItem()));
	    	   ((Spinner)findViewById(R.id.spn_transactionAddRepeat)).setSelection(adapRepeat.getPosition(data.getTransactionRepeat()));
	    	   ((Spinner)findViewById(R.id.spn_transactionAddPayMode)).setSelection(adapPay.getPosition(data.getTransactionPaymode()));
	    	   ((Spinner)findViewById(R.id.spn_transactionAddCategory)).setSelection(arrAdapCate.getPosition(data.getTransactionCategory()));
	       }
	       
	       tds.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			tds.close();
		}
	}
	
	/**Click Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			
			Transaction trans = new Transaction();
			trans.setTransactionAccount(((Spinner)findViewById(R.id.spn_transactionAddAccount)).getSelectedItem().toString());
			trans.setTransactionAmount(Double.parseDouble(((EditText)findViewById(R.id.edt_transactionAddAmount)).getText().toString()));
			trans.setTransactionCategory(((Spinner)findViewById(R.id.spn_transactionAddCategory)).getSelectedItem().toString());
			String date = Publics.formatDate(Publics.FormatDate, ((TextView)findViewById(R.id.tv_transAddDate)).getText().toString());
			trans.setTransactionDate(date);
			trans.setTransactionItem(((EditText)findViewById(R.id.edt_transactionItemName)).getText().toString());
			trans.setTransactionPaymode(((Spinner)findViewById(R.id.spn_transactionAddPayMode)).getSelectedItem().toString());
			trans.setTransactionRepeat(((Spinner)findViewById(R.id.spn_transactionAddRepeat)).getSelectedItem().toString());
			trans.setTransactionNote(((EditText)findViewById(R.id.edt_transactionAddNote)).getText().toString());
			
			TransactionDataSource tds = new TransactionDataSource(TransactionNew.this);
			RepeatDataSource rds = new RepeatDataSource(TransactionNew.this);
			Repeat r = new Repeat();
			try{
				tds.open();
				rds.open();
				if (update)
				{
					r.setSetupDate(trans.getTransactionDate());
					trans.setTransactionId(id);
					tds.updateTransaction(trans);
					r.setItem_id(id);
				}
				else
				{
					r.setSetupDate(trans.getTransactionDate());
					Transaction tmp = tds.createTransaction(trans);
					r.setItem_id(tmp.getTransactionId());
				}
				if(trans.getTransactionRepeat().equals("None") == false)
					rds.insertRepeat(r);
				tds.close();
				rds.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
				tds.close();
				rds.close();
			}
			Intent intent = new Intent(getApplicationContext(),General.class);
			intent.putExtra("tab", 2);
			startActivity(intent);
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
	    	    	StringBuilder tmp = new StringBuilder()
                    .append(pDay).append("/")
                    .append(pMonth + 1).append("/")
                    .append(pYear).append(" ");
	    	    	pDisplayDate.setText(Publics.formatDate(Publics.FormatDate, tmp.toString()));
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
	    
	    /**Save info repeat to database*/
}
