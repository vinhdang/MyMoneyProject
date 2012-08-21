package model.category;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import database.infrastructure.SQLiteDB;

public class CategoryDataSoure {
	
	  private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.CId,
			  SQLiteDB.CName, SQLiteDB.CType, SQLiteDB.CDescript };
	  
	  public CategoryDataSoure(Context context) {
		    dbHelper = new SQLiteDB(context);
		  }
		  
		  public void open() throws SQLException
		  	{
				  try{
				    database = dbHelper.getWritableDatabase();
				  }catch(Exception ex)
				  {
					  ex.printStackTrace();
				  }
			  }

		  public void close() {
		    dbHelper.close();
		  }
		  // insert new acc
		  public Category createCategory(String _name, String _type, String _descript) {
			  Category newCate = null;
			  try
			  { 
				  	ContentValues cv = new ContentValues();
				  	cv.put(SQLiteDB.CName, _name);
			      	cv.put(SQLiteDB.CType, _type);
			        cv.put(SQLiteDB.CDescript, _descript);
				    long insertId = database.insert(SQLiteDB.TABLE_CATE, null, cv);
				    Cursor cursor = database.query(SQLiteDB.TABLE_CATE,
				        allColumns, SQLiteDB.CId + " = " + insertId, null,
				        null, null, null);
				    cursor.moveToFirst();
				    newCate = cursorToCategory(cursor);
				    cursor.close();
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			  return newCate;
		  }
		  //delete acc
		  public void deleteCategory(Category Cate) {
			    long id = Cate.getId();
			    System.out.println("Comment deleted with id: " + id);
			    try{
			    	database.delete(SQLiteDB.TABLE_CATE, SQLiteDB.CId + " = " + id, null);
			    }catch(Exception ex)
			    {
			    	ex.printStackTrace();
			    }
		  }
		  //get all acc
		  public List<Category> getAllCategorys() {
		    List<Category> Cates = new ArrayList<Category>();
		    try{
			    Cursor cursor = database.query(SQLiteDB.TABLE_CATE,
			        allColumns, null, null, null, null, null);
	
			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Category cate = cursorToCategory(cursor);
			      Cates.add(cate);
			      cursor.moveToNext();
			    }
			    // Make sure to close the cursor
			    cursor.close();
		    }catch(Exception ex)
		    {
		    	ex.printStackTrace();
		    }
		    return Cates;
		  }
		  
		  // insert new acc
		  public void insertCategory(Category cate) {
			  if(cate != null)
			  {
				ContentValues cv = new ContentValues();
			    cv.put(SQLiteDB.CName, cate.getCategoryName());
		        cv.put(SQLiteDB.CType, cate.getCategoryType());
		        cv.put(SQLiteDB.CDescript, cate.getCategoryDescription());
		        try{
		        	database.insert(SQLiteDB.TABLE_CATE, null, cv);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
			  }
//			    Cursor cursor = database.query(SQLiteDB.TABLE_Category,
//			        allColumns, SQLiteDB.AId + " = " + insertId, null,
//			        null, null, null);
//			    cursor.moveToFirst();
//			    Category newAcc = cursorToCategory(cursor);
//			    cursor.close();
		  }
		  
		  //Get Category by id
		  public Category getCategoryById(int _id)
		    {
			  	Category newCate = null;
			  	try{
				  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_CATE, 
				  			allColumns, SQLiteDB.CId + "=" + _id, null, null, null, null, null);
			        if(mCursor != null)
			        {
			            mCursor.moveToFirst();
			        }
			        newCate = cursorToCategory(mCursor);
			        mCursor.close();
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
		        return newCate;
		    }
		  
		  private Category cursorToCategory(Cursor cursor) {
			    Category cate = new Category();
			    cate.setId(cursor.getInt(0));
			    cate.setCategoryName(cursor.getString(1));
			    cate.setCategoryType(cursor.getString(2));
			    cate.setCategoryDescription(cursor.getString(3));
			    return cate;
		  }
}
