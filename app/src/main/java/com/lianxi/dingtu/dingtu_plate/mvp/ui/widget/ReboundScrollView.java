package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


public class ReboundScrollView extends ScrollView {

	@SuppressWarnings("unused")
	private static final String TAG = "ReboundScrollView";


	private static final float MOVE_FACTOR = 0.6f;


	private static final int ANIM_TIME = 400;

	private View contentView;

	private float startY;

	private Rect originalRect = new Rect();

	private boolean isMoved = false;

	public ReboundScrollView(Context context) {
		super(context);
	}

	public ReboundScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReboundScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			contentView = getChildAt(0);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (contentView == null)
			return;

		originalRect.set(contentView.getLeft(), contentView.getTop(), contentView.getRight(), contentView.getBottom());
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (contentView == null) { return super.dispatchTouchEvent(ev); }
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;

		case MotionEvent.ACTION_UP:
			if (!isMoved)
				break;


			TranslateAnimation anim = HelperManager.getAnimationsHelper().getTranslateAnimation(Animation.ABSOLUTE, 0,
					Animation.ABSOLUTE, 0, Animation.ABSOLUTE, contentView.getTop(), Animation.ABSOLUTE,
					originalRect.top);
			anim.setDuration(ANIM_TIME);
			anim.setInterpolator(new DecelerateInterpolator());
			contentView.startAnimation(anim);

			contentView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);

			isMoved = false;
			break;
		case MotionEvent.ACTION_MOVE:

			if (!isCanPullDown() && !isCanPullUp()) {
				startY = ev.getY();
				break;
			}


			float nowY = ev.getY();
			int deltaY = (int) (nowY - startY);


			boolean shouldMove = (isCanPullDown() && deltaY > 0)
					|| (isCanPullUp() && deltaY < 0)
					|| (isCanPullDown() && isCanPullUp());

			if (shouldMove) {

				int offset = (int) (deltaY * MOVE_FACTOR);


				contentView.layout(originalRect.left, originalRect.top + offset, originalRect.right,
						originalRect.bottom + offset);
				isMoved = true;
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean isCanPullDown() {
		return getScrollY() == 0 || contentView.getHeight() <= getHeight() + getScrollY();
	}


	private boolean isCanPullUp() {
		return contentView.getHeight() <= getHeight() + getScrollY();
	}
}