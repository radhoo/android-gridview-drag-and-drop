package net.pocketmagic.android.ccdyngridview;



import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.GridView;
import android.widget.Toast;

/************************************************
 * Project: DynGridView sample
 * License: This code is Released under GPL v2 , (C) Radu Motisan, 2013
 * File: DynGridView.java 
 * Description:  Create a GridView with dragging functionality
 * The GridViewAdapter creates each of the item-views, and sets 
 * parent (the gridview) as receiver for click,touch, long click
 * thus we immplement these listener to handle all the logic behind
 * 
 * Author: Radu Motisan
 * Email: radu.motisan@gmail.com
 ************************************************/

public class DynGridView extends GridView 
	implements DragController.DragListener, View.OnLongClickListener, View.OnTouchListener, View.OnClickListener {


	DynGridViewAdapter	 			m_gridviewAdapter		= null; 
	// drag and drop
	private DragController 			mDragController			= null;	// Object that handles a drag-drop sequence. It intersacts with DragSource and DropTarget objects.
	private DynGridViewListener		mListener				= null; // listener for dyn grid view events such as item click or drag events
	private boolean 				mLongClickStartsDrag 	= true; // If true, it takes a long click to start the drag operation.
	private DeleteZone 				mDeleteView				= null; // the view (usually an image view with level drawables) that shows the delete zone only when dragging started!
	boolean							mDragging				= false;// true is we are currently dragging something
	private GestureDetector 		gestureDetector;
	private boolean					mSwipeEnabled			 = false;
	
	public DynGridView(Context context) {
		super(context);
		gestureDetector = new GestureDetector(context, new SwipeGestureDetector());
	}
	
	public void setSwipeEnabled(boolean mode) {
		mSwipeEnabled = mode;
	}
	
	public void setDeleteView(DeleteZone deleteView)
	{
	   mDeleteView = deleteView; // most likely an imageview
	   if (mDeleteView != null) {
           mDeleteView.setVisibility(View.INVISIBLE);
       }
	} 
	
	public void setDragController(DragController dragController) {
		if (dragController == null) {
			return;
		}
		mDragController = dragController;
		mDragController.setDragListener(this); //gets drag events: the DragController.DragListener interface
	}
	
	public DragController getDragController() {
		return mDragController;
	}
	
	private class SwipeGestureDetector extends SimpleOnGestureListener {
	    // Swipe properties, you can change it to make the swipe longer or shorter and speed
	    private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 200;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    	try {
	    		float diffAbs = Math.abs(e1.getY() - e2.getY());
				float diffx = e1.getX() - e2.getX();
				float diffy = e1.getY() - e2.getY();
				//if (diffAbs > SWIPE_MAX_OFF_PATH) return false;
				if (diffx > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) return onLeftSwipe();
				else if (-diffx > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) return onRightSwipe();
				else if (diffy > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) return onUpSwipe();
				else if (-diffy > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) return onDownSwipe();
	    	} catch (Exception e) {
	    	  Log.e("Err", "Error on gestures"+e.getMessage());
	    	}
	    	return false;
	    }
	}//end of SwipeGestureDetector class

	//methods called by SwipeGestureDetector when the approrpiate swipes occured
	private boolean onLeftSwipe() {
		Log.e("bla", "Successfully have the swipe working for left");
		if (mListener != null) mListener.onSwipeLeft();
		return true;
	}

	private boolean onRightSwipe() {
		Log.e( "bla", "Successfully have the swipe working for right");
		if (mListener != null) mListener.onSwipeRight();
		return true;
	}

	private boolean onUpSwipe() {
		Log.e( "bla", "Successfully have the swipe working for up");
		if (mListener != null) mListener.onSwipeUp();
		return true;
	}
	private boolean onDownSwipe() {
		Log.e( "bla", "Successfully have the swipe working for down");
		if (mListener != null) mListener.onSwipeDown();
		return true;
	}
	
   /**
     * Interface to receive notifications when a drag starts or stops
     */
    public interface DynGridViewListener {
        
    	/**
    	 * An item in the DynGridView has been clicked
    	 * @param v the view of this item
    	 * @param position the position in the adapter array
    	 * @param id the item id
    	 */
        public void onItemClick(View v, int position, int id);
        /**
    	 * TheFAV icon on top right corner of an item in the DynGridView has been clicked
    	 * @param v the view of this item
    	 * @param position the position in the adapter array
    	 * @param id the item id
    	 */
        public void onItemFavClick(View v, int positionForView, int id);
        /**
         * Dragging has started
         */
        public void onDragStart();
        /**
         * Dragging has stopped	
         */
        public void onDragStop();
        /**
         * Two items have swapped their positions by drag and drop
         * @param positionOne the first item
         * @param positionTwo the second item
         */
        public void onItemsChanged(int positionOne, int positionTwo);
        /**
         * This item has been deleted, by dragging over the delete zone
         * @param position the position in the adapter array
         * @param id the item id
         */
        public void onItemDeleted(int position, int id);
        
		public void onSwipeLeft();
		
		public void onSwipeRight();
		
		public void onSwipeUp();
		
		public void onSwipeDown();
        
    }
   
    /**
     * Sets the listener which will be notified for drag events and clicks on items in the gridview
     */
    public void setDynGridViewListener(DynGridViewListener l) {
        mListener = l;
    }
    
    
    /**
     */
    // Other Methods
	// --------------------------------------------------------------------------- //

    /**
     * Show a string on the screen via Toast.
     * 
     * @param msg String
     * @return void
     */

    public void toast (String msg)
    {
        // if (!DragActivity.Debugging) return;
        Toast.makeText (getContext (), msg, Toast.LENGTH_SHORT).show ();
    } // end toast
    
	/**
	 * Start dragging a view.
	 *
	 */    
	public boolean startDrag (View v)
	{
	    DragSource dragSource = (DragSource) v;
	    // We are starting a drag. Let the DragController handle it.
	    mDragController.startDrag (v, dragSource, dragSource, DragController.DRAG_ACTION_MOVE);

	    return true;
	}
	
    /**
	 */
	// The following callbacks are in place to intercept all user input and react accordingly
    // --------------------------------------------------------------------------- //
    
    /**
     * 
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
		if (mDragController == null || !mDragging ) {
			return super.dispatchKeyEvent(event);
		}
		// controller to handle event
		return mDragController.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
		
    }
    /**
     * 
     */
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mDragController == null || !mDragging) {
			if (mDragController!=null) mDragController.onInterceptTouchEvent(ev);
			if (mSwipeEnabled) {
				gestureDetector.onTouchEvent(ev);
				return false; //to make sure we receive further messages
			}
			else
				return super.onInterceptTouchEvent(ev); //for scroll, etc
		}
		else
		// controller to handle event
    	return mDragController.onInterceptTouchEvent(ev);
    }

	/**
	 * 
	 */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	if (mDragController == null || !mDragging) {
    		//if (!gestureDetector.onTouchEvent(ev))
    			return super.onTouchEvent(ev);
    	}
    	// controller to handle event
        return mDragController.onTouchEvent(ev);
    }

    /**
     * 
     */
    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
    	if (mDragController == null || !mDragging) {
    		return super.dispatchUnhandledMove(focused, direction);	
    	}
    	// controller to handle event
    	return mDragController.dispatchUnhandledMove(focused, direction);
    }

    /**
	 */
	// The following are the implementation for DragController.DragListener
    // --------------------------------------------------------------------------- //
    
    
    /**
     * A drag has begun.
     * 
     * @param source An object representing where the drag originated
     * @param info The data associated with the object that is being dragged
     * @param dragAction The drag action: either {@link DragController#DRAG_ACTION_MOVE}
     *        or {@link DragController#DRAG_ACTION_COPY}
     */
    public void onDragStart(DragSource source, Object info, int dragAction) 
    {
    	if (mDragController == null ) return;
    	
    	// We are starting a drag.
    
    	// Mark our internal flag
    	mDragging = true;

    	// Build up a list of DropTargets from the child views of the GridView.
    	// Tell the drag controller about them.
        
    	int numVisibleChildren = getChildCount();
    	for ( int i = 0; i < numVisibleChildren; i++ ) {
    		DropTarget view = (DropTarget) getChildAt (i);
    		mDragController.addDropTarget (view);
    	}
        
    	// Always add the delete_zone so there is a place to get rid of views.
    	// Find the delete_zone and add it as a drop target.
    	// That gives the user a place to drag views to get them off the screen.
    	// View v = findViewById (R.id.delete_zone_view);
    	if (mDeleteView != null) {
    		mDeleteView.setVisibility(View.VISIBLE);
    		mDragController.addDropTarget (mDeleteView);
    	}
    	
    	// inform listener
        if (mListener!=null) mListener.onDragStart();
    }

    /**
     * A drag-drop operation has ended.
     * sent with OnDropCompleted 
     */
    public void onDragEnd() 
    {
    	if (mDragController == null) return;
    	mDragging = false;
        mDragController.removeAllDropTargets ();
        if (mDeleteView != null) {
            mDeleteView.setVisibility(View.INVISIBLE);
        }
        // inform listener
        if (mListener!=null) mListener.onDragStop();
    }

    
    /**
     * A drag-drop operation has ended, and we get the source and the target of this operation
     * Sent with onDragEnd
     */
	public void onDropCompleted(View source, View target) {
		// TODO: better checking mechanism, this will fail if DeleteZone has a valid ID!
		if (target.getId() != -1) {
			int positionOne = getPositionForView(source), positionTwo = getPositionForView(target);
			// inform listener
	        if (mListener!=null) mListener.onItemsChanged(positionOne, positionTwo);
			((DynGridViewAdapter) getAdapter()).swapItems(positionOne, positionTwo);
		}
		else {
			int position = getPositionForView(source);
			// inform listener
	        if (mListener!=null) mListener.onItemDeleted(position, source.getId());
			((DynGridViewAdapter) getAdapter()).remove(position);	
		} 
	}
	
	/**
	 */
	// The following are the implementation for item events
    // --------------------------------------------------------------------------- //
	/**
	 * Long click on gridview item
	 */
	public boolean onLongClick(View v) {
		if (mDragController!= null && mLongClickStartsDrag) {
	        // Make sure the drag was started by a long press as opposed to a long click.
	        // (Note: I got this from the Workspace object in the Android Launcher code. 
	        //  I think it is here to ensure that the device is still in touch mode as we start the drag operation.)
	        if (!v.isInTouchMode()) {
	           Log.e("XX", "isInTouchMode returned false. Try touching the view again.");
	           return false;
	        }
	        return startDrag (v);
	    }

	    // If we get here, return false to indicate that we have not taken care of the event.
	    return false;
	}
	
	
	/**
	 * This is the starting point for a drag operation if mLongClickStartsDrag is false.
	 * It looks for the down event that gets generated when a user touches the screen.
	 * Only that initiates the drag-drop sequence.
	 *
	 */    
	public boolean onTouch (View v, MotionEvent ev) 
	{
		final int action = ev.getAction();
		
	    // If we are configured to start only on a long click, we are not going to handle any events here.
	    if (mLongClickStartsDrag) {
	    	/*if (action == MotionEvent.ACTION_DOWN) {
		    	if (mListener!=null) 
					mListener.
					onItemClick(v, 
							getPositionForView(v), 
							v.getId());
	    	}*/
	    	return false;
	    }

	    boolean handledHere = false;
	    // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
	    if (mDragController != null && action == MotionEvent.ACTION_DOWN) {
	       handledHere = startDrag (v);
	    }	    
	    return handledHere;
	}

	public void onClick(View v) {
		if (mListener!=null) { 
			if (v.getId() == DynGridViewItemView.FAVICONID)
				mListener.
				onItemFavClick(v, 
						getPositionForView(v), 
						v.getId());
			else {
				if (v!=null) mListener.
				onItemClick(v, 
					getPositionForView(v), 
					v.getId());
			}
		}
	}  
}
