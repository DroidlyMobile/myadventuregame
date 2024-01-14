package droidlymobile.ca.thelegendadventure.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import droidlymobile.ca.thelegendadventure.GameView;
import droidlymobile.ca.thelegendadventure.R;

public class EnemySetup extends EntityInfo{

    public EnemySetup(GameView gameView){
        this.gameView = gameView;
        entityWidth = gameView.defaultTilesize;
        entityHeight = gameView.defaultTilesize;
        initializeSpriteSheet();
    }
    public void initializeSpriteSheet(){
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(gameView.getResources(),
                R.drawable.enemiesspritesheet,
                bitmapOptions);
        int maxColumns = spritesheet1.getWidth()/16;
        int maxRows = spritesheet1.getHeight()/16;
        while (currentRow<maxRows){
            entitySprites[numberOftiles] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                            currentColumn * 16,
                            currentRow * 16,
                            16,
                            16),entityWidth,
                    entityHeight,false);
            currentColumn ++;
            if (currentColumn == maxColumns){
                currentColumn = 0;
                currentRow ++;
            }
            numberOftiles ++;
        }
    }
}
