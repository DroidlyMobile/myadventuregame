package droidlymobile.ca.thelegendadventure.utils;

import android.graphics.RectF;

import droidlymobile.ca.thelegendadventure.Entities.EntityInfo;
import droidlymobile.ca.thelegendadventure.GameView;
public class CollisionChecker extends EntityInfo {
    public int checkPlayerLeftSide = 0;
    public int checkPlayerRightSide = 0;
    public int setPlayerLeftX;
    public int setPlayerRightX;
    public int checkPlayerTopSide;
    public int checkPlayerBottomSide;
    public int setPlayerTopY;
    public int setPlayerBottomY;

    public CollisionChecker(GameView gameView) {
        this.gameView = gameView;
    }

    public void checkTileCollision(EntityInfo entity, final String entityDirection) {
        //Check Right & Left
        checkPlayerLeftSide = (int) (entity.posX + entity.hitbox.x);
        checkPlayerRightSide = (int) (entity.posX + entity.hitbox.x + entity.hitbox.width);
        setPlayerLeftX = checkPlayerLeftSide / gameView.defaultTilesize;
        setPlayerRightX = checkPlayerRightSide / gameView.defaultTilesize;
        //Check Up & Down
        checkPlayerTopSide = (int) (entity.posY + entity.hitbox.y);
        checkPlayerBottomSide = (int) (entity.posY + entity.hitbox.y + entity.hitbox.height);
        setPlayerTopY = checkPlayerTopSide / gameView.defaultTilesize;
        setPlayerBottomY = checkPlayerBottomSide / gameView.defaultTilesize;//To round the current Y value we can -1 so that when the player is walking left or right they don't collide
        //When we go left or right we want to check the top tile and bottom tile position so that the player cannot walk through the tile halfway

        switch (entityDirection) {
            case "left":
                setPlayerLeftX = (checkPlayerLeftSide - entity.speed) / gameView.defaultTilesize;
                setPlayerTopY = (checkPlayerTopSide + entity.speed) / gameView.defaultTilesize;
                setPlayerBottomY = (checkPlayerBottomSide - entity.speed) / gameView.defaultTilesize;
                checkTile1 = gameView.tileManager.worldTileNumLayer1[setPlayerLeftX][setPlayerTopY];
                checkTile2 = gameView.tileManager.worldTileNumLayer1[setPlayerLeftX][setPlayerBottomY];
                checkTile1Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerLeftX][setPlayerTopY];
                checkTile2Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerLeftX][setPlayerBottomY];
                if (gameView.tileManager.tileInfoLayer1[checkTile1].tileCollision
                        || gameView.tileManager.tileInfoLayer1[checkTile2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile1Layer2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile2Layer2].tileCollision) {
                    entity.collision = true;
                }
                break;
            case "right":
                setPlayerRightX = (checkPlayerRightSide + entity.speed) / gameView.defaultTilesize;
                setPlayerBottomY = (checkPlayerBottomSide - entity.speed) / gameView.defaultTilesize;
                setPlayerTopY = (checkPlayerTopSide + entity.speed) / gameView.defaultTilesize;
                checkTile1 = gameView.tileManager.worldTileNumLayer1[setPlayerRightX][setPlayerTopY];
                checkTile2 = gameView.tileManager.worldTileNumLayer1[setPlayerRightX][setPlayerBottomY];
                checkTile1Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerRightX][setPlayerTopY];
                checkTile2Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerRightX][setPlayerBottomY];
                if (gameView.tileManager.tileInfoLayer1[checkTile1].tileCollision
                        || gameView.tileManager.tileInfoLayer1[checkTile2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile1Layer2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile2Layer2].tileCollision) {
                    entity.collision = true;
                }
                break;
            case "up":
                setPlayerTopY = (checkPlayerTopSide - entity.speed) / gameView.defaultTilesize;
                setPlayerRightX = (checkPlayerRightSide - entity.speed) / gameView.defaultTilesize;
                setPlayerLeftX = (checkPlayerLeftSide + entity.speed) / gameView.defaultTilesize;
                checkTile1 = gameView.tileManager.worldTileNumLayer1[setPlayerLeftX][setPlayerTopY];
                checkTile2 = gameView.tileManager.worldTileNumLayer1[setPlayerRightX][setPlayerTopY];
                checkTile1Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerLeftX][setPlayerTopY];
                checkTile2Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerRightX][setPlayerTopY];
                if (gameView.tileManager.tileInfoLayer1[checkTile1].tileCollision
                        || gameView.tileManager.tileInfoLayer1[checkTile2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile1Layer2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile2Layer2].tileCollision) {
                    entity.collision = true;
                }
                break;
            case "down":
                setPlayerBottomY = (checkPlayerBottomSide + entity.speed) / gameView.defaultTilesize;
                setPlayerRightX = (checkPlayerRightSide - entity.speed) / gameView.defaultTilesize;
                setPlayerLeftX = (checkPlayerLeftSide + entity.speed) / gameView.defaultTilesize;
                checkTile1 = gameView.tileManager.worldTileNumLayer1[setPlayerLeftX][setPlayerBottomY];
                checkTile2 = gameView.tileManager.worldTileNumLayer1[setPlayerRightX][setPlayerBottomY];
                checkTile1Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerLeftX][setPlayerBottomY];
                checkTile2Layer2 = gameView.tileManager.worldTileNumLayer2[setPlayerRightX][setPlayerBottomY];
                if (gameView.tileManager.tileInfoLayer1[checkTile1].tileCollision
                        || gameView.tileManager.tileInfoLayer1[checkTile2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile1Layer2].tileCollision
                        || gameView.tileManager.tileInfoLayer2[checkTile2Layer2].tileCollision) {
                    entity.collision = true;
                }
                break;
        }
    }

    public int checkObject(EntityInfo entity,boolean player, String direction) {
        int index = 999;
        //scans all the objects in the array checks if null or not if not null it will
        //check the solid area position (rectangle) & check if player has walked in front of
        //the object to block them from walking into the object

        for (int i = 0; i < gameView.objects.length; i++) {
            if (gameView.objects[i] != null) {
                //Easier way to write the code
                entity.hitbox.x += entity.posX;
                entity.hitbox.y += entity.posY;
                //Get solid area position
                gameView.objects[i].hitbox.x += gameView.objects[i].objPosX;
                gameView.objects[i].hitbox.y += gameView.objects[i].objPosY;
                if (direction.equals("up")) {
                    entity.hitbox.y -= entity.speed;
                    if (entity.hitbox.intersecting(gameView.objects[i].hitbox)) {
                        if (gameView.objects[i].collision == true) {
                            entity.collision = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                }else
                if (direction.equals("down")) {
                    entity.hitbox.y += entity.speed;
                    if (entity.hitbox.intersecting(gameView.objects[i].hitbox)) {
                        if (gameView.objects[i].collision == true) {
                            entity.collision = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                }else
                if (direction.equals("right")) {
                    entity.hitbox.x += entity.speed;
                    if (entity.hitbox.intersecting(gameView.objects[i].hitbox)) {
                        if (gameView.objects[i].collision == true) {
                            entity.collision = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                }else
                if (direction.equals("left")) {
                    entity.hitbox.x -= entity.speed;
                    if (entity.hitbox.intersecting(gameView.objects[i].hitbox)) {
                        if (gameView.objects[i].collision == true) {
                            entity.collision = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                }

                entity.hitbox.x = entity.defaultLeft;
                entity.hitbox.y = entity.defaultTop;
                gameView.objects[i].hitbox.x = gameView.objects[i].defaultLeft;
                gameView.objects[i].hitbox.y = gameView.objects[i].defaultTop;
            }

        }
        return index;
    }

    public int checkEntityCollision(EntityInfo entity, EntityInfo[] targetEntity, final String direction){
        int index = 999;

        for (int es = 0; es < targetEntity.length; es++) {
            if (targetEntity[es] != null) {
                entity.hitbox.x = entity.posX + entity.hitbox.x;
                entity.hitbox.y = entity.posY + entity.hitbox.y;
                targetEntity[es].hitbox.x = targetEntity[es].posX + targetEntity[es].hitbox.x;
                targetEntity[es].hitbox.y = targetEntity[es].posY + targetEntity[es].hitbox.y;

                if (direction.equals("up")) {
                    entity.hitbox.y -= entity.speed;
                }
                if (direction.equals("down")) {
                    entity.hitbox.y += entity.speed;
                }
                if (direction.equals("left")) {
                    entity.hitbox.x -= entity.speed;
                }
                if (direction.equals("right")) {
                    entity.hitbox.x += entity.speed;
                }
                if (entity.hitbox.intersecting(targetEntity[es].hitbox)) {
                    if (targetEntity[es].collidable) {
                        entity.collision = true;
                    }
                    index = es;
                }

                entity.hitbox.x = entity.defaultLeft;
                entity.hitbox.y = entity.defaultTop;
                targetEntity[es].hitbox.x = targetEntity[es].defaultLeft;
                targetEntity[es].hitbox.y = targetEntity[es].defaultTop;
            }
        }

        return index;
    }
    public void checkPlayerCollision(EntityInfo entity,String direction){
        entity.hitbox.x = entity.posX + entity.hitbox.x;
        entity.hitbox.y = entity.posY + entity.hitbox.y;
        gameView.player.hitbox.x = gameView.player.posX + gameView.player.hitbox.x;
        gameView.player.hitbox.y = gameView.player.posY + gameView.player.hitbox.y;

        if (direction.equals("up")) {
            entity.hitbox.y -= entity.speed;
        }
        if (direction.equals("down")) {
            entity.hitbox.y += entity.speed;
        }
        if (direction.equals("left")) {
            entity.hitbox.x -= entity.speed;
        }
        if (direction.equals("right")) {
            entity.hitbox.x += entity.speed;
        }

        if (entity.hitbox.intersecting(gameView.player.hitbox)){
            entity.collision = true;
            if (entity.collision) {
                if (!entity.beingattacked) {
                    //Attack or Miss player
                    int attack = gameView.getRandom(0, 3);
                    if (attack == 2) {
                        if (!gameView.player.beingattacked) {
                            gameView.player.beingattacked = true;
                            gameView.player.playerHealth--;
                        }
                    }
                }
            }
        }

        entity.hitbox.x = entity.defaultLeft;
        entity.hitbox.y = entity.defaultTop;
        gameView.player.hitbox.x = gameView.player.defaultLeft;
        gameView.player.hitbox.y = gameView.player.defaultTop;

    }
}
