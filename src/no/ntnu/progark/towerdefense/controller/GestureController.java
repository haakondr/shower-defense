package no.ntnu.progark.towerdefense.controller;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.GestureDetector.OnGestureListener;

public class GestureController extends ScaleGestureDetector.SimpleOnScaleGestureListener implements OnGestureListener{
	
	private TowerDefenseState state;
	private GestureDetector gestureDetector;
	

	public GestureController(TowerDefenseState state) {
		this.state = state;
		gestureDetector = new GestureDetector(this);
		gestureDetector.setIsLongpressEnabled(true);
	}
	
	public void setLongPressEnabled(boolean enabled) {
		gestureDetector.setIsLongpressEnabled(enabled);
	}
	


	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		state.multiplyScaleFactor(detector.getScaleFactor());
		return true;
	}
	
	public void detectGestures(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		state.showDialog(0);
		state.setNewTowerLocationX(e.getX());
		state.setNewTowerLocationY(e.getY());
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}








}
