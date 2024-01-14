package droidlymobile.ca.thelegendadventure.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import droidlymobile.ca.thelegendadventure.GameView;



public class YOUWINPLAYAGAIN {

    public GameView gameView;
    public Paint paint = new Paint();
    public Paint textpaint = new Paint();
    public String youdead = "";


    public YOUWINPLAYAGAIN(GameView gameView){
        this.gameView = gameView;
        paint.setColor(Color.BLACK);
        textpaint.setColor(Color.RED);
        textpaint.setTextSize(100);
        youdead = "YOU WIN PLAY AGAIN";
    }

    public void draw(Canvas canvas){
        canvas.drawRect(0,0,gameView.getDisplayWidth()+1000,gameView.getDisplayHeight(),paint);
        canvas.drawText(youdead,youdead.length()/2 * textpaint.getTextSize(),
                textpaint.getTextSize()*2,textpaint);
    }
}