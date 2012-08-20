package database.infrastructure;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AccountDB extends SQLiteOpenHelper {

	public static final String AId = "id";
    public static final String AName = "name";
    public static final String ABalance = "balance";
    public static final String AUnit = "unit";
    
    public static final String CId = "id";
    public static final String CName = "name";
    public static final String CType = "type";
    public static final String CDescript = "descript";    
     
    public static final String DB_NAME = "Database";
    public static final String DB_TABLE = "Account";
    public static final String TABLE_CATE = "Category";
    public static final int DB_VERSION = 1;
//    SQLiteDatabase db;
    
//	public AccountDB(Context context, String name, CursorFactory factory,
//			int version) {
//		super(context, name, factory, version);
//	}
	
	public AccountDB(Context context) {
	    super(context, DB_NAME, null, DB_VERSION);
//	    try
//        {
//           db.execSQL("CREATE TABLE Account(id integer primary key autoincrement, name text, balance double, unit text);");
//        }
//        catch(SQLException ex)
//        {
//            ex.printStackTrace();
//        }
	  }

	@Override
	public void onCreate(SQLiteDatabase db) {
		 try
         {
            db.execSQL("CREATE TABLE Account(id integer PRIMARY KEY autoincrement, name text, balance double, unit text);");
            db.execSQL("CREATE TABLE Category(id integer primary key autoincrement, name text, type text, descript text);");
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
	        onCreate(db);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
