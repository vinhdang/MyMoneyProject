package model.setting;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import database.infrastructure.SQLiteDB;

public class SettingDataSource {
	 private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.SId,
			  SQLiteDB.SItem, SQLiteDB.SValue };
	  
	  public SettingDataSource(Context context) {
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
		  public void deleteItem(Setting s) {
			    long id = s.getId();
			    System.out.println("Comment deleted with id: " + id);
			    try{
			    	database.delete(SQLiteDB.TABLE_SETTING, SQLiteDB.SId + " = " + id, null);
			    }catch(Exception ex)
			    {
			    	ex.printStackTrace();
			    }
		  }
		  //get all item
		  public List<Setting> getAllSettings() {
		    List<Setting> Settings = new ArrayList<Setting>();
		    try{
			    Cursor cursor = database.query(SQLiteDB.TABLE_SETTING,
			        allColumns, null, null, null, null, null);
			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Setting Setting = cursorToSetting(cursor);
			      Settings.add(Setting);
			      cursor.moveToNext();
			    }
			    // Make sure to close the cursor
			    cursor.close();
		    }catch(Exception ex)
		    {
		    	ex.printStackTrace();
		    }
		    return Settings;
		  }
		  
		  // insert new acc
		  public void insertSetting(Setting Setting) {
			  if(Setting != null)
			  {
				ContentValues cv = new ContentValues();
			    cv.put(SQLiteDB.SItem, Setting.getItem());
		        cv.put(SQLiteDB.SValue, Setting.getValue());
		        try{
		        	database.insert(SQLiteDB.TABLE_SETTING, null, cv);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
			  }
		  }
		  
		  //Get Setting by id
		  public Setting getSettingById(int _id)
		    {
			  	Setting newS = null;
			  	try{
				  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_SETTING, 
				  			allColumns, SQLiteDB.SId + "=" + _id, null, null, null, null, null);
			        if(mCursor != null)
			        {
			            mCursor.moveToFirst();
			        }
			        newS = cursorToSetting(mCursor);
			        mCursor.close();
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
		        return newS;
		    }
		  
		  private Setting cursorToSetting(Cursor cursor) {
			    Setting setting = new Setting();
			    setting.setId(cursor.getInt(0));
			    setting.setItem(cursor.getString(1));
			    setting.setValue(cursor.getString(2));
			    return setting;
		  }
}
