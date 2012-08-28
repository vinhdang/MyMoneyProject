package model.bill;

import java.util.ArrayList;
import java.util.List;

import database.infrastructure.SQLiteDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BillDataSource {
	
	  private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.BId,
			  SQLiteDB.BItem, SQLiteDB.BAmount, SQLiteDB.BCategory, SQLiteDB.BDueday, SQLiteDB.BNote, SQLiteDB.BNotification, SQLiteDB.BRepeat};

	  public BillDataSource(Context context) {
	    dbHelper = new SQLiteDB(context);
	  }
	  
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }

	  public void close() {
	    dbHelper.close();
	  }
	  // insert new bill
	  public Bill createBill(String _item, double _amount, String _category, String _dueday, String _note, String _notification, String _repeat ) {
		    ContentValues cv = new ContentValues();
		    cv.put(SQLiteDB.BItem, _item);
	        cv.put(SQLiteDB.BAmount,_amount);
	        cv.put(SQLiteDB.BCategory,_category);
	        cv.put(SQLiteDB.BDueday, _dueday);
	        cv.put(SQLiteDB.BNote, _note);
	        cv.put(SQLiteDB.BNotification, _notification);
	        cv.put(SQLiteDB.BRepeat, _repeat);
		    long insertId = database.insert(SQLiteDB.TABLE_BILL, null,
		        cv);
		    Cursor cursor = database.query(SQLiteDB.TABLE_BILL,
		        allColumns, SQLiteDB.BId + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Bill newBill = cursorToBill(cursor);
		    cursor.close();
		    return newBill;
	  }
	  
	  // insert new bill
	  public void insertBill(Bill bill) {
		  if(bill != null)
		  {
			ContentValues cv = new ContentValues();
		    cv.put(SQLiteDB.BItem, bill.getBillItem());
	        cv.put(SQLiteDB.BAmount, bill.getBillAmount());
	        cv.put(SQLiteDB.BCategory, bill.getBillCategory());
	        cv.put(SQLiteDB.BDueday, bill.getBillDueDay());
	        cv.put(SQLiteDB.BNote, bill.getBillNote());
	        cv.put(SQLiteDB.BNotification, bill.getBillNotification());
	        cv.put(SQLiteDB.BRepeat, bill.getBillRepeat());
	        try{
	        	database.insert(SQLiteDB.TABLE_BILL, null, cv);
	        }catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
		  }
	  }
	  
	  //delete bill
	  public void deleteBill(Bill bill) {
		    long id = bill.getBillId();
		    System.out.println("Comment deleted with id: " + id);
		    try{
		    	database.delete(SQLiteDB.TABLE_BILL, SQLiteDB.BId + " = " + id, null);
		    }catch(Exception ex)
		    {
		    	ex.printStackTrace();
		    }
	  }
	  //get all bill
	  public List<Bill> getAllBills() {
	    List<Bill> Bills = new ArrayList<Bill>();
	    try{
	    	Cursor cursor = database.query(SQLiteDB.TABLE_BILL,
	        allColumns, null, null, null, null, null);
	    	cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Bill bill = cursorToBill(cursor);
		      Bills.add(bill);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
	    }catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }    
	    return Bills;
	  }
	  
	  //Update bill
	  public boolean updateBill(Bill bill)
	  {
		  if(bill != null)
		  {
			  	ContentValues cv = new ContentValues();
			  	cv.put(SQLiteDB.BItem, bill.getBillItem());
		        cv.put(SQLiteDB.BAmount, bill.getBillAmount());
		        cv.put(SQLiteDB.BCategory, bill.getBillCategory());
		        cv.put(SQLiteDB.BDueday, bill.getBillDueDay());
		        cv.put(SQLiteDB.BNote, bill.getBillNote());
		        cv.put(SQLiteDB.BNotification, bill.getBillNotification());
		        cv.put(SQLiteDB.BRepeat, bill.getBillRepeat());
		        try{
		        	database.update(SQLiteDB.TABLE_BILL, cv, SQLiteDB.BId + "=" + bill.getBillId(), null);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        	return false;
		        }
		        return true;
		  }
		  return false;
	  }
	  
	  //Get bill by id
	  public Bill getBillById(int _id)
	    {
		  	Bill newBill = null;
		  	try{
			  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_BILL, 
			  			allColumns, SQLiteDB.BId + "=" + _id, null, null, null, null, null);
		        if(mCursor != null)
		        {
		            mCursor.moveToFirst();
		        }
		        newBill = cursorToBill(mCursor);
		        mCursor.close();
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	        return newBill;
	    }
	  //
	  private Bill cursorToBill(Cursor cursor) {
		    Bill bill = new Bill();
		    bill.setBillId((cursor.getInt(0)));
		    bill.setBillItem((cursor.getString(1)));
		    bill.setBillAmount(cursor.getDouble(2));
		    bill.setBillCategory(cursor.getString(3));
		    bill.setBillDueDay(cursor.getString(4));
		    bill.setBillNote(cursor.getString(5));
		    bill.setBillNotification(cursor.getString(6));
		    bill.setBillRepeat(cursor.getString(7));
		    return bill;
	  }
}
