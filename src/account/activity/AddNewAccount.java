package account.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.account.Account;
import model.account.AccountDataSource;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class AddNewAccount extends Activity {
	private EditText edt_name;
	private Spinner spn_currency;
	private EditText edt_descript;
	private EditText edt_balance;
	private Button btnSave;
	private Account acc = null;
	private AccountDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_account);
		dataSource = new AccountDataSource(this);
		dataSource.open();
		
		//Action when click "save" button
		btnSave = (Button)findViewById(R.id.btn_AddNewAccountSave);
		spn_currency = (Spinner)findViewById(R.id.spn_accNewAdd);
		edt_name = (EditText)findViewById(R.id.edt_accNewName);
		edt_descript = (EditText)findViewById(R.id.edt_accNewdescription);
		edt_balance = (EditText)findViewById(R.id.edt_accNewstartingBalance);
		ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listCurrency);
		
		spn_currency.setAdapter(adapterCurrency);
		btnSave.setOnClickListener(handleSave);
	}
	
	OnClickListener handleSave = new OnClickListener() {
		
		public void onClick(View v) {
			getData();
			dataSource.insertAccount(acc);
			dataSource.close();
			Intent intent = new Intent(getApplicationContext(),General.class);
			intent.putExtra("tab", 1);
			startActivity(intent);
			finish();
		}
	};
	
	/**Get data*/
	public void getData()
	{
		acc = new Account();
		acc.setAccountName(edt_name.getText().toString());
		acc.setUnit(spn_currency.getSelectedItem().toString());
		acc.setDescript(edt_descript.getText().toString());
		String tmp = edt_balance.getText().toString();
		if(Publics.isNumeric(tmp))
			acc.setFinalBalance(Double.parseDouble(tmp));
		else
		{
			edt_balance.setText("");
			edt_balance.setFocusable(true);
		}
	}
}
