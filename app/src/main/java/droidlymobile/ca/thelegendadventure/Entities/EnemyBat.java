package droidlymobile.ca.thelegendadventure.Entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import droidlymobile.ca.thelegendadventure.GameView;

public class EnemyBat extends EntityInfo{

    public EnemyBat(GameView gameView){
        this.gameView = gameView;
        entityAnimMaxCount = 6;
        hitbox.x = 0;
        hitbox.width = gameView.defaultTilesize;
        hitbox.y = 0;
        hitbox.height = gameView.defaultTilesize;
        defaultLeft = hitbox.x;
        defaultTop =  hitbox.y;
        speed = 2;
        entityDirection = "idle";
        playerHealth = 5;
        playerMaxHealth = 5;
        maxAttackTimer = 100;
        randomDirectionCounter = 1000;
        healthBarWidth = gameView.defaultTilesize;
        maxHealthBarWidth = healthBarWidth;
        hitboxpaint = new Paint();
        hitboxpaint.setColor(Color.RED);
        hitboxpaint.setTextSize(50);
        collidable = false;
    }

    public void update(){
        checkHealthSetHealthbar();
        updateAnimation();
        if (!beingattacked || !entityDead) {
            updateDirection();
        }
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
                entityAnimNum = 5;
            }else if (entityAnimNum == 5) {
                entityAnimNum = 6;
            }else if (entityAnimNum == 6) {
                entityAnimNum = 1;
            }
            entityAnimCounter = 0;
        }
        if (entityAnimNum == 1) {
            defaultEntityImg = gameView.enemySetup.entitySprites[5];
        }
        if (entityAnimNum == 2) {
            defaultEntityImg = gameView.enemySetup.entitySprites[6];
        }
        if (entityAnimNum == 3) {
            defaultEntityImg = gameView.enemySetup.entitySprites[7];
        }
        if (entityAnimNum == 4) {
            defaultEntityImg = gameView.enemySetup.entitySprites[8];
        }
        if (entityAnimNum == 5) {
            defaultEntityImg = gameView.enemySetup.entitySprites[9];
        }
    }

    public void updateDirection(){
        collision = false;
        gameView.collisionChecker.checkTileCollision(this,entityDirection);
        gameView.collisionChecker.checkPlayerCollision(this,entityDirection);
        pickRandomDirection();
        switch (entityDirection) {
            case "right":
                if (!collision) {
                    posX += speed;
                }
                break;
            case "left":
                if (!collision) {
                    posX -= speed;
                }
                break;
            case "down":
                if (!collision) {
                    posY += speed;
                }
                break;
            case "up":
                if (!collision) {
                    posY -= speed;
                }
                break;
            case "idle":
                break;
        }

    }
    public void pickRandomDirection(){
        randomDirectionCounter++;
        if (randomDirectionCounter > 100){
            int random = gameView.getRandom(0,150);
            randomDirectionCounter = 0;
            if (random > 0 && random < 26){
                entityDirection = "right";
            }
            if (random > 25 && random < 51){
                entityDirection = "right";
            }
            if (random > 50 && random < 76){
                entityDirection = "idle";
            }
            if (random > 75 && random < 101){
                entityDirection = "left";
            }
            if (random > 100 && random < 126){
                entityDirection = "up";
            }
            if (random > 125 && random < 151){
                entityDirection = "down";
            }
        }
    }
    public void checkBeingAttacked(){
        if (beingattacked){
            spritePaint.setAlpha(100);
            if (playerHealth > 0) {
                attackTimer++;
                if (attackTimer > maxAttackTimer) {
                    attackTimer = 0;
                    beingattacked = false;
                    spritePaint.setAlpha(255);

                }
            }else {
                entityDead = true;
            }
        }
    }
    public void checkHealthSetHealthbar(){
        healthBarWidth = (int)(((double)playerHealth/(double)playerMaxHealth) * maxHealthBarWidth);
    }
}
