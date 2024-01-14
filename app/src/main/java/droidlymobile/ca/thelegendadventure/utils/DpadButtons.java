package droidlymobile.ca.thelegendadventure.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DpadButtons {

    public int dpadXstart,dpadXend,dpadYstart,dpadYend = 0;
    public Paint buttonPaint = new Paint();
    private boolean isPressed = false;

    public DpadButtons(int xstart,int xend,int ystart,int yend){
        this.dpadXstart = xstart;
        this.dpadXend = xend;
        this.dpadYstart = ystart;
        this.dpadYend = yend;
        buttonPaint.setColor(Color.GREEN);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawRect(dpadXstart,dpadYstart,dpadXend,dpadYend,buttonPaint);
    }
    public boolean isPressed(float touchPositionX, double touchPositionY) {
        if (touchPositionX > dpadXstart && touchPositionX < dpadXend){
            isPressed = true;
        }else {
            isPressed = false;
        }
        return isPressed;
    }
    public boolean getIsPressed() {
        return isPressed;
    }
    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
}
