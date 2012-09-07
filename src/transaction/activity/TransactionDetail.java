package transaction.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.transaction.Transaction;
import model.transaction.TransactionDataSource;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionDetail extends Activity {
	private Button btn_transactionUpdate;
	private Button btn_transactionDelete;
	private int id;
	private Transaction data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_view_detail);
		data = new Transaction();
		//create function for 4 image button on bottom
		Publics.topFunction(this);	
		
		/***/
		btn_transactionDelete = (Button)findViewById(R.id.btn_transactionViewDelete);
		btn_transactionUpdate = (Button)findViewById(R.id.btn_transactionViewUpdate);
		
		/***/
		btn_transactionDelete.setOnClickListener(handleDelete);
		btn_transactionUpdate.setOnClickListener(handleUpdate);
		
		Intent in = getIntent();
        Bundle b = in.getExtras();
        id = b.getInt("POS");
        
        TransactionDataSource tds = new TransactionDataSource(this);
        tds.open();
        
        data = tds.getTransactionById(id);
        tds.close();
        //set account
        TextView tv = (TextView)findViewById(R.id.tv_trans_detail_account);
        tv.setText(data.getTransactionAccount());
        
        //set category
        tv = (TextView)findViewById(R.id.tv_trans_detail_category);
        tv.setText(data.getTransactionCategory());
        
        //set date
        tv = (TextView)findViewById(R.id.tv_trans_detail_date);
        tv.setText(data.getTransactionDate());
        
        //set note
        tv = (TextView)findViewById(R.id.tv_transDetailNote);
        tv.setText(data.getTransactionNote());
        
        //set paymode
        tv = (TextView)findViewById(R.id.tv_trans_detail_paymode);
        tv.setText(data.getTransactionPaymode());
        
        //set paymode
        tv = (TextView)findViewById(R.id.tv_transactionViewNameItem);
        tv.setText(data.getTransactionItem());
        
        //set paymode
        tv = (TextView)findViewById(R.id.tv_transdetailamount);
        tv.setText(Publics.formatNumber(data.getTransactionAmount()));
        
	}
	
	/***/
	OnClickListener handleUpdate = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), TransactionNew.class);
			Toast.makeText(getApplicationContext(), "Start Update .....", Toast.LENGTH_SHORT).show();
			i.putExtra("update", true);
			i.putExtra("POS", id);
			startActivity(i);
		}
	};
	
	/***/
	OnClickListener handleDelete = new OnClickListener() {
		
		public void onClick(View v) {
			Publics.msgBoxDelete(TransactionDetail.this, "Deleting.......",
					new DialogInterface.OnClickListener() {
	
						public void onClick(DialogInterface dialog, int which) {
							TransactionDataSource t = new TransactionDataSource(getApplicationContext());
							t.open();
							t.deleteTransaction(TransactionDetail.this.data);
							t.close();
							Intent intent = new Intent(getApplicationContext(),General.class);
							intent.putExtra("tab", 2);
							startActivity(intent);
						}
					}, new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
		}			
	};
	
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
