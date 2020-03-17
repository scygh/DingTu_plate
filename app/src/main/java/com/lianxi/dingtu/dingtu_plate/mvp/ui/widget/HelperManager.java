package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;


public class HelperManager {

	private HelperManager(){
		
	}

	public static AnimationsHelper getAnimationsHelper(){
		return AnimationsHelper.getSington();
	}

}