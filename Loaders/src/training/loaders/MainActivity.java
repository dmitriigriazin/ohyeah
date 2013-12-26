package training.loaders;

import android.app.*;
import android.app.LoaderManager.LoaderCallbacks;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;
import android.database.*;

public class MainActivity extends Activity implements LoaderCallbacks<String>
	{
		final String LOG_TAG = "myLogs";
		static final int LOADER_TIME_ID = 1;

		TextView tvTime;
		RadioGroup rgTimeFormat;
		static int lastCheckedId = 0;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		tvTime = (TextView) findViewById(R.id.tvTime);
		rgTimeFormat = (RadioGroup) findViewById(R.id.rgTimeFormat);
		
		Bundle bndl = new Bundle();
		bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
	    getLoaderManager().initLoader(LOADER_TIME_ID, bndl, this);
		lastCheckedId = rgTimeFormat.getCheckedRadioButtonId();
    }
	
		@Override
		public Loader<String> onCreateLoader(int p1, Bundle p2)
			{
				// TODO: Implement this method
				Loader<String> loader = null;
				if(p1 == LOADER_TIME_ID){
					loader = new TimeLoader(this, p2);
					Log.d(LOG_TAG, "onCreateLoader " + loader.hashCode());
					
				}
				return loader;
			}

		@Override
		public void onLoadFinished(Loader<String> p1, String p2)
			{
				// TODO: Implement this method
				Log.d(LOG_TAG, "onLoadFinished for loader " + p1.hashCode()
					  + ", result = " + p2);
				tvTime.setText(p2);
			}

		@Override
		public void onLoaderReset(Loader<String> p1)
			{
				// TODO: Implement this method
				Log.d(LOG_TAG, "onLoaderReset for loader " + p1.hashCode());
			}
			
		public void getTimeClick(View v){
	        Loader<String> loader;
			
			int id = rgTimeFormat.getCheckedRadioButtonId();
			if(id == lastCheckedId){
				loader = getLoaderManager().getLoader(LOADER_TIME_ID);
			}
			else{
				Bundle bndl = new Bundle();
				bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
				loader = getLoaderManager().restartLoader(LOADER_TIME_ID, bndl, this);
				lastCheckedId = id;
			}
			loader.forceLoad();
		}
		
		String getTimeFormat(){
			String result;
			switch(rgTimeFormat.getCheckedRadioButtonId()){
				case R.id.rdLong: 
					result = TimeLoader.TIME_FORMAT_LONG; 
				    break;
				case R.id.rdShort: 
					result = TimeLoader.TIME_FORMAT_SHORT;
				    break;
				default:
				    result = TimeLoader.TIME_FORMAT_SHORT;
			}
			return result;
		}
		
		public void observerClick(View v) {
			Log.d(LOG_TAG, "Observer clicked");
			Loader<String> loader = getLoaderManager().getLoader(LOADER_TIME_ID);
			final ContentObserver observer = loader.new ForceLoadContentObserver();
				v.postDelayed(new Runnable() {
							@Override
							public void run()
								{
									// TODO: Implement this method
									observer.dispatchChange(false);
								}
			}, 5000);
			}
}
			
			
			
			
			
			
			
			
			
			
			
