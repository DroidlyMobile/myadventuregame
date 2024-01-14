package droidlymobile.ca.thelegendadventure.Entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import droidlymobile.ca.thelegendadventure.GameView;

public class EnemyBomber extends EntityInfo{

    public int explosiontimer = 0;
    public boolean exploding = false;
    public boolean exploded = false;

    public EnemyBomber(GameView gameView){
        this.gameView = gameView;
        entityAnimMaxCount = 10;
        hitboxpaint = new Paint();
        hitboxpaint.setColor(Color.RED);
        hitboxpaint.setTextSize(50);
        healthBarWidth = gameView.defaultTilesize;
        maxHealthBarWidth = healthBarWidth;
        hitbox.x = 0;
        hitbox.width = gameView.defaultTilesize;
        hitbox.y = 0;
        hitbox.height = gameView.defaultTilesize;
        defaultLeft = hitbox.x;
        defaultTop =  hitbox.y;
        playerHealth = 5;
        playerMaxHealth = 5;
        maxAttackTimer = 150;
        entityDirection = "left";
        speed = 10;
    }

    public void update(){
        checkExploding();
        checkHealthSetHealthbar();
        updateAnimation();
        checkBeingAttacked();
    }

    public void draw(Canvas canvas){
        screenPosX = posX + gameView.player.screenPosX - gameView.player.posX;
        screenPosY = posY + gameView.player.screenPosY - gameView.player.posY;

        if (screenPosX>-gameView.defaultTilesize
                && screenPosX + hitbox.x + hitbox.width <
                (gameView.getDisplayWidth() + (gameView.defaultTilesize*3))
                && screenPosY > -gameView.defaultTilesize
                && screenPosY + hitbox.height <
                (gameView.getDisplayHeight() + gameView.defaultTilesize)) {

            if (defaultEntityImg != null) {
                canvas.drawBitmap(defaultEntityImg, screenPosX, screenPosY, spritePaint);
            }
            if (playerHealth < playerMaxHealth) {
                canvas.drawRect(screenPosX + hitbox.x, screenPosY + hitbox.y, screenPosX + maxHealthBarWidth, screenPosY - 25, hitboxpaintbg);
                canvas.drawRect(screenPosX + hitbox.x, screenPosY + hitbox.y, screenPosX + healthBarWidth, screenPosY - 25, hitboxpaint);
            }
        }
        //canvas.drawText(String.valueOf(playerCloseToEnemy),screenPosX,screenPosY,hitboxpaint);
    }

    public void updateAnimation(){

            entityAnimCounter++;
            if (entityAnimCounter > entityAnimMaxCount) {
                if (entityAnimNum == 1) {
                    entityAnimNum = 2;
                } else if (entityAnimNum == 2) {
                    entityAnimNum = 3;
                } else if (entityAnimNum == 3) {
                    entityAnimNum = 4;
                } else if (entityAnimNum == 4) {
                    entityAnimNum = 1;
                }
                entityAnimCounter = 0;
            }
        if (!exploding) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntityImg = gameView.enemySetup.entitySprites[0];
            }
            if (entityAnimNum == 2) {
                defaultEntityImg = gameView.enemySetup.entitySprites[1];
            }
            if (entityAnimNum == 4) {
                defaultEntityImg = gameView.enemySetup.entitySprites[2];
            }
        }
        if (exploding){
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntityImg = gameView.enemySetup.entitySprites[0];
            }
            if (entityAnimNum == 2) {
                defaultEntityImg = gameView.enemySetup.entitySprites[3];
            }
            if (entityAnimNum == 4) {
                defaultEntityImg = gameView.enemySetup.entitySprites[3];
            }
        }

        if (exploded){
            defaultEntityImg = gameView.enemySetup.entitySprites[4];
            deathCounter++;
            if (deathCounter > 15) {
                if (playerCloseToEnemy) {
                    gameView.player.playerHealth--;
                    playerCloseToEnemy = false;
                }
                    playerHealth = -1;


            }
        }
    }
    public void checkHealthSetHealthbar(){
        healthBarWidth = (int)(((double)playerHealth/(double)playerMaxHealth) * maxHealthBarWidth);
    }
    public void checkBeingAttacked(){
        if (beingattacked){
            spritePaint.setAlpha(100);
            attackTimer ++;
            if (attackTimer > maxAttackTimer){
                attackTimer = 0;
                beingattacked = false;
                spritePaint.setAlpha(255);

            }
        }
    }
    public void checkExploding(){
        //If bomber is dead it will explode
        //Two checks happen here if the bomber is completely dead and the player is close the player takes damage
        //If player is not close the bomber still explodes
        if (exploding){
            explosiontimer++;
            if (explosiontimer > 75){//Change explosion timer maybe based of difficulty level
                //EXPLODE or ATTACK player
                playerHealth = 0;
                exploded = true;
            }
        }else {
            explosiontimer = 0;
        }

    }

}