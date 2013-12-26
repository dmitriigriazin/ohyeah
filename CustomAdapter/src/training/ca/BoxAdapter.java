package training.ca;
import android.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;
import android.widget.CompoundButton.*;

public class BoxAdapter extends BaseAdapter
	{
        Context ctx;
		LayoutInflater inflater;
		ArrayList<Product> objects;
		
		public BoxAdapter(Context context, ArrayList<Product> products){
			ctx = context;
			objects = products;
			inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount()
			{
				// TODO: Implement this method
				return objects.size();
			}

		@Override
		public Object getItem(int p1)
			{
				// TODO: Implement this method
				return objects.get(p1);
			}

		@Override
		public long getItemId(int p1)
			{
				// TODO: Implement this method
				return p1;
			}
		@Override
		public View getView(int pos, View cv, ViewGroup parent)
			{
				View view = cv;
				if(view == null){
					view = inflater.inflate(R.layout.item, parent, false);
				}
				
				Product p = (Product) getItem(pos);
				if(p!=null){
				((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
				((TextView) view.findViewById(R.id.tvPrice)).setText(p.price+"");
				((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
				
				CheckBox cb = (CheckBox) view.findViewById(R.id.cbItem);
				cb.setTag(pos);
				cb.setChecked(p.box);
				
				cb.setOnCheckedChangeListener(CheckChang);
				}
				// TODO: Implement this method
				return view;
			}
			
			ArrayList<Product> getBox (){
					ArrayList<Product> list = new ArrayList<Product>();
					for( Product p : objects) 
					    if(p.box) list.add(p);
				    return list;	
			}
			
		OnCheckedChangeListener CheckChang = new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
					{
						// TODO: Implement this method
						((Product)getItem((Integer)p1.getTag())).box = p2;
					}
			};

}
