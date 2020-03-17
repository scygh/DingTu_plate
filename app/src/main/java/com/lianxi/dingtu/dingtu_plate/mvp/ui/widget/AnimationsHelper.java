package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


public class AnimationsHelper {

	private static AnimationsHelper mAnimationsHelper;
	private AnimationsHelper(){
		
	}

	public static AnimationsHelper getSington(){
		if(mAnimationsHelper==null){
			mAnimationsHelper=new AnimationsHelper();
		}
		return mAnimationsHelper;
	}

	public AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha){
		AlphaAnimation alpha=new AlphaAnimation(fromAlpha, toAlpha);
		return alpha;
	}
	

	public ScaleAnimation getScaleAnimation(float fromX, float toX, float fromY, float toY,
                                            int pivotXType, float pivotXValue, int pivotYType, float pivotYValue){
		ScaleAnimation scale=new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
		return scale;
	}

	public RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,
                                              int pivotYType, float pivotYValue){
		RotateAnimation rotate=new RotateAnimation(fromDegrees,toDegrees,pivotXType,pivotXValue,pivotYType,pivotYValue);
		return rotate;
	}

	public TranslateAnimation getTranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue,
                                                    int fromYType, float fromYValue, int toYType, float toYValue){
		TranslateAnimation translate=new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
		return translate;
	}

	public ObjectAnimator getObjectAnimatorFloat(Object target, String propertyName, float... values) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(target, propertyName, values);
		return anim;
	}

	public ValueAnimator getValueAnimatorFloat(Object target, float... values) {
		ValueAnimator anim = ValueAnimator.ofFloat(values);
		anim.setTarget(target); 
		return anim;
	}

	public ValueAnimator getValueAnimatorInt(Object target, int... values) {
		ValueAnimator anim = ValueAnimator.ofInt(values);
		anim.setTarget(target); 
		return anim;
	}
}