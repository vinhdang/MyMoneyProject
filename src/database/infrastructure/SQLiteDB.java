package database.infrastructure;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDB extends SQLiteOpenHelper {
	/**Table Account*/
	public static final String AId = "id";
    public static final String AName = "name";
    public static final String ABalance = "balance";
    public static final String AUnit = "unit";
    public static final String ADescript = "descript";
    /**Table Category*/
    public static final String CId = "id";
    public static final String CName = "name";
    public static final String CType = "type";
    public static final String CDescript = "descript";    
    /**Table Plan*/
    public static final String PId = "id";
    public static final String PName = "name";
    public static final String PAccount = "account";
    public static final String PCate = "category";
    public static final String PAmount = "amount";
    
    /**Table Transaction*/
    public static final String TId = "id";
    public static final String TItem = "item";
    public static final String TAmount = "amount";
    public static final String TAccount = "account";
    public static final String TCategory = "category";
    public static final String TDate = "tdate";
    public static final String TNote = "note";
    public static final String TPaymode = "paymode";
    public static final String TRepeat = "repeat";
    /**Table Bill*/
    public static final String BId = "id";
    public static final String BItem = "item";
    public static final String BAmount = "amount";
    public static final String BCategory = "category";  
	public static final String BDueday = "dueday";
	public static final String BNote = "note";
	public static final String BNotification = "notification";
	public static final String BType = "billtype";  
	public static final String BRepeat = "repeat";  
	/**Table Setting*/
	 public static final String SId = "id";
	 public static final String SItem = "item";
	 public static final String SValue = "value";
	 /**Table Repeat*/
	 public static final String RId = "id";
	 public static final String RItem = "itemid";//save id of transaction have repeat
	 public static final String RSetupDate = "setdate";
	/**Name and version Database*/ 
	public static final String DB_NAME = "Database.db";
	public static final String TABLE_ACCOUNT = "Account";
	public static final String TABLE_CATE = "Category";
	public static final String TABLE_PLAN = "Plan";
	public static final String TABLE_TRANS = "Transactions";
	public static final String TABLE_BILL = "Bill";
	public static final String TABLE_SETTING = "Setting";
	public static final String TABLE_REPEAT = "Repeat";
	public static final int DB_VERSION = 3;
	
	public SQLiteDB(Context context) {
	    super(context, DB_NAME, null, DB_VERSION);
	  }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		 try
	     {
	        db.execSQL("CREATE TABLE Account(id integer PRIMARY KEY autoincrement, name text, balance double, unit text, descript text);");
	        db.execSQL("CREATE TABLE Category(id integer primary key autoincrement, name text, type text, descript text);");
	        db.execSQL("CREATE TABLE Plan(id integer primary key autoincrement, name text, account text, category text, amount double);");
	        db.execSQL("CREATE TABLE Transactions(id integer primary key autoincrement, item text, tdate text, amount double, category text, account text, note text, paymode text, repeat text);");
	        db.execSQL("CREATE TABLE Bill(id integer primary key autoincrement, item text, amount double, category text, dueday text, note text, notification text, repeat text, billtype integer);");
	        db.execSQL("CREATE TABLE Setting(id integer primary key, item text, value text);");
	        db.execSQL("CREATE TABLE Repeat(id integer primary key autoincrement, itemid integer, setdate text);");
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try
		{ 
			Log.i("DBAdapter", "Updating database...");
	        db.execSQL("DROP TABLE IF EXISTS Account");
	        db.execSQL("DROP TABLE IF EXISTS Category");
	        db.execSQL("DROP TABLE IF EXISTS Plan");
	        db.execSQL("DROP TABLE IF EXISTS Transactions");
	        db.execSQL("DROP TABLE IF EXISTS Bill");
	        db.execSQL("DROP TABLE IF EXISTS Setting");
	        db.execSQL("DROP TABLE IF EXISTS Repeat");
	        onCreate(db);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
