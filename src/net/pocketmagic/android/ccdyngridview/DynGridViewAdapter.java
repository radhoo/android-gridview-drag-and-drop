package net.pocketmagic.android.ccdyngridview;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
//import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;


/************************************************
 * Project: DynGridView sample
 * License: This code is Released under GPL v2 , (C) Radu Motisan, 2013
 * File: DynGridViewAdapter.java 
 * Description: custom adapter class for the dynamic grid view
 * 
 * Author: Radu Motisan
 * Email: radu.motisan@gmail.com
 ************************************************/

public class DynGridViewAdapter extends BaseAdapter {
	
    private Context context;
    private List<DynGridViewItemData> itemList, itemListOrig;

    public DynGridViewAdapter(Context context, List<DynGridViewItemData> itemList ) { 
        this.context = context;
        this.itemList = itemList;
    }

    public int getCount() {                        
        return itemList.size();
    }

    public Object getItem(int position) {     
        return itemList.get(position);
    }
    
    /**
	 * Removes the specified element from the list
	 */
	public void remove(DynGridViewItemData item) {
		itemList.remove(item);
		notifyDataSetChanged();
	}

	/**
	 * Removes the element at the specified position in the list
	 */
	public void remove(int position) {
		itemList.remove(position);
		notifyDataSetChanged();
	}
    public long getItemId(int position) {  
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    { 
    	DynGridViewItemData itemData = itemList.get(position);
        View v = new DynGridViewItemView(this.context, itemData );
        // set listeners to trigger the drag events
        v.setOnClickListener((OnClickListener) parent);
        if (((DynGridViewItemView) v).getFavoriteView() != null) {
        	((DynGridViewItemView) v).getFavoriteView().setId(DynGridViewItemView.FAVICONID);
        	((DynGridViewItemView) v).getFavoriteView().setOnClickListener((OnClickListener) parent);
        }
        v.setOnLongClickListener((OnLongClickListener) parent);//(OnLongClickListener) context);
        v.setOnTouchListener ((OnTouchListener) parent);
        return v;
    }
    
    
    /**
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 */
	public void set(int position, DynGridViewItemData item) {
		itemList.set(position, item);
		notifyDataSetChanged();
	}
	
	public void swapItems(int positionOne, int positionTwo) {
		DynGridViewItemData temp = (DynGridViewItemData) getItem(positionOne);
		set(positionOne, (DynGridViewItemData) getItem(positionTwo));
		set(positionTwo, temp);
	} 
	
	 public Filter getFilter() {
 	    return new Filter() {

 	        @Override
 	        protected FilterResults performFiltering(CharSequence constraint) {
 	            final FilterResults oReturn = new FilterResults();
 	            //final Document[] mDocus;
 	            final ArrayList<DynGridViewItemData> results = new ArrayList<DynGridViewItemData>();
 	            if (itemListOrig == null) itemListOrig = itemList;
 	            if (constraint != null) {
 	                if (itemListOrig != null && itemListOrig.size() > 0) {
 	                    for (final DynGridViewItemData g : itemListOrig) {
 	                    	// filter: check document name
 	                        if (g.getLabel().toLowerCase().contains(constraint.toString().toLowerCase()))
 	                            results.add(g);
 	                    }
 	                }
 	                oReturn.values = results;
 	            }
 	            return oReturn;
 	        }

 	        @SuppressWarnings("unchecked")
 	        @Override
 	        protected void publishResults(CharSequence constraint,
 	                FilterResults results) {
 	        	itemList = (ArrayList<DynGridViewItemData>) results.values;
 	            notifyDataSetChanged();
 	        }
 	    };
 	} 
}
