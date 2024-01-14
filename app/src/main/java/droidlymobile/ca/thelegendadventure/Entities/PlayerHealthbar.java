package droidlymobile.ca.thelegendadventure.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import droidlymobile.ca.thelegendadventure.GameView;

public class PlayerHealthbar{

    public GameView gameView;
    public Bitmap defaultImage,defaultImage2,defaultImage3 = null;
    public Bitmap h1,h2,h3,h4,h5 = null;
    public int screenX,screenY = 0;
    public int width,height = 0;

    public PlayerHealthbar(GameView gameView){
        this.gameView = gameView;
        initializeHealthbar();
    }

    private void initializeHealthbar() {
        screenX = gameView.defaultTilesize/2;
        screenY = gameView.defaultTilesize/2;
        width = gameView.defaultTilesize/2;
        height = gameView.defaultTilesize/2;
        h1 = Bitmap.createScaledBitmap(gameView.player.entitySprites[24],width,height,false);
        h2 = Bitmap.createScaledBitmap(gameView.player.entitySprites[25],width,height,false);
        h3 = Bitmap.createScaledBitmap(gameView.player.entitySprites[26],width,height,false);
        h4 = Bitmap.createScaledBitmap(gameView.player.entitySprites[27],width,height,false);
        h5 = Bitmap.createScaledBitmap(gameView.player.entitySprites[28],width,height,false);


    }
    public void update(){
        if (gameView.player.playerHealth == 6) {
            defaultImage = h1;
            defaultImage2 = h1;
            defaultImage3 = h1;
        }
        if (gameView.player.playerHealth == 5) {
            defaultImage = h1;
            defaultImage2 = h1;
            defaultImage3 = h3;
        }
        if (gameView.player.playerHealth == 4) {
            defaultImage = h1;
            defaultImage2 = h1;
            defaultImage3 = h5;
        }
        if (gameView.player.playerHealth == 3) {
            defaultImage = h1;
            defaultImage2 = h3;
            defaultImage3 = h5;
        }
        if (gameView.player.playerHealth == 2) {
            defaultImage = h1;
            defaultImage2 = h5;
            defaultImage3 = h5;
        }
        if (gameView.player.playerHealth == 1) {
            defaultImage = h3;
            defaultImage2 = h5;
            defaultImage3 = h5;
        }
        if (gameView.player.playerHealth == 0) {
            defaultImage = h5;
            defaultImage2 = h5;
            defaultImage3 = h5;
            //YOU DEAD
        }

    }
    public void draw(Canvas canvas){
        if (defaultImage!=null){
            canvas.drawBitmap(defaultImage,screenX,screenY,null);
        }
        if (defaultImage2!=null) {
            canvas.drawBitmap(defaultImage2, (screenX * 2)+10, screenY, null);
        }
        if (defaultImage3!=null) {
            canvas.drawBitmap(defaultImage3, (screenX * 3)+20, screenY, null);
        }
    }
}
