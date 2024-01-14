package droidlymobile.ca.thelegendadventure.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import droidlymobile.ca.thelegendadventure.GameView;

public class ControllerButtons {

    public int startX,endX,startY,endY = 0;

    public Paint buttonpaint = new Paint();
    public GameView gameView;
    public Bitmap buttonImage = null;

    public ControllerButtons(GameView gameView,int startX,int startY){
        this.gameView = gameView;
        this.startX = startX;
        this.startY = startY;
        buttonpaint.setColor(Color.GREEN);
    }
    public void draw(Canvas canvas){
        //canvas.drawRect(startX,startY,startX + gameView.buttonWidth,startY + gameView.buttonHeight,buttonpaint);
        if (buttonImage != null){
            canvas.drawBitmap(buttonImage,startX,startY,buttonpaint);
        }
    }
    public boolean getXY(int x, int y){
        boolean checkxy = false;
        if (x > startX && x < startX + gameView.buttonWidth && y > startY && y < startY + gameView.buttonHeight){
            checkxy = true;
        }
        return checkxy;
    }
}
