package com.mycompany.myapp2;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;
import android.util.*;

public class DB
{
	private static final String DB_NAME = "mydb1";
	private final static int DB_VERSION = 1;
	private final static String DB_TABLE = "mytable";
	
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_TXT = "txt";
	public final static String COLUMN_IMG = "img";
	
	private final static String DB_CREATE = "create table "+DB_TABLE+"("+
	        COLUMN_ID+" integer primary key autoincrement, "+
			COLUMN_IMG+" integer, "+
			COLUMN_TXT+" text"+
			");";
			
    private final Context mCtx;
	
	private DBHelper mDBHelper;
	private SQLiteDatabase mSQLite;	
	public DB(Context context){
		mCtx=context;
	}
	
	public void open(){
		mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
		mSQLite = mDBHelper.getWritableDatabase();
		Log.d(MainActivity.TAG, "sql opened" + mSQLite.isOpen() + "readonly:" + mSQLite.isReadOnly());
		//mDBHelper.onCreate(mSQLite);
	}
	
	public void close(){
		if(mDBHelper!=null) mDBHelper.close();
	}
	
	public Cursor getAllData(){
		return mSQLite.query(DB_TABLE, null, null, null, null, null, null);
	}
	
	public void addRec(String txt, int img){
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_IMG, img);
		cv.put(COLUMN_TXT, txt);
		mSQLite.insert(DB_TABLE, null, cv);
	}
	
	public void delRec(long id){
		mSQLite.delete(DB_TABLE, COLUMN_ID + "=" + id, null);
	}
	
	private class DBHelper extends SQLiteOpenHelper
	{

		public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
			super(context, name, factory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase p1)
		{
			//
			Log.d(MainActivity.TAG, "sql try to create: " + DB_CREATE);
			p1.execSQL(DB_CREATE);
			Log.d(MainActivity.TAG, "sql creating " + p1.isOpen());
			ContentValues cv = new ContentValues();
			for(int i = 0; i<5; i++){
				cv.put(COLUMN_TXT, "Awesome text "+i);
				cv.put(COLUMN_IMG, R.drawable.ic_launcher);
				p1.insert(DB_TABLE, null, cv);
			}
			Log.d(MainActivity.TAG, "sql inserted" + p1.query(DB_TABLE, null, null, null, null, null, null).getCount());
		}

		@Override
		public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
		{
			// TODO: Implement this method
		}
		
	}
	
	
	
}
