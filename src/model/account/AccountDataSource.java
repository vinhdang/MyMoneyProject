package model.account;

import java.util.ArrayList;
import java.util.List;

import database.infrastructure.AccountDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccountDataSource {
	
	  private SQLiteDatabase database;
	  private AccountDB dbHelper;
	  private String[] allColumns = { AccountDB.AId,
			  AccountDB.AName, AccountDB.ABalance, AccountDB.AUnit };

	  public AccountDataSource(Context context) {
	    dbHelper = new AccountDB(context);
	  }
	  
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }

	  public void close() {
	    dbHelper.close();
	  }
	  // insert new acc
	  public Account createAccount(String _name, double _balance, String _unit) {
		    ContentValues cv = new ContentValues();
		    cv.put(AccountDB.AName, _name);
	        cv.put(AccountDB.ABalance,_balance);
	        cv.put(AccountDB.AUnit,_unit);
		    long insertId = database.insert(AccountDB.DB_TABLE, null,
		        cv);
		    Cursor cursor = database.query(AccountDB.DB_TABLE,
		        allColumns, AccountDB.AId + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Account newAcc = cursorToAccount(cursor);
		    cursor.close();
		    return newAcc;
	  }
	  //delete acc
	  public void deleteAccount(Account ACC) {
		    long id = ACC.getId();
		    System.out.println("Comment deleted with id: " + id);
		    database.delete(AccountDB.DB_TABLE, AccountDB.AId
		        + " = " + id, null);
	  }
	  //get all acc
	  public List<Account> getAllAccounts() {
	    List<Account> Accs = new ArrayList<Account>();

	    Cursor cursor = database.query(AccountDB.DB_TABLE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Account acc = cursorToAccount(cursor);
	      Accs.add(acc);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return Accs;
	  }
	  
	  private Account cursorToAccount(Cursor cursor) {
		    Account acc = new Account();
		    acc.setId((cursor.getInt(0)));
		    acc.setAccountName((cursor.getString(1)));
		    acc.setFinalBalance(Double.parseDouble(cursor.getString(2)));
		    acc.setUnit(cursor.getString(3));
		    return acc;
	  }
}
