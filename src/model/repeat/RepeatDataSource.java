package model.repeat;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import database.infrastructure.SQLiteDB;

public class RepeatDataSource {
	private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.RId,
			  SQLiteDB.RItem, SQLiteDB.RSetupDate };
	  
	  public RepeatDataSource(Context context) 
	  {
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
		  
		  //delete item
		  public void deleteItem(Repeat s) {
			    long id = s.getId();
			    System.out.println("Comment deleted with id: " + id);
			    try{
			    	database.delete(SQLiteDB.TABLE_REPEAT, SQLiteDB.RId + " = " + id, null);
			    }catch(Exception ex)
			    {
			    	ex.printStackTrace();
			    }
		  }
		  //get all item
		  public List<Repeat> getAllRepeats() {
		    List<Repeat> Repeats = new ArrayList<Repeat>();
		    try{
			    Cursor cursor = database.query(SQLiteDB.TABLE_REPEAT,
			        allColumns, null, null, null, null, null);
			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Repeat Repeat = cursorToRepeat(cursor);
			      Repeats.add(Repeat);
			      cursor.moveToNext();
			    }
			    // Make sure to close the cursor
			    cursor.close();
		    }catch(Exception ex)
		    {
		    	ex.printStackTrace();
		    }
		    return Repeats;
		  }
		  
		  // insert new acc
		  public void insertRepeat(Repeat repeat) {
			  if(repeat != null)
			  {
				ContentValues cv = new ContentValues();
			    cv.put(SQLiteDB.RItem, repeat.getItem_id());
		        cv.put(SQLiteDB.RSetupDate, repeat.getSetupDate());
		        try{
		        	database.insert(SQLiteDB.TABLE_REPEAT, null, cv);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
			  }
		  }
		  
		  //Get Repeat by id
		  public Repeat getRepeatById(int _id)
		    {
			  	Repeat newS = null;
			  	try{
				  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_REPEAT, 
				  			allColumns, SQLiteDB.RId + "=" + _id, null, null, null, null, null);
			        if(mCursor != null)
			        {
			            mCursor.moveToFirst();
			        }
			        newS = cursorToRepeat(mCursor);
			        mCursor.close();
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
		        return newS;
		    }
		  
		  //Get Repeat by id item//
		  public Repeat getRepeatByIdItem(int item_id)
		    {
			  	Repeat newS = null;
			  	try{
				  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_REPEAT, 
				  			allColumns, SQLiteDB.RItem + "=" + item_id, null, null, null, null, null);
			        if(mCursor != null)
			        {
			            mCursor.moveToFirst();
			        }
			        newS = cursorToRepeat(mCursor);
			        mCursor.close();
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
		        return newS;
		    }
		  
		  /**Update*/
		  public boolean updateRepeat(Repeat set)
		  {
			  if(set != null)
			  {
				  	ContentValues cv = new ContentValues();
				    cv.put(SQLiteDB.RItem, set.getItem_id());
			        cv.put(SQLiteDB.RSetupDate,set.getSetupDate());
			        try{
			        	database.update(SQLiteDB.TABLE_REPEAT, cv, SQLiteDB.RId + "=" + set.getId(), null);
			        }catch(Exception ex)
			        {
			        	ex.printStackTrace();
			        	return false;
			        }
			        return true;
			  }
			  return false;
		  }
		  
		  private Repeat cursorToRepeat(Cursor cursor) {
			    Repeat Repeat = new Repeat();
			    Repeat.setId(cursor.getInt(0));
			    Repeat.setItem_id(cursor.getInt(1));
			    Repeat.setSetupDate(cursor.getString(2));
			    return Repeat;
		  }
}
