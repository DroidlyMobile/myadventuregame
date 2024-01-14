package droidlymobile.ca.thelegendadventure.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Collections;

import droidlymobile.ca.thelegendadventure.GameView;
import droidlymobile.ca.thelegendadventure.R;

public class Objects extends ObjectInfo {

    public Objects(GameView gameView){
        this.gameView = gameView;
        initializeObject();
    }

    private void initializeObject() {
        objWidth = gameView.defaultTilesize;
        objHeight = gameView.defaultTilesize;
        hitbox.x = 0;
        hitbox.width = objWidth;
        hitbox.y = 0;
        hitbox.height = objHeight;
        defaultLeft =  hitbox.x;
        defaultTop = hitbox.y;
        hitboxpaint.setColor(Color.RED);
        hitboxpaintbg.setColor(Color.BLACK);
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(50);
        healthBarWidth = objWidth-20;
        maxHealthBarWidth = healthBarWidth;
    }
    public void update(){
        checkHealthSetHealthbar();
        checkTileIDSetImage();
        checkBeingAttacked();
    }
    public void draw(Canvas canvas){
        objScreenX = (objPosX - gameView.player.posX) + gameView.player.screenPosX;
        objScreenY = (objPosY - gameView.player.posY) + gameView.player.screenPosY;
        if (objDefaultImage!=null) {
            if (objScreenX>-gameView.defaultTilesize
                    && objScreenX + hitbox.x + hitbox.width < (gameView.getDisplayWidth() + (gameView.defaultTilesize*3))
                    && objScreenY > -gameView.defaultTilesize
                    && objScreenY + objHeight < (gameView.getDisplayHeight() + gameView.defaultTilesize)) {
                //Draw Healthbar
                if (beingAttacked) {
                    canvas.drawRect((objScreenX + hitbox.x), (objScreenY + hitbox.y) - 26, (objScreenX + maxHealthBarWidth), objScreenY, hitboxpaintbg);
                    canvas.drawRect((objScreenX + hitbox.x), (objScreenY + hitbox.y) - 26, (objScreenX + healthBarWidth), objScreenY, hitboxpaint);
                }
                canvas.drawBitmap(objDefaultImage, objScreenX, objScreenY, null);
                if (mineAble){
                    canvas.drawBitmap(objHighlight, objScreenX, objScreenY, null);
                }
                //canvas.drawText(String.valueOf(objHealth),objScreenX,objScreenY,textpaint);
            }
        }
    }
    public void checkTileIDSetImage(){
        //objDefaultImage = gameView.tileManager.tileImgs[tileID];

        objHighlight = gameView.tileManager.tileImgs[27];
    }
    public void checkHealthSetHealthbar(){
        healthBarWidth = (int)(((double)objHealth/(double)objMaxHealth) * maxHealthBarWidth);
    }
    public void checkBeingAttacked(){
        if (!beingAttacked){
            attackedTimer++;
            if (attackedTimer>200) {
                showHealthBar = false;
                attackedTimer=0;
            }
        }
    }
}
