package net.pocketmagic.android.DynamicGridViewDemo;

import java.util.ArrayList;

import net.pocketmagic.android.ccdyngridview.DeleteZone;
import net.pocketmagic.android.ccdyngridview.DragController;
import net.pocketmagic.android.ccdyngridview.DynGridView;
import net.pocketmagic.android.ccdyngridview.DynGridView.DynGridViewListener;
import net.pocketmagic.android.ccdyngridview.DynGridViewAdapter;
import net.pocketmagic.android.ccdyngridview.DynGridViewItemData;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
Version:    0.1.0
Date:       November, 2013
License:	GPL v2
Description: Gridview complex sample for Android
****************************************************************************
Copyright (C) 2013 Radu Motisan  <radu.motisan@gmail.com>

http://www.pocketmagic.net

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
****************************************************************************

*/


public class DynamicGridViewDemo extends Activity implements   View.OnClickListener,
	DynGridView.DynGridViewListener{
	
	final static int		idTopLayout = Menu.FIRST + 100,
							idBack 		= Menu.FIRST + 101,
							idBotLayout	= Menu.FIRST + 102,
							idToggleScroll=Menu.FIRST+ 103,
							idToggleFavs = Menu.FIRST+ 104;
	
	DynGridViewAdapter	 	m_gridviewAdapter		= null; 
	DeleteZone 				mDeleteZone				= null;
	ArrayList<DynGridViewItemData> itemList			= null;
	DynGridView 			gv						= null;
	boolean					mToggleScroll			= false,
							mToggleFavs				= false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide titlebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //Create our top content holder
        
        
        RelativeLayout global_panel = new RelativeLayout (this);
		global_panel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		//global_panel.setGravity(Gravity.FILL);
		global_panel.setBackgroundDrawable(getResources().getDrawable( R.drawable.back));
		setContentView(global_panel);
		
		// +++++++++++++ TOP COMPONENT: the header
		RelativeLayout ibMenu = new RelativeLayout(this);
     	ibMenu.setId(idTopLayout);
		ibMenu.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
     	int ibMenuPadding = (int) 6;
     	ibMenu.setPadding(ibMenuPadding,ibMenuPadding,ibMenuPadding,ibMenuPadding);
     	RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
     	topParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
     	global_panel.addView(ibMenu,topParams);
     	// textview in ibMenu : card holder
		TextView cTV = new TextView(this);
		cTV.setText("Header");
		cTV.setTextColor(Color.rgb(255,255,255));
		int nTextH =  18;
		cTV.setTextSize(nTextH);
		cTV.setTypeface(Typeface.create("arial", Typeface.BOLD));
		RelativeLayout.LayoutParams lpcTV = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpcTV.addRule(RelativeLayout.CENTER_IN_PARENT);
		ibMenu.addView(cTV, lpcTV);
		// cancel button in ibMenu
		Button m_bCancel = new Button(this);
		m_bCancel.setId(idBack);
		m_bCancel.setOnClickListener((OnClickListener) this);
		m_bCancel.setText("Exit");
		nTextH =  12;
		m_bCancel.setTextSize(nTextH);
		m_bCancel.setTypeface(Typeface.create("arial", Typeface.BOLD));
		RelativeLayout.LayoutParams lpb = 
			new RelativeLayout.LayoutParams(150,50);//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpb.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lpb.addRule(RelativeLayout.CENTER_VERTICAL);
		ibMenu.addView(m_bCancel, lpb);
		
		mDeleteZone = new DeleteZone(this);
		//ivD.setImageResource(R.drawable.ic_launcher);
		LevelListDrawable a  = new LevelListDrawable();
		a.addLevel(0, 1, getResources().getDrawable(R.drawable.delete_icon)); // normal image
		a.addLevel(1, 2, getResources().getDrawable(R.drawable.delete_icon_red)); // delete image, drag over
		mDeleteZone.setImageDrawable(a);
		
		RelativeLayout.LayoutParams lpbDel = 
				new RelativeLayout.LayoutParams(50,50);//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpbDel.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lpbDel.addRule(RelativeLayout.CENTER_VERTICAL);
		ibMenu.addView(mDeleteZone, lpbDel);
			

		// +++++++++++++ BOTTOM COMPONENT: the footer
		RelativeLayout ibMenuBot = new RelativeLayout(this);
		ibMenuBot.setId(idBotLayout);
		ibMenuBot.setBackgroundDrawable(getResources().getDrawable(R.drawable.line));
		ibMenuBot.setPadding(ibMenuPadding,ibMenuPadding,ibMenuPadding,ibMenuPadding);
		RelativeLayout.LayoutParams botParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		botParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		global_panel.addView(ibMenuBot,botParams);
		// textview in ibMenu : card holder
		TextView cTVBot = new TextView(this);
		cTVBot.setText("www.pocketmagic.net");
		cTVBot.setTextColor(Color.rgb(179,116,197));
		cTVBot.setTextSize(nTextH);
		cTVBot.setTypeface(Typeface.create("arial", Typeface.NORMAL));
		RelativeLayout.LayoutParams lpcTVBot = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpcTVBot.addRule(RelativeLayout.CENTER_IN_PARENT);
		ibMenuBot.addView(cTVBot, lpcTVBot);
		
		Button m_bToggleScroll = new Button(this);
		m_bToggleScroll.setId(idToggleScroll);
		m_bToggleScroll.setOnClickListener((OnClickListener) this);
		m_bToggleScroll.setText("Scroll/Swipe");
		nTextH =  12;
		m_bToggleScroll.setTextSize(nTextH);
		m_bToggleScroll.setTypeface(Typeface.create("arial", Typeface.BOLD));
		lpb = new RelativeLayout.LayoutParams(150,50);//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpb.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lpb.addRule(RelativeLayout.CENTER_VERTICAL);
		ibMenuBot.addView(m_bToggleScroll, lpb);
		
		Button m_bToggleFavs = new Button(this);
		m_bToggleFavs.setId(idToggleFavs);
		m_bToggleFavs.setOnClickListener((OnClickListener) this);
		m_bToggleFavs.setText("Toggle Favs");
		nTextH =  12;
		m_bToggleFavs.setTextSize(nTextH);
		m_bToggleFavs.setTypeface(Typeface.create("arial", Typeface.BOLD));
		lpb = new RelativeLayout.LayoutParams(150,50);//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpb.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lpb.addRule(RelativeLayout.CENTER_VERTICAL);
		ibMenuBot.addView(m_bToggleFavs, lpb);
		
		// +++++++++++++ MIDDLE COMPONENT: all our GUI content
		LinearLayout midLayout = new LinearLayout (this);
		midLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		midLayout.setOrientation(LinearLayout.VERTICAL);
		//DragLayer mid = new DragLayer(this);
		//LinearLayout mid = new LinearLayout (this);
		
		
		//1. create gridview view 
		gv = new DynGridView(this);
		RelativeLayout.LayoutParams midParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		midParams.addRule(RelativeLayout.ABOVE,ibMenuBot.getId());
		midParams.addRule(RelativeLayout.BELOW,ibMenu.getId());
		global_panel.addView(gv,midParams );
		
		int imgs[] = new int[]{ R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,
				R.drawable.pic4,R.drawable.pic5,R.drawable.pic6, R.drawable.pic7,R.drawable.pic8,
				R.drawable.pic9};
		String texts[] = new String[]{"FS1000A", "BB Z10", "NaI Crystal","Android", 
				"CDV", "uRADMonitor", "Virtual", "Robot", "Meda"}; 
		
		//2. setup gridview data
		itemList = new ArrayList<DynGridViewItemData>();
		for (int i=0;i<50;i++) {
			DynGridViewItemData item = new DynGridViewItemData( 
					texts[i%9], // item string name
					150, 150, 15, // sizes: item w, item h, item padding
					R.drawable.item2, // item background image
					R.drawable.favon, 
					R.drawable.favoff, 
					true, // favorite state, if favs are enabled
					mToggleFavs, // favs disabled
					imgs[i%9], // item image res id
					i  // item id
					);

			//DynGridViewItemData item = new DynGridViewItemData("Item:"+i,
				//	R.drawable.itemback, R.drawable.itemimg,i);
			itemList.add(item);
		}
		
		//3. create adapter
		m_gridviewAdapter = new DynGridViewAdapter(this, itemList);
		
		//4. add adapter to gridview
		gv.setAdapter(m_gridviewAdapter);   
		//gv.setColumnWidth(300);
		gv.setNumColumns(4);
		gv.setSelection(2);
		gv.setDynGridViewListener((DynGridViewListener) this);

        
     
        
        // drag functionality
        gv.setDeleteView(mDeleteZone);
        DragController dragController = new DragController(this);
        
        gv.setDragController(dragController);
        
        // gv.getDragController().setDragEnabled(false); // disable DRAG functionality
        gv.setSwipeEnabled(mToggleScroll); // enable swipe but disable scrolling
       
    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		// If cancel is pressed, close our app
		if (id == idBack) finish();
		if (id == idToggleScroll) {
			mToggleScroll = !mToggleScroll;
			gv.setSwipeEnabled(mToggleScroll);
			String text = "Swipe enabled:"+mToggleScroll;
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();		
		}
		if (id == idToggleFavs) {
			mToggleFavs = !mToggleFavs;
			for (DynGridViewItemData item:itemList)
				item.setFavoriteStateShow(mToggleFavs);
			m_gridviewAdapter.notifyDataSetChanged();
			gv.invalidateViews();
			
			String text = "Favs enabled:"+mToggleFavs;
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		}
	}

	public void onItemClick(View v, int position, int id) {
		String text = "Click on:"+id+ " " +
				((DynGridViewItemData)m_gridviewAdapter.getItem(position)).getLabel();
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();		
	}

	public void onItemFavClick(View v, int position, int id) {
		itemList.get(position).setFavoriteState(!itemList.get(position).getFavoriteState());
		m_gridviewAdapter.notifyDataSetChanged();
		gv.invalidateViews();
		
		String text = "Item:"+position+ " fav state:" +
				((DynGridViewItemData)m_gridviewAdapter.getItem(position)).getFavoriteState();
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();		
	}

	public void onDragStart() {
	}

	public void onDragStop() {
	}

	public void onItemsChanged(int positionOne, int positionTwo) {
		String text = "You've changed item " + positionOne + " with item "+ positionTwo;
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();		
	}

	public void onItemDeleted(int position, int id) {
		String text = "You've deleted item " + id + " " +
				((DynGridViewItemData)m_gridviewAdapter.getItem(position)).getLabel();
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();		
	}

	public void onSwipeLeft() {
		String text = "Swipe LEFT detected";
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void onSwipeRight() {
		String text = "Swipe RIGHT detected";
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void onSwipeUp() {
		String text = "Swipe UP detected";
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();		
	}

	public void onSwipeDown() {
		String text = "Swipe DOWN detected";
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}



}