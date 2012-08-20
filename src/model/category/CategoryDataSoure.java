package model.category;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import database.infrastructure.AccountDB;

public class CategoryDataSoure {
	
	  private SQLiteDatabase database;
	  private AccountDB dbHelper;
	  private String[] allColumns = { AccountDB.CId,
			  AccountDB.CName, AccountDB.CType, AccountDB.CDescript };
	  
	  public CategoryDataSoure(Context context) {
		    dbHelper = new AccountDB(context);
		  }
		  
		  public void open() throws SQLException {
			    database = dbHelper.getWritableDatabase();
			  }

		  public void close() {
		    dbHelper.close();
		  }
		  // insert new acc
		  public Category createCategory(String _name, String _type, String _descript) {
			    ContentValues cv = new ContentValues();
			    cv.put(AccountDB.CName, _name);
		        cv.put(AccountDB.CType, _type);
		        cv.put(AccountDB.CDescript, _descript);
			    long insertId = database.insert(AccountDB.TABLE_CATE, null, cv);
			    Cursor cursor = database.query(AccountDB.TABLE_CATE,
			        allColumns, AccountDB.CId + " = " + insertId, null,
			        null, null, null);
			    cursor.moveToFirst();
			    Category newCate = cursorToCategory(cursor);
			    cursor.close();
			    return newCate;
		  }
		  //delete acc
		  public void deleteCategory(Category Cate) {
			    long id = Cate.getId();
			    System.out.println("Comment deleted with id: " + id);
			    database.delete(AccountDB.TABLE_CATE, AccountDB.CId
			        + " = " + id, null);
		  }
		  //get all acc
		  public List<Category> getAllCategorys() {
		    List<Category> Cates = new ArrayList<Category>();

		    Cursor cursor = database.query(AccountDB.TABLE_CATE,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Category cate = cursorToCategory(cursor);
		      Cates.add(cate);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return Cates;
		  }
		  
		  private Category cursorToCategory(Cursor cursor) {
			    Category acc = new Category();
			    acc.setId(cursor.getInt(0));
			    acc.setCategoryName(cursor.getString(1));
			    acc.setCategoryType(cursor.getString(2));
			    acc.setCategoryDescription(cursor.getString(3));
			    return acc;
		  }
}
