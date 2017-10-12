package com.lucabl.gridviewpagersample;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Luca on 10/10/2017.
 */

public class PageView extends View {

    int color;
    float initialX, initialY;
    Long initialTouchTime = null;
    final long MAX_TOUCH_TIME = 500;
    final int TOLERANCE = 50;
    private Context context;

    public PageView(int color, Context context) {
        super(context);
        this.context = context;
        this.color = color;
        this.setClickable(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            initialX = event.getX();
            initialY = event.getY();
            initialTouchTime = System.currentTimeMillis();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(initialTouchTime!=null && initialTouchTime.longValue()>=System.currentTimeMillis()-MAX_TOUCH_TIME) {
                initialTouchTime = null;
                boolean sameX = initialX + TOLERANCE > event.getX() && initialX - TOLERANCE < event.getX();
                boolean sameY = initialY + TOLERANCE > event.getY() && initialY - TOLERANCE < event.getY();
                if(sameX && sameY){
                    Toast.makeText(context, "Touched at " + initialX + " " + initialY, Toast.LENGTH_SHORT).show();
                }
            }
        }

        return super.onTouchEvent(event);

    }
}
