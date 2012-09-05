package tool.activity;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import publics.Publics;
import main.activity.R;
import model.setting.SettingDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ToolExchange extends Activity {
	private Spinner spn_exchangeCurrency;
	private Button btn_refresh;
	private Button btn_ex;
	private TextView tv_src;
	private TextView tv_date;
	private TextView tv_sell;
	private TextView tv_buy;
	private EditText edt_amount;
	private EditText edt_result;
	private String RateBuy = "";
	private String RateSell = "";
	private String rate = "rate";
	private String UpdateDate = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_exchange);
		
		/***/
		spn_exchangeCurrency = (Spinner)findViewById(R.id.spn_toolExchangeCurrency);
		btn_ex = (Button)findViewById(R.id.btn_toolExchange);
		btn_refresh = (Button)findViewById(R.id.btn_toolExchangeRefresh);
		tv_buy = (TextView)findViewById(R.id.tv_exBuy);
		tv_sell = (TextView)findViewById(R.id.tv_exSell);
		tv_src = (TextView)findViewById(R.id.tv_toolSourceRate);
		tv_date = (TextView)findViewById(R.id.tv_toolDateUpdateRate);
		edt_amount = (EditText)findViewById(R.id.edt_toolAmountVND);
		edt_result = (EditText)findViewById(R.id.edt_toolExchangeResult);
		ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(getApplicationContext(), 
				android.R.layout.simple_spinner_item, Publics.listCurrency);
		if(Publics.list_Setting.size() > 0)
		{
			RateBuy = Publics.list_Setting.get(Publics.RateBuy_num).getValue();
			RateSell = Publics.list_Setting.get(Publics.RateSell_num).getValue();
			UpdateDate = Publics.list_Setting.get(Publics.UpdateDate_num).getValue();
			UpdateDate = Publics.formatDate(Publics.FormatDate, UpdateDate);
		}
		/***/
		spn_exchangeCurrency.setAdapter(adapterCurrency);
		btn_refresh.setOnClickListener(handleRefresh);
		btn_ex.setOnClickListener(handleExchange);
		tv_src.setText("tcnh.tdt.edu.vn");
		tv_buy.setText(RateBuy);
		tv_sell.setText(RateSell);
		tv_date.setText(UpdateDate);
		//create on click for 5 top button
		Publics.topFunction(this);
	}
	
	/**get data from website*/
	private void getRate()
	{
		try{
			Document doc = Jsoup.connect("http://tcnh.tdt.edu.vn/").get();
			Elements table = doc.select( "td.chitiet-vang1" );
			String[] t = new String[2];
			int i=0;
			for(Element Row : table)
			{
				if(Row.hasText())
				{
					String tmp = Row.text();
					if(rate.equals("USD"))
					{
						t[i] = tmp;
						i++;
					}
					if(tmp.equals("USD"))
					{
						rate = "USD";
					}
				}
			}
			RateBuy = t[0];
			RateSell = t[1];
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**Refresh*/
	OnClickListener handleRefresh = new OnClickListener() {
		
		public void onClick(View v) {
			getRate();
			UpdateDate = Publics.getCurrentDay();
			tv_buy.setText(RateBuy);
			tv_sell.setText(RateBuy);
			tv_date.setText(UpdateDate);
			Publics.list_Setting.get(Publics.RateBuy_num).setValue(RateBuy);
			Publics.list_Setting.get(Publics.RateSell_num).setValue(RateSell);
			Publics.list_Setting.get(Publics.UpdateDate_num).setValue(UpdateDate);
			SettingDataSource sdt = new SettingDataSource(getApplicationContext());
			try{
				sdt.open();
				sdt.updateSetting(Publics.list_Setting.get(Publics.RateBuy_num));
				sdt.updateSetting(Publics.list_Setting.get(Publics.RateSell_num));
				sdt.updateSetting(Publics.list_Setting.get(Publics.UpdateDate_num));
				sdt.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
				sdt.close();
			}
		}
	};
	
	/**exchange rate*/
	OnClickListener handleExchange = new OnClickListener() {
		public void onClick(View v) {
			String tmp = spn_exchangeCurrency.getSelectedItem().toString();
			if(tmp.equals("USD-US"))
			{
				double amount = Double.parseDouble(edt_amount.getText().toString().trim());
				double rte = Publics.ParseStringToDouble(RateBuy);
				double rs = amount/rte;
				edt_result.setText(Publics.formatNumber(rs));
			}
			else
			{
				edt_result.setText(edt_amount.getText().toString().trim());
			}
		}
	};
	
	
}
