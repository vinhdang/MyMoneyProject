package model.plan;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import database.infrastructure.SQLiteDB;

public class PlanDataSource {
	

	  private SQLiteDatabase database;
	  private SQLiteDB dbHelper;
	  private String[] allColumns = { SQLiteDB.PId,
			  SQLiteDB.PName, SQLiteDB.PAccount, SQLiteDB.PCate, SQLiteDB.PAmount };
	  
	  public PlanDataSource(Context context) {
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
		  public Plan createPlan(String name, String account, String cate, double amount) {
			  Plan newCate = null;
			  try
			  { 
				  	ContentValues cv = new ContentValues();
				  	cv.put(SQLiteDB.PName, name);
			      	cv.put(SQLiteDB.PAccount, account);
			        cv.put(SQLiteDB.PCate, cate);
			        cv.put(SQLiteDB.PAmount, amount);
				    long insertId = database.insert(SQLiteDB.TABLE_PLAN, null, cv);
				    Cursor cursor = database.query(SQLiteDB.TABLE_PLAN,
				        allColumns, SQLiteDB.PId + " = " + insertId, null,
				        null, null, null);
				    cursor.moveToFirst();
				    newCate = cursorToPlan(cursor);
				    cursor.close();
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			  return newCate;
		  }
		  //delete acc
		  public void deletePlan(Plan Cate) {
			    long id = Cate.getId();
			    System.out.println("Comment deleted with id: " + id);
			    try{
			    	database.delete(SQLiteDB.TABLE_PLAN, SQLiteDB.PId + " = " + id, null);
			    }catch(Exception ex)
			    {
			    	ex.printStackTrace();
			    }
		  }
		  //get all acc
		  public List<Plan> getAllPlans() {
		    List<Plan> Plans = new ArrayList<Plan>();
		    try{
			    Cursor cursor = database.query(SQLiteDB.TABLE_PLAN,
			        allColumns, null, null, null, null, null);
			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Plan plan = cursorToPlan(cursor);
			      Plans.add(plan);
			      cursor.moveToNext();
			    }
			    // Make sure to close the cursor
			    cursor.close();
		    }catch(Exception ex)
		    {
		    	ex.printStackTrace();
		    }
		    return Plans;
		  }
		  
		  // insert new acc
		  public void insertPlan(Plan plan) {
			  if(plan != null)
			  {
				ContentValues cv = new ContentValues();
			    cv.put(SQLiteDB.PName, plan.getName());
		        cv.put(SQLiteDB.PAccount, plan.getAccount());
		        cv.put(SQLiteDB.PCate, plan.getCategory());
		        cv.put(SQLiteDB.PAmount, plan.getAmount());
		        try{
		        	database.insert(SQLiteDB.TABLE_PLAN, null, cv);
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
			  }
//			    Cursor cursor = database.query(SQLiteDB.TABLE_Plan,
//			        allColumns, SQLiteDB.AId + " = " + insertId, null,
//			        null, null, null);
//			    cursor.moveToFirst();
//			    Plan newAcc = cursorToPlan(cursor);
//			    cursor.close();
		  }
		  
		  //Get Plan by id
		  public Plan getPlanById(int _id)
		    {
			  	Plan newCate = null;
			  	try{
				  	Cursor mCursor = database.query(true, SQLiteDB.TABLE_PLAN, 
				  			allColumns, SQLiteDB.PId + "=" + _id, null, null, null, null, null);
			        if(mCursor != null)
			        {
			            mCursor.moveToFirst();
			        }
			        newCate = cursorToPlan(mCursor);
			        mCursor.close();
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
		        return newCate;
		    }
		  
		  private Plan cursorToPlan(Cursor cursor) {
			    Plan plan = new Plan();
			    plan.setId(cursor.getInt(0));
			    plan.setName(cursor.getString(1));
			    plan.setAccount(cursor.getString(2));
			    plan.setCategory(cursor.getString(3));
			    plan.setAmount(Double.parseDouble(cursor.getString(4)));
			    return plan;
		  }
}
