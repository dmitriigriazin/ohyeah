package com.mycompany.myapp2;

import android.app.*;
import android.database.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.view.ContextMenu.*;
import android.widget.AdapterView.*;
import android.util.*;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.*;
import java.util.concurrent.*;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor>
	{

	private final static int CM_DELETE_ID = 1;
	public final static String TAG = "myLog";
	final int LOADER1 = 0;
	final int LOADER2 = 1;
	
	
	DB db;
	ListView list;
	Cursor cursor;
	SimpleCursorAdapter SCAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		db= new DB(this);
		db.open();
		Log.d(MainActivity.TAG, "MA: db opened " + db.toString());
		cursor = db.getAllData();
		
		String[] from = new String[]{DB.COLUMN_IMG, DB.COLUMN_TXT};
		int[] to = new int[]{R.id.itemImage, R.id.itemText};
		
		SCAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
		list = (ListView) findViewById(R.id.lData);
		list.setAdapter(SCAdapter);
		
		registerForContextMenu(list);
	
		getLoaderManager().initLoader(LOADER1, null, (LoaderCallbacks<Cursor>) this);
    }
	
	public void onBtnClick(View v){
		db.addRec("Äwesome text " + (cursor.getCount()+1), R.drawable.ic_launcher);
		//cursor.requery();
		//Log.d(TAG, "Added, total:" + cursor.getCount());
		getLoaderManager().getLoader(LOADER1).forceLoad();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		// TODO: Implement this method
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DELETE_ID, 0, R.string.cm_delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		if(item.getItemId() == CM_DELETE_ID){
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
			db.delRec(acmi.id);
			getLoaderManager().getLoader(LOADER1).forceLoad();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		db.close();
		super.onDestroy();
	}
	

		@Override
		public Loader<Cursor> onCreateLoader(int p1, Bundle p2)
			{
				// TODO: Implement this method
				return new MyCursorLoader(this, db);
			}

		@Override
		public void onLoadFinished(Loader<Cursor> p1, Cursor p2)
			{
				// TODO: Implement this method
				SCAdapter.swapCursor(p2);
			}

		@Override
		public void onLoaderReset(Loader<Cursor> p1)
			{
				// TODO: Implement this method
			}
			
			
		static class MyCursorLoader extends CursorLoader {
			DB db;
			
			public MyCursorLoader(Context context, DB db){
				super(context);
				this.db = db;
				}

			@Override
			public Cursor loadInBackground()
				{
					// TODO: Implement this method
					Cursor cursor = db.getAllData();
					
					try
						{
							TimeUnit.SECONDS.sleep(3);
						}
					catch (InterruptedException e)
						{e.printStackTrace();}
					
					return cursor;
				}
			
			
		}

		
}
