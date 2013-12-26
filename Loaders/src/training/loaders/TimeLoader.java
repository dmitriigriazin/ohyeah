package training.loaders;
import android.content.*;
import android.os.*;
import android.util.*;
import java.util.concurrent.*;
import java.text.*;
import java.util.*;

public class TimeLoader extends Loader<String>
{
	final String LOG_TAG = "myLogs";
	final int PAUSE = 10;

	public final static String ARGS_TIME_FORMAT = "time_format";
	public final static String TIME_FORMAT_SHORT = "h:mm:ss a";
	public final static String TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss";

	GetTimeTask getTimeTask;
	String format;
	
	public TimeLoader(Context context, Bundle args){
		super(context);
		Log.d(LOG_TAG, hashCode() + "Timeloader constructed");
		if(args!=null) format = args.getString(ARGS_TIME_FORMAT, TIME_FORMAT_SHORT);
		else format = TIME_FORMAT_SHORT; 
	}

	@Override
	protected void onStartLoading()
	{
		// TODO: Implement this method
		super.onStartLoading();
		Log.d(LOG_TAG, hashCode() + "onStartLoading");
		if(takeContentChanged()) forceLoad();
	}

	@Override
	protected void onStopLoading()
	{
		// TODO: Implement this method
		super.onStopLoading();
		Log.d(LOG_TAG, hashCode() + "onStopLoading");
	}

	@Override
	protected void onForceLoad()
	{
		// TODO: Implement this method
		super.onForceLoad();
		Log.d(LOG_TAG, hashCode() + "onForceLoad");
		if(getTimeTask!=null) getTimeTask.cancel(true);
		getTimeTask = new GetTimeTask();
		getTimeTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, format);
	}

	@Override
	protected void onAbandon()
	{
		// TODO: Implement this method
		super.onAbandon();
		Log.d(LOG_TAG, hashCode() + "onAbandon");
	}

	@Override
	protected void onReset()
	{
		// TODO: Implement this method
		super.onReset();
		Log.d(LOG_TAG, hashCode() + "onReset");
	}
	
	class GetTimeTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String[] p1)
		{
			// TODO: Implement this method
			Log.d(LOG_TAG, TimeLoader.this.hashCode() + " GetTimeTask do in background");
			try
			    {TimeUnit.SECONDS.sleep(3);}
			catch (InterruptedException e)
			    {return null;}
			SimpleDateFormat sdf = new SimpleDateFormat(p1[0]);
			return sdf.format(new Date());
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
			Log.d(LOG_TAG, TimeLoader.this.hashCode() + "GetTimeTask onPostExecute "+result);
			deliverResult(result);
		}

		
		
	}
	
	
}
