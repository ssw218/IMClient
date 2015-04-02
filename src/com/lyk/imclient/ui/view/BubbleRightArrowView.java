package com.lyk.imclient.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BubbleRightArrowView extends View{
	public BubbleRightArrowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		path.moveTo(10, 0);
		path.lineTo(0, 0);
		path.lineTo(0, 10);
		path.close();
		canvas.drawPath(path, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(10, 10);
	}
	
}