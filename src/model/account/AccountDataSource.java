package model.account;

import java.util.ArrayList;
import java.util.List;

import database.infrastructure.SQLiteDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccountDataSource {
	
	  private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.AId,
			  SQLiteDB.AName, SQLiteDB.ABalance, SQLiteDB.AUnit, SQLiteDB.ADescript };

	  public AccountDataSource(Context context) {
	    dbHelper = new SQLiteDB(context);
	  }
	  
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }

	  public void close() {
	    dbHelper.close();
	  }
	  // insert new acc
	  public Account createAccount(String _name, double _balance, String _unit, String des ) {
		    ContentValues cv = new ContentValues();
		    cv.put(SQLiteDB.AName, _name);
	        cv.put(SQLiteDB.ABalance,_balance);
	        cv.put(SQLiteDB.AUnit,_unit);
	        cv.put(SQLiteDB.ADescript, des);
		    long insertId = database.insert(SQLiteDB.TABLE_ACCOUNT, null,
		        cv);
		    Cursor cursor = database.query(SQLiteDB.TABLE_ACCOUNT,
		        allColumns, SQLiteDB.AId + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Account newAcc = cursorToAccount(cursor);
		    cursor.close();
		    return newAcc;
	  }
	  
	  // insert new acc
	  public void insertAccount(Account acc) {
		  if(acc != null)
		  {
			ContentValues cv = new ContentValues();
		    cv.put(SQLiteDB.AName, acc.getAccountName());
	        cv.put(SQLiteDB.ABalance, acc.getFinalBalance());
	        cv.put(SQLiteDB.AUnit, acc.getUnit());
	        cv.put(SQLiteDB.ADescript, acc.getDescript());
	        try{
	        	database.insert(SQLiteDB.TABLE_ACCOUNT, null, cv);
	        }catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
		  }
	  }
	  
	  //delete acc
	  public void deleteAccount(Account ACC) {
		    long id = ACC.getId();
		    System.out.println("Comment deleted with id: " + id);
		    try{
		    	database.delete(SQLiteDB.TABLE_ACCOUNT, SQLiteDB.AId + " = " + id, null);
		    }catch(Exception ex)
		    {
		    	ex.printStackTrace();
		    }
	  }
	  //get all acc
	  public List<Account> getAllAccounts() {
	    List<Account> Accs = new ArrayList<Account>();
	    try{
	    	Cursor cursor = database.query(SQLiteDB.TABLE_ACCOUNT,
	        allColumns, null, null, null, null, null);
	    	cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Account acc = cursorToAccount(cursor);
		      Accs.add(acc);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
	    }catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }    
	    return Accs;
	  }
	  
	  //Update account
	  public boolean updateAccount(Account acc)
	  {
		  if(acc != null)
		  {
			  	ContentValues cv = new ContentValues();
			    cv.put(SQLiteDB.AName, acc.getAccountName());
		        cv.put(SQLiteDB.ABalance,acc.getFinalBalance());
		        cv.put(SQLiteDB.AUnit,acc.getUnit());
		        cv.put(SQLiteDB.ADescript, acc.getDescript());
		        try{
		        	database.update(SQLiteDB.TABLE_ACCOUNT, cv, SQLiteDB.AId + "=" + acc.getId(), null);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        	return false;
		        }
		        return true;
		  }
		  return false;
	  }
	  
	  //Get account by id
	  public Account getAccountById(int _id)
	    {
		  	Account newAcc = null;
		  	try{
			  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_ACCOUNT, 
			  			allColumns, SQLiteDB.AId + "=" + _id, null, null, null, null, null);
		        if(mCursor != null)
		        {
		            mCursor.moveToFirst();
		        }
		        newAcc = cursorToAccount(mCursor);
		        mCursor.close();
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	        return newAcc;
	    }
	  //
	  private Account cursorToAccount(Cursor cursor) {
		    Account acc = new Account();
		    acc.setId((cursor.getInt(0)));
		    acc.setAccountName((cursor.getString(1)));
		    acc.setFinalBalance(cursor.getDouble(2));
		    acc.setUnit(cursor.getString(3));
		    acc.setDescript(cursor.getString(4));
		    return acc;
	  }
}
