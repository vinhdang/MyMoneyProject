package account.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.account.AccountDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateAccount extends Activity {
	private EditText edt_name;
	private Spinner spn_currency;
	private EditText edt_descript;
	private EditText edt_balance;
	private Button btnSave;
	private int pos;
	private Account acc = null;
	private AccountDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_account);
		dataSource = new AccountDataSource(this);
		dataSource.open();
		
		//create on click for 5 top button
		Publics.topFunction(this);
		btnSave = (Button)findViewById(R.id.btn_UpdateAccountSave);
		edt_name = (EditText)findViewById(R.id.edt_accName);
		spn_currency = (Spinner)findViewById(R.id.spn_accUpdateCurrency);
		edt_descript = (EditText)findViewById(R.id.edt_description);
		edt_balance = (EditText)findViewById(R.id.edt_startingBalance);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listCurrency);
		spn_currency.setAdapter(adapter);
		
		Intent i = getIntent();
		if(i != null)
		{
			pos = i.getIntExtra("update", -1);
			if(pos != -1)
			{
				acc = Publics.list_Account.get(pos);
				edt_name.setText(acc.getAccountName());
				edt_balance.setText(String.valueOf(acc.getFinalBalance()));
				edt_descript.setText(acc.getDescript());
				int spinnerPosition = adapter.getPosition(acc.getUnit());
				spn_currency.setSelection(spinnerPosition);	
			}
		}
		
		//Action when click "save" button
		btnSave.setOnClickListener(handleSave);
	}
	
	/**Save*/
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			acc = getData(acc);
			dataSource.updateAccount(acc);
			dataSource.close();
			Intent intent = new Intent(getApplicationContext(),General.class);
			intent.putExtra("tab", 1);
			startActivity(intent);
			finish();
		}
	};
	
	/**Get data*/
	public Account getData(Account a)
	{
		a.setAccountName(edt_name.getText().toString());
		a.setUnit(spn_currency.getSelectedItem().toString());
		a.setDescript(edt_descript.getText().toString());
		String tmp = edt_balance.getText().toString();
		if(Publics.isNumeric(tmp))
			a.setFinalBalance(Double.parseDouble(tmp));
		else
		{
			edt_balance.setText("");
			edt_balance.setFocusable(true);
		}
		return a;
	}
	
	 @Override
	  protected void onResume() {
		dataSource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
		dataSource.close();
	    super.onPause();
	  }
	  
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add("Exit");
			return super.onCreateOptionsMenu(menu);
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getTitle() == "Exit")//exit
			{
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			    finish();
			    System.exit(0);
			}
			return super.onOptionsItemSelected(item);
		}
}