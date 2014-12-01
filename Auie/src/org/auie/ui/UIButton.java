package org.auie.ui;

import org.auie.image.UEImage;
import org.auie.utils.UEDevice;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RemoteViews.RemoteView;

/**
 * 
 * 按钮类
 * 
 * @author Soniy7x
 *
 */
@SuppressLint("ClickableViewAccessibility")
@RemoteView
@TargetApi(Build.VERSION_CODES.L)
public class UIButton extends Button {

	private static final int DEFAULT_BACKGROUNDCOLOR = Color.parseColor("#D8D8D8");

	private int backgroundColor = DEFAULT_BACKGROUNDCOLOR;
	private Bitmap bitmap;
	private float radius = 5;
	private float distanceX = 0;
	private float distanceY = 0;
	private Paint mPaint = new Paint();

	/**
	 * 构造方法
	 */
	public UIButton(Context context) {
		super(context);
		init();
	}

	/**
	 * 构造方法
	 */
	public UIButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 构造方法
	 */
	public UIButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/**
	 * 构造方法
	 */
	public UIButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		if (UEDevice.getOSVersionCode() >= 11) {
			try {
				setBackgroundColor(((ColorDrawable) getBackground()).getColor());
			} catch (Exception e) {
				setBackgroundColor(backgroundColor);
			}
		} else {
			setBackgroundColor(backgroundColor);
		}
		setGravity(Gravity.CENTER);
	}

	/**
	 * 设置背景颜色
	 * @param backgroundColor
	 */
	@SuppressWarnings("deprecation")
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		if (UEDevice.getOSVersionCode() >= 16) {
			super.setBackground(UEImage.createBackground(backgroundColor, 255, radius));
		} else {
			super.setBackgroundDrawable(UEImage.createBackground(backgroundColor, 255, radius));
		}
	}

	/**
	 * 获得圆角大小
	 * @return 圆角大小
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * 设置圆角大小
	 * @param radius 圆角大小
	 */
	public void setRadius(float radius) {
		this.radius = radius;
		setBackgroundColor(backgroundColor);
	}
	
	/**
	 * 设置图片资源
	 * @param resId 资源ID
	 */
	public void setImageResource(int resId){
		setImage(new UEImage(getResources(), resId).toBitmap());
	}
	
	/**
	 * 设置图片资源
	 * @param bitmap 图片
	 */
	public void setImage(Bitmap bitmap){
		this.bitmap = bitmap;
		this.setText("");
		invalidate();
	}
	
	/**
	 * 设置文字内容
	 * @param text 内容
	 */
	public void setText(String text){
		if (bitmap == null) {
			super.setText(text);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setAlpha(0.6f);
			super.onTouchEvent(event);
			return true;
		case MotionEvent.ACTION_UP:
			setAlpha(1f);
			return super.onTouchEvent(event);
		default:
			setAlpha(0.6f);
			return super.onTouchEvent(event);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (bitmap != null) {
			distanceX = (float) ((getWidth() - bitmap.getWidth())/2.0);
			distanceY = (float) ((getHeight() - bitmap.getHeight())/2.0);
			canvas.drawBitmap(bitmap, distanceX, distanceY, mPaint);
		}
	}
	
}
