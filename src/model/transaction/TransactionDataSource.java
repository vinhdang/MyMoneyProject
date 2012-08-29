package model.transaction;

import java.util.ArrayList;
import java.util.List;

import database.infrastructure.SQLiteDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TransactionDataSource {
	
	  private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.TId,
			  SQLiteDB.TItem, SQLiteDB.TDate, SQLiteDB.TAmount, SQLiteDB.TCategory, SQLiteDB.TAccount,  
			   SQLiteDB.TNote, SQLiteDB.TPaymode, SQLiteDB.TRepeat};

	  public TransactionDataSource(Context context) {
	    dbHelper = new SQLiteDB(context);
	  }
	  
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }

	  public void close() {
	    dbHelper.close();
	  }
	  // insert new transaction
	  public Transaction createTransaction(String _item, double _amount, String _account, String _category, String _note, String _date, String _paymode, String _repeat) {
		    ContentValues cv = new ContentValues();
		    cv.put(SQLiteDB.TItem, _item);
	        cv.put(SQLiteDB.TAmount, _amount);
	        cv.put(SQLiteDB.TAccount,_account);
	        cv.put(SQLiteDB.TCategory, _category);
	        cv.put(SQLiteDB.TNote, _note);
	        cv.put(SQLiteDB.TDate, _date);
	        cv.put(SQLiteDB.TPaymode, _paymode);
	        cv.put(SQLiteDB.TRepeat, _repeat);
		    long insertId = database.insert(SQLiteDB.TABLE_TRANS, null,
		        cv);
		    Cursor cursor = database.query(SQLiteDB.TABLE_ACCOUNT,
		        allColumns, SQLiteDB.AId + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Transaction newTrans = cursorToTransaction(cursor);
		    cursor.close();
		    return newTrans;
	  }
	  
	  // insert new transaction
	  public void insertTransaction(Transaction trans) {
		  if(trans != null)
		  {

			//database.execSQL("insert into transactions(item, amount, category,tdate,note,paymode,repeat) values('61234',33,'dfsf','dfsf','dfsf','dfsf','dfsf')");
			ContentValues cv = new ContentValues();
			cv.put(SQLiteDB.TItem, trans.getTransactionItem());
			cv.put(SQLiteDB.TDate, trans.getTransactionDate());
		    cv.put(SQLiteDB.TAmount ,trans.getTransactionAmount());
		    cv.put(SQLiteDB.TCategory, trans.getTransactionCategory());
		    cv.put(SQLiteDB.TAccount,trans.getTransactionAccount());
		    cv.put(SQLiteDB.TNote, trans.getTransactionNote());
		    cv.put(SQLiteDB.TPaymode, trans.getTransactionPaymode());
		    cv.put(SQLiteDB.TRepeat, trans.getTransactionRepeat());		    
	        try{
	        
	        	database.insert(SQLiteDB.TABLE_TRANS, null, cv);
	        }catch(Exception ex)
	        {
	        	Log.i("Loi", "Khong them duoc");
	        	ex.printStackTrace();
	        }
		  }
//		    Cursor cursor = database.query(SQLiteDB.TABLE_ACCOUNT,
//		        allColumns, SQLiteDB.AId + " = " + insertId, null,
//		        null, null, null);
//		    cursor.moveToFirst();
//		    Account newAcc = cursorToAccount(cursor);
//		    cursor.close();
	  }
	  
	  //delete transaction
	  public void deleteTransaction(Transaction trans) {
		    long id = trans.getTransactionId();
			System.out.println("Comment deleted with id: " + id);
			try{
				database.delete(SQLiteDB.TABLE_TRANS, SQLiteDB.TId + " = " + id, null);
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			}
			//get all transaction
			public List<Transaction> getAllTransactions() {
			List<Transaction> trans = new ArrayList<Transaction>();
			try{
			Cursor cursor = database.query(SQLiteDB.TABLE_TRANS,
			allColumns, null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
			  Transaction tran = cursorToTransaction(cursor);
			  trans.add(tran);
			  cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			}catch(Exception ex)
			{
			ex.printStackTrace();
			}    
			return trans;
			}
			
			//Update transaction
			public boolean updateTransaction(Transaction trans)
			{
			if(trans != null)
			{
			  	ContentValues cv = new ContentValues();
			  	cv.put(SQLiteDB.TItem, trans.getTransactionItem());
			    cv.put(SQLiteDB.TAmount ,trans.getTransactionAmount());
			    cv.put(SQLiteDB.TAccount,trans.getTransactionAccount());
			    cv.put(SQLiteDB.TCategory, trans.getTransactionCategory());
			    cv.put(SQLiteDB.TNote, trans.getTransactionNote());
			    cv.put(SQLiteDB.TDate, trans.getTransactionDate());
			    cv.put(SQLiteDB.TPaymode, trans.getTransactionPaymode());
			    cv.put(SQLiteDB.TRepeat, trans.getTransactionRepeat());	
			    cv.put(SQLiteDB.TId, trans.getTransactionId());
		        try{
		        	database.update(SQLiteDB.TABLE_TRANS, cv, SQLiteDB.TId + "=" + trans.getTransactionId(), null);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        	return false;
		        }
		        return true;
		  }
		  return false;
	  }
	  
	  //Get transaction by id
	  public Transaction getTransactionById(int _id)
	    {
		  	Transaction newtrans = null;
		  	try{
			  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_TRANS, 
			  			allColumns, SQLiteDB.TId + "=" + _id, null, null, null, null, null);
		        if(mCursor != null)
		        {
		            mCursor.moveToFirst();
		        }
		        newtrans = cursorToTransaction(mCursor);
		        mCursor.close();
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	        return newtrans;
	    }
	  //
	  private Transaction cursorToTransaction(Cursor cursor) {
		    Transaction trans = new Transaction();
		    trans.setTransactionId((cursor.getInt(0)));
		    trans.setTransactionItem((cursor.getString(1)));
		    trans.setTransactionDate((cursor.getString(2)));
		    trans.setTransactionAmount((cursor.getDouble(3)));
		    trans.setTransactionCategory((cursor.getString(4)));
		    trans.setTransactionAccount((cursor.getString(5)));	    
		    trans.setTransactionNote((cursor.getString(6)));		    
		    trans.setTransactionPaymode((cursor.getString(7)));
		    trans.setTransactionRepeat((cursor.getString(8)));		 
			
		    return trans;
	  }
	  
	  //VuTuyen's add
	  
	  public String[] getAccountList()
	  {
		  Cursor cu = database.query(SQLiteDB.TABLE_ACCOUNT,
				  new String[]{SQLiteDB.AName}, null, null, null, null, null);
		  cu.moveToFirst();
		  String[] rs = new String[cu.getCount()];
		  int i=0;
		  while (!cu.isAfterLast())
		  {
			  rs[i++] = cu.getString(0);
			  cu.moveToNext();
		  }
		  cu.close();
		  return rs;
	  }
	  public String[] getCategoryList()
	  {
		  Cursor cu = database.query(SQLiteDB.TABLE_CATE,
				  new String[]{SQLiteDB.CName}, null, null, null, null, null);
		  cu.moveToFirst();
		  String[] rs = new String[cu.getCount()];
		  int i=0;
		  while (!cu.isAfterLast())
		  {
			  rs[i++] = cu.getString(0);
			  cu.moveToNext();
		  }
		  cu.close();
		  return rs;
	  }
}
