package in.co.sdslabs.iitr.todo;

import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Product> objects;
    CheckBox cbBuy;
    ListAdapter(Context context, ArrayList<Product> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
   
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_text, parent, false);
        }

        Product p = getProduct(position);

        ((TextView) view.findViewById(R.id.Task)).setText("Task  :"+p.task);
        ((TextView) view.findViewById(R.id.num)).setText("priority:"+p.num + "");
        ((TextView) view.findViewById(R.id.date)).setText(p.date);
        ((TextView) view.findViewById(R.id.time)).setText("time :"+p.time);
        ((TextView) view.findViewById(R.id.done)).setText("done :"+p.done);
        cbBuy = (CheckBox) view.findViewById(R.id.check);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
       cbBuy.setChecked(p.box);
        
        	 ((TextView) view.findViewById(R.id.Task)).setTextColor(Color.BLUE);
        //	 Log.i("sneha", "COLOR TO TEXTvIEW");
       
        if(p.done==1)
        {
        	cbBuy.setBackgroundColor(Color.GREEN);
        	//cbBuy.setChecked(true);
        }
       
        return view;
    }
    
    
   public void SetDone()
   {
	  cbBuy.setChecked(true);
   }

    Product getProduct(int position) {
        return ((Product) getItem(position));
    }

    ArrayList<Product> getBox() {
        ArrayList<Product> box = new ArrayList<Product>();
        for (Product p : objects) {
            if (p.box)
                box.add(p);
        }
        return box;
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(final CompoundButton buttonView,
                boolean isChecked) {
            getProduct((Integer) buttonView.getTag()).box = isChecked;
           int d=getProduct((Integer) buttonView.getTag()).id;
           
           Log.i("sneha", "hello");
          
           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
    
   			// set title
   			alertDialogBuilder.setTitle("Delete");
    
   			// set dialog message
   			alertDialogBuilder
   				.setMessage("Do you want to delete this task ?")
   				.setCancelable(false)
   				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
   					public void onClick(DialogInterface dialog,int id) {
   						// if this button is clicked, close
   						// current activity
   					
   						int a=getProduct((Integer) buttonView.getTag()).id;
   						try{
   						DatabaseHelper entry=new DatabaseHelper(ctx);
   						entry.open();
   						
   						entry.updateEntry(a, getProduct((Integer) buttonView.getTag()).task, getProduct((Integer) buttonView.getTag()).num, getProduct((Integer) buttonView.getTag()).date, getProduct((Integer) buttonView.getTag()).time, 1);
   						((Activity) ctx).finish();
   						Bundle b=new Bundle();
   						b.putBoolean("done", true);
   						
   						Intent intent = new Intent(ctx, MainActivity.class);
   						intent.putExtras(b);
                        ctx.startActivity(intent);
                       
   						entry.close();
   					   					    
   						just();
   						}
   						catch(Exception e)
   						{
   							
   						}
   					
   					}
   				  })
   				.setNegativeButton("No",new DialogInterface.OnClickListener() {
   					public void onClick(DialogInterface dialog,int id) {
   						// if this button is clicked, just close
   						// the dialog box and do nothing
   						getProduct((Integer) buttonView.getTag()).box = false;
   						((Activity) ctx).finish();
   						Intent intent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(intent);
   						dialog.cancel();
   					}
   				});
    
   				// create alert dialog
   				AlertDialog alertDialog = alertDialogBuilder.create();
    
   				// show it
   				alertDialog.show();
           }
        
    };
    public void just()
    {
    	
    }
}