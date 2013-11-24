package net.pocketmagic.android.ccdyngridview;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/************************************************
 * Project: DynGridView sample
 * License: This code is Released under GPL v2 , (C) Radu Motisan, 2013
 * File: DynGridViewItemView.java 
 * Description: dynamic gridview item view constructor
 * 
 * Author: Radu Motisan
 * Email: radu.motisan@gmail.com
 ************************************************/


class DynGridViewItemView extends RelativeLayout implements DragSource, DropTarget {       
	public static final int FAVICONID = -5;
	DynGridViewItemData mitem;
	ImageView ivFavorite;
	
	public ImageView getFavoriteView() {
		return ivFavorite;
	}
	
	public DynGridViewItemView(Context context, DynGridViewItemData item) 
	{
		super( context );
		mitem = item;
		// get/set ID
		setId(item.getItemId());
		
		ImageView ivBack = new ImageView(context);
		ivBack.setImageResource(item.getBackgroundRes());
		RelativeLayout.LayoutParams lp_ivBack = new RelativeLayout.LayoutParams(
				mitem.getWidth(), mitem.getHeight());
				//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_ivBack.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(ivBack, lp_ivBack);
		
		// Configure holder layout
		RelativeLayout panel = new RelativeLayout(context);
		panel.setPadding(mitem.getPadding(), mitem.getPadding(), mitem.getPadding(), mitem.getPadding());
		RelativeLayout.LayoutParams lp_PV = new RelativeLayout.LayoutParams(
				mitem.getWidth(), mitem.getHeight());
				//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_PV.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(panel, lp_PV);
		
		//info for logo
		ImageView ivLogo;
		ivLogo = new ImageView(context);
		ivLogo.setId(100);
		RelativeLayout.LayoutParams lp_logo = new RelativeLayout.LayoutParams(
				mitem.getWidth() - 2*mitem.getPadding(), LayoutParams.WRAP_CONTENT);
		lp_logo.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		lp_logo.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		ivLogo.setImageResource(item.getImageRes());
		
		
		
		panel.addView(ivLogo, lp_logo);
		
		if (item.getFavoriteStateShow()) {
			if(item.getFavoriteState())
			{
				ivFavorite = new ImageView(context);
				ivFavorite.setImageResource(item.getFavoriteOnRes());
				RelativeLayout.LayoutParams lp_ivFav = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp_ivFav.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_ivFav.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				addView(ivFavorite, lp_ivFav);
			} else {
				ivFavorite = new ImageView(context);
				ivFavorite.setImageResource(item.getFavoriteOffRes());
				RelativeLayout.LayoutParams lp_ivFav = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp_ivFav.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp_ivFav.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				addView(ivFavorite, lp_ivFav);
			}
		}
		// the play logo currently disabled
		/*ImageView ivPlay = new ImageView(context);
		ivPlay.setImageResource(item.getFavoritePlayRes());
		RelativeLayout.LayoutParams lp_ivPlay = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//lp_ivPlay.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		//lp_ivPlay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp_ivPlay.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(ivPlay, lp_ivPlay);*/
		
		// text below image
		TextView textName = new TextView( context );
		textName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
		//textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setTextColor(Color.GRAY);
		textName.setShadowLayer(2, 1, 1, Color.BLACK);

		textName.setText( item.getLabel());
		textName.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams lp_text = new RelativeLayout.LayoutParams(
				mitem.getWidth() - 2*mitem.getPadding(), LayoutParams.WRAP_CONTENT);
		lp_text.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp_text.addRule(RelativeLayout.CENTER_HORIZONTAL);
		panel.addView(textName, lp_text);
	}
	
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//this.setMeasuredDimension(mitem.getWidth(), mitem.getHeight());
	}

	/**
     */
    // interface DragTarget implementation: handle what to do when we are the Drag TARGET
	// our item is a target, and is receiving a new drop from somewhere
	// --------------------------------------------------------------------------- //

	/**
	 * the drop is released on our item
	 */
	public void onDrop(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
	}

	public void onDragEnter(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
	}

	/**
	 * Another item is being dragged and hovering on us, switch items animation ?!
	 */
	public void onDragOver(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
		// TODO: switch animation
	}

	public void onDragExit(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
	}
	
	/**
	 * Another item has been dragged on us, accept or reject
	 * By default all gridview items accept any drop
	 */
	public boolean acceptDrop(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
		return true;
	}

	public Rect estimateDropLocation(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo, Rect recycle) {
		return null;
	}

    /**
     */
    // interface DragSource implementation: handle what to do when we are the Drag SOURCE
	// our item is dragged away
	// --------------------------------------------------------------------------- //

	/**
	 * To accept to be dragged. By default all gridview items can be dragged!
	 */
	public boolean allowDrag() {
		//return true;
		return mitem.getAllowDrag();
	}

	/**
	 * Get the drag controller object
	 */
	public void setDragController(DragController dragger) {
	    // Do nothing. We do not need to know the controller object.
	}

	/**
	 * one of our gridviewitems is now a source and is being dragged and released on Target
	 * Target may accept of reject this drag
	 */
	public void onDropCompleted(View target, boolean success) {		
		// Do nothing
	}

	 public boolean onDown(MotionEvent e) {
		 Log.e("bla","ondown");
	  return true;
	 }

	 public boolean onSingleTapUp(MotionEvent e) {
		 Log.e("bla","onSingleTapUp");
	  return true;
	 }

}
