package billreminder.activity;

import general.activity.General;
import publics.Publics;
import main.activity.R;
import model.bill.Bill;
import model.bill.BillDataSource;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BillDetail extends Activity {
	private BillDataSource dataSource;
	private Bill bill;
	private int pos = -1;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill_detail);
		dataSource = new BillDataSource(this);
		
		TextView tvName = (TextView)findViewById(R.id.tv_itemName);
		TextView tvAmount = (TextView)findViewById(R.id.tv_amount);
		TextView tvCate = (TextView)findViewById(R.id.tv_category);
		TextView tvDay = (TextView)findViewById(R.id.tv_dueDay);
		TextView tvNote = (TextView)findViewById(R.id.tv_note);
		
		
		
		Intent intent = getIntent();
		if(intent != null)
		{
			pos = intent.getIntExtra("POS", -1);
			if(pos != -1)
			{
				bill = new Bill();
				if (intent.getBooleanExtra("paid", true))
					bill = Publics.list_PaidBill.get(pos);
				else
					bill = Publics.list_UpcomingBill.get(pos);
				
				id = bill.getBillId();
				tvName.setText(bill.getBillItem());
				tvCate.setText(bill.getBillCategory());
				tvDay.setText(bill.getBillDueDay());
				tvNote.setText(bill.getBillNote());
				tvAmount.setText(Publics.formatNumber(bill.getBillAmount()));
			}
		}
		
		
		//Update button
		Button btnUpdate = (Button)findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intentUpdateAccount = new Intent(BillDetail.this, UpdateBillReminder.class);
				intentUpdateAccount.putExtra("POS", id);
				startActivity(intentUpdateAccount);			
			}
		});
		//delete button
		Button btnDelete = (Button)findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Publics.msgBoxDelete(BillDetail.this, "This bill will be deleted from List bill",
						new DialogInterface.OnClickListener() {
		
							public void onClick(DialogInterface dialog, int which) {
								if(bill !=null)
								{
									try{
										if (bill.billType==0) //paid
											Publics.list_PaidBill.remove(bill);
										else
											Publics.list_UpcomingBill.remove(bill);
										dataSource.open();
										dataSource.deleteBill(bill);
										dataSource.close();
										Intent intent = new Intent(getApplicationContext(),General.class);
										intent.putExtra("tab", 3);
										startActivity(intent);
										
									}catch(Exception ex)
									{
										ex.printStackTrace();
									}
								}
							}
						}, new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();						
							}
						});
				
			}
		});
	}
}
