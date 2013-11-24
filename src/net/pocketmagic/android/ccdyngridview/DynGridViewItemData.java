package net.pocketmagic.android.ccdyngridview;

import android.graphics.Bitmap;

/************************************************
 * Project: DynGridView sample
 * License: This code is Released under GPL v2 , (C) Radu Motisan, 2013
 * File: DynGridViewItemData.java 
 * Description: dynamic gridview item data structures
 * 
 * Author: Radu Motisan
 * Email: radu.motisan@gmail.com
 ************************************************/

public class DynGridViewItemData {
		   
	    private String m_szLabel;
	    private int m_nWidth, m_nHeight, m_nPadding;
	    private int m_nBackgroundRes, 
	    	m_nFavoriteOnRes,
	    	m_nFavoriteOffRes,
	    	m_nImgRes, m_nItemId;
	    private boolean m_bFavorite, m_bShowFavorite;
	    private boolean m_bAllowDrag = true;
	    
	   
	    

	    public DynGridViewItemData( String label,
	    		int w, int h, int padding,
	    		int backres, int favorite_on_res, int favorite_off_res, 
	    		boolean b_favorite_state,
	    		boolean b_show_fav,
	    		int itemimg, int id ) {
	    	m_nWidth = w; 
	    	m_nHeight = h;
	    	m_nPadding = padding;
	        m_szLabel = label;
	        m_nBackgroundRes = backres;
	        m_nFavoriteOnRes = favorite_on_res;
	        m_nFavoriteOffRes = favorite_off_res;
	        m_nImgRes = itemimg; 
	        m_nItemId = id;
	        m_bFavorite = b_favorite_state;
	        m_bShowFavorite = b_show_fav;
	      }
	    
	    public int getWidth() {
	    	return m_nWidth;
	    }
	    public int getHeight() {
	    	return m_nHeight;
	    }
	    public int getPadding() {
	    	return m_nPadding;
	    }
	    public String getLabel() { return m_szLabel; }
	    public void setLabel(String p) { m_szLabel = p;}
	    
	    	    
	    public int getBackgroundRes() {return m_nBackgroundRes;}
	    public void setBackgroundRes(int p) {m_nBackgroundRes = p;}
	   
	    public int getFavoriteOnRes() {return m_nFavoriteOnRes;}
	    public void setFavoriteOnRes(int p) {m_nFavoriteOnRes = p;}
	    
	    public int getFavoriteOffRes() {return m_nFavoriteOffRes;}
	    public void setFavoriteOffRes(int p) {m_nFavoriteOffRes = p;}
	    
	    public int getImageRes() { return m_nImgRes; }
	    public void setImageRes(int p) { m_nImgRes = p;}
	    
	    
	    public int getItemId() { return m_nItemId; }
	    public void setItemId(int p) { m_nItemId = p;}
	    
	    public boolean getFavoriteState() { return m_bFavorite; }
	    public void setFavoriteState(boolean p) { m_bFavorite = p;}
	    
	    public boolean getFavoriteStateShow() { return m_bShowFavorite; }
	    public void setFavoriteStateShow(boolean p) { m_bShowFavorite = p;}
	    
	    public boolean getAllowDrag() { return m_bAllowDrag; }
	    public void setAllowDrag(boolean p) { m_bAllowDrag = p;}

}