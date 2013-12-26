package training.ca;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity
{
		ArrayList<Product> products = new ArrayList<Product>();
		BoxAdapter boxAdapter;
		boolean flag = true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		if(savedInstanceState!= null) flag=savedInstanceState.getBoolean("flag", true);
        setContentView(R.layout.main);
		
		if(flag) {fillData();
				Toast.makeText(this, "filling", Toast.LENGTH_LONG).show();
		}
		boxAdapter = new BoxAdapter(this, products);
		
		ListView list = (ListView) findViewById(R.id.LvMain);
		list.setAdapter(boxAdapter);
    }
	
		void fillData() {
				for (int i = 1; i <= 20; i++) {
						products.add(new Product("Product " + i, i * 1000,
												 R.drawable.ic_launcher, false));
					}
			}
			
		public void showResult(View v){
			String result = "Items in box";
			ArrayList<Product> box = boxAdapter.getBox();
			for(Product p : box){
				result += "\n" + p.name;
			}
			
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			}

		@Override
		protected void onSaveInstanceState(Bundle outState)
			{
				// TODO: Implement this method
				super.onSaveInstanceState(outState);
				flag = false;
				outState.putBoolean("flag", flag);
				
				Toast.makeText(this, "saving!", Toast.LENGTH_LONG).show();
			}
		
		
}
