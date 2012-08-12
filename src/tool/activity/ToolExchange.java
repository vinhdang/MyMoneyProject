package tool.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ToolExchange extends Activity {
	private Spinner spn_exchangeCurrency;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_exchange);
		
		/***/
		spn_exchangeCurrency = (Spinner)findViewById(R.id.spn_toolExchangeCurrency);
		ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Publics.listCurrency);
		
		/***/
		spn_exchangeCurrency.setAdapter(adapterCurrency);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
}
