package transaction.activity;

import publics.Publics;
import main.activity.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TransactionDetail extends Activity {
	private Button btn_transactionUpdate;
	private Button btn_transactionDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_view_detail);
		
		//create function for 4 image button on bottom
		Publics.bottomFunction(this);	
		
		/***/
		btn_transactionDelete = (Button)findViewById(R.id.btn_transactionViewDelete);
		btn_transactionUpdate = (Button)findViewById(R.id.btn_transactionViewUpdate);
		
		/***/
		btn_transactionDelete.setOnClickListener(handleDelete);
		btn_transactionUpdate.setOnClickListener(handleUpdate);
	}
	
	/***/
	OnClickListener handleUpdate = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), TransactionNew.class);
			Toast.makeText(getApplicationContext(), "Start Update .....", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
	};
	
	/***/
	OnClickListener handleDelete = new OnClickListener() {
		
		public void onClick(View v) {
			Publics.msgBoxDelete(TransactionDetail.this, "Deleting.......",
					new DialogInterface.OnClickListener() {
	
						public void onClick(DialogInterface dialog, int which) {
							
							TransactionDetail.this.finish();
						}
					}, new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// cancel action
							
						}
					});
		}			
	};
}
