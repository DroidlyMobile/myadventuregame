package droidlymobile.ca.thelegendadventure.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import droidlymobile.ca.thelegendadventure.GameView;
import droidlymobile.ca.thelegendadventure.Objects.Objects;
import droidlymobile.ca.thelegendadventure.R;
import droidlymobile.ca.thelegendadventure.utils.TrimBitmap;

public class Player extends EntityInfo{

    public Player(GameView gameView){
        this.gameView = gameView;
        initialize();
    }
    public void initialize(){
        entityWidth = gameView.defaultTilesize;
        entityHeight = gameView.defaultTilesize;
        hitboxpaint.setColor(Color.GREEN);
        screenPosX = gameView.getDisplayWidth()/2 - entityWidth/2;
        screenPosY = gameView.getDisplayHeight()/2 - entityHeight/2;
        initializeSpriteSheet();
        dummyImg = new TrimBitmap().trim(entitySprites[0]);

        hitbox.x = (entitySprites[0].getWidth()-dummyImg.getWidth())/2;
        hitbox.width = dummyImg.getWidth();
        hitbox.y = (entitySprites[0].getHeight()-dummyImg.getHeight())/2;
        hitbox.height = dummyImg.getHeight();
        defaultLeft = hitbox.x;
        defaultTop =  hitbox.y;
        defaultEntityImg = entitySprites[0];
        entityAnimMaxCount = 12;
        speed = 10;
        posX = gameView.defaultTilesize * 10;
        posY = gameView.defaultTilesize * 3;
        entityDefaultDirection = "down";
        equippedweapon = "none";
        maxminingspeed = 50;
        playerHealth = 6;
        playerMaxHealth = 6;
        maxAttackTimer = 300;

        setupMineableItems();
    }
    public void update(){
        x = (int) ((posX + rectF.left + speed)/gameView.defaultTilesize);
        y = (int) ((posY + rectF.top + speed)/gameView.defaultTilesize);
        updatePlayerDirection();
        updateEntityPosXY();
        checkBeingAttacked();
    }

    private void checkBeingAttacked() {
        if (beingattacked){
            spritePaint.setAlpha(100);
            if (playerHealth > 0) {
                attackTimer++;
                if (attackTimer > maxAttackTimer) {
                    attackTimer = 0;
                    beingattacked = false;
                    spritePaint.setAlpha(255);

                }
            }
        }
    }

    public void draw(Canvas canvas){
        /*canvas.drawRect(screenPosX + hitbox.x,screenPosY + hitbox.y,
                screenPosX + hitbox.x + hitbox.width,
                screenPosY + hitbox.y + hitbox.height,
                hitboxpaint);*/
        canvas.drawBitmap(defaultEntityImg,
                screenPosX,screenPosY,spritePaint);
    }
    public void updatePlayerDirection(){
        if (entityRight){
            entityDirection = "right";
            entityDefaultDirection = "right";
        }else if (entityLeft){
            entityDirection = "left";
            entityDefaultDirection = "left";
        }else if (entityUp){
            entityDirection = "up";
            entityDefaultDirection = "up";
        }else if (entityDown){
            entityDirection = "down";
            entityDefaultDirection = "down";
        }else if (entityAttacking){
            entityDirection = "attack";
        }else if (entityMining){
            entityDirection = "mining";
        }else if (!gameView.checkbuttonpressed){
            entityDirection = "buttonreleased";
            entityAnimNum = 1;
            entityAnimCounter = 0;
        }
        if (entityDirection.equals("attack")){
            attackAnimation();
        }else if (entityDirection.equals("mining")){
            miningAnimation();
        }else {
            updateEntityAnimations();
        }

        ////Once the user presses the direction they are going we will update their animation accordingly
    }

    private void updateEntityPosXY() {
        collision = false;
        gameView.collisionChecker.checkTileCollision(this,entityDirection);
        int objectID = gameView.collisionChecker.checkObject(this,true,entityDefaultDirection);
        checkObjectType(objectID);
        int bomberID = gameView.collisionChecker.checkEntityCollision(this,gameView.bombers,entityDefaultDirection);
        checkBomberEnemy(bomberID);
        int batID = gameView.collisionChecker.checkEntityCollision(this,gameView.bats,entityDefaultDirection);
        checkBatEnemy(batID);

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
        }
    }

    private void checkBomberEnemy(int checkBomber) {
        if (checkBomber!=999){
            gameView.bombers[checkBomber].playerCloseToEnemy = true;
            gameView.bombers[checkBomber].exploding = true;
            if (entityMining) {
                if (gameView.bombers[checkBomber].beingattacked == false) {
                    gameView.bombers[checkBomber].playerHealth --;
                    gameView.bombers[checkBomber].beingattacked = true;
                }
            }
        }
    }
    private void checkBatEnemy(int checkBat) {
        if (checkBat!=999){
            gameView.bats[checkBat].playerCloseToEnemy = true;
            if (entityMining) {
                if (gameView.bats[checkBat].beingattacked == false) {
                    gameView.bats[checkBat].playerHealth --;
                    gameView.bats[checkBat].beingattacked = true;
                }
            }
        }
    }

    private void checkObjectType(int objectID) {
        mineableItem = false;
        if (objectID!=999){
            int ID = gameView.objects[objectID].tileID;
            if (mineableitemslist.contains(ID)){
                mineableItem = true;
                gameView.objects[objectID].mineAble = true;
                /*entityMining = true;*/
                checkMining(objectID);
            }/*else {
                checkDroppedItems(objectID);
            }*/
            checkSpecialItems(objectID);
        }
    }

    private void checkDroppedItems(int objectID) {
        if (objectID!=999){
            //gameView.objects[objectID]=null;
            //31 is apple
            if (gameView.objects[objectID].tileID == 31){
                if (playerHealth<6) {
                    playerHealth++;
                }
                gameView.objects[objectID] = null;
            }
        }
    }

    private void checkMining(int objectID){
        nexttoobj = true;
        if (entityMining) {
            gameView.objects[objectID].beingAttacked = true;
            miningspeed++;
            if (miningspeed > maxminingspeed-1){
                gameView.objects[objectID].objHealth --;
                miningspeed = 0;
                if (gameView.objects[objectID].objHealth < 1){
                    checkObjectDrop(objectID);
                }
            }
        }else {
            miningspeed = 0;
        }
    }

    private void checkObjectDrop(int objectID) {
        int objnum = objectID;
        int objPosX = gameView.objects[objectID].objPosX/gameView.defaultTilesize;
        int objPosY = gameView.objects[objectID].objPosY/gameView.defaultTilesize;
        if (gameView.objects[objectID].tileID == 16){
            /*gameView.objects[objnum] = new Objects(gameView);
            gameView.objects[objnum].tileID = 31;
            gameView.objects[objnum].collision = false;
            gameView.objects[objnum].objDefaultImage = Bitmap.createScaledBitmap(
                    gameView.tileManager.tileImgs[31],
                    gameView.defaultTilesize / 2,
                    gameView.defaultTilesize / 2, false);
            gameView.objects[objnum].objPosX = objPosX * (gameView.defaultTilesize / 4);
            gameView.objects[objnum].objPosY = objPosY * (gameView.defaultTilesize / 4);*/
            if (playerHealth<6) {
                playerHealth++;
            }
            gameView.objects[objnum] = null;
        }else {

            gameView.objects[objnum] = null;
        }


    }

    public void dropItem(int objnum,int tileID,int objPosX,int objPosY,int dropitem){
        //if (dropitem == 2 || dropitem == 0) {
            gameView.objects[objnum] = new Objects(gameView);
            gameView.objects[objnum].tileID = 31;
            gameView.objects[objnum].collision = false;
            gameView.objects[objnum].objDefaultImage = Bitmap.createScaledBitmap(
                    gameView.tileManager.tileImgs[31],
                    gameView.defaultTilesize / 2,
                    gameView.defaultTilesize / 2, false);
            gameView.objects[objnum].objPosX = objPosX + (gameView.defaultTilesize / 4);
            gameView.objects[objnum].objPosY = objPosY + (gameView.defaultTilesize / 4);


    }


    private void checkSpecialItems(int objectID){
        //22 23 are axe and pickaxe
    }

    private void setupMineableItems(){
        mineableitemslist.add(11);
        mineableitemslist.add(16);
        mineableitemslist.add(21);
        mineableitemslist.add(12);
        mineableitemslist.add(13);
    }


    public void updateEntityAnimations() {
        if (gameView.checkbuttonpressed == false){
            entityDirection = "buttonreleased";
        }
        if (gameView.checkbuttonpressed) {
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

        } else {
            if (entityAnimCounter < entityAnimMaxCount + 1) {
                entityAnimCounter = 0;
                entityAnimNum = 1;
                if (entityAnimNum == 1) {
                    entityAnimNum = 2;
                } else if (entityAnimNum == 2) {
                    entityAnimNum = 3;
                } else if (entityAnimNum == 3) {
                    entityAnimNum = 4;
                }
            }
        }
            if (entityDirection.equals("down")) {
                if (entityAnimNum == 1 || entityAnimNum == 3) {
                    defaultEntityImg = entitySprites[0];
                }
                if (entityAnimNum == 2) {
                    defaultEntityImg = entitySprites[1];
                }
                if (entityAnimNum == 4) {
                    defaultEntityImg = entitySprites[2];
                }
            }
            if (entityDirection.equals("up")) {
                if (entityAnimNum == 1 || entityAnimNum == 3) {
                    defaultEntityImg = entitySprites[3];
                }
                if (entityAnimNum == 2) {
                    defaultEntityImg = entitySprites[4];
                }
                if (entityAnimNum == 4) {
                    defaultEntityImg = entitySprites[5];
                }
            }
            if (entityDirection.equals("right")) {
                if (entityAnimNum == 1 || entityAnimNum == 3) {
                    defaultEntityImg = entitySprites[9];
                }
                if (entityAnimNum == 2) {
                    defaultEntityImg = entitySprites[10];
                }
                if (entityAnimNum == 4) {
                    defaultEntityImg = entitySprites[11];
                }
            }
            if (entityDirection.equals("left")) {
                if (entityAnimNum == 1 || entityAnimNum == 3) {
                    defaultEntityImg = entitySprites[6];
                }
                if (entityAnimNum == 2) {
                    defaultEntityImg = entitySprites[7];
                }
                if (entityAnimNum == 4) {
                    defaultEntityImg = entitySprites[8];
                }
            }

        if (entityDirection.equals("buttonreleased")) {
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[3];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[0];
            }
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[9];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[6];
            }
        }
    }

    private void attackAnimation() {
        if (gameView.checkbuttonpressed){
            entityAnimCounter ++;
            if (entityAnimCounter>6){
                if (entityAnimNum == 1){
                    entityAnimNum = 2;
                }else if (entityAnimNum == 2){
                    entityAnimNum = 3;
                }else if (entityAnimNum == 3){
                    entityAnimNum = 4;
                }
                entityAnimCounter = 0;
            }
        }else if (entityAnimCounter < 6 + 1){
            entityAnimNum = 1;
            //entityAttackAnimCounter = 0;
            if (entityAnimNum == 1){
                entityAnimNum = 2;
            }else if (entityAnimNum == 2){
                entityAnimNum = 3;
            }else if (entityAnimNum == 3){
                entityAnimNum = 4;
            }
        }
        if (entityAnimNum == 1 || entityAnimNum == 2) {
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[13];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[14];
            }
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[15];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[16];
            }
        }
        if (entityAnimNum == 3) {
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[9];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[6];
            }
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[3];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[0];
            }
        }
        if (entityAnimNum == 4) {
            gameView.checkbuttonpressed = false;
            entityAttacking = false;
            entityDirection = "none";
            //Check if dpad is still being held down then continue walking

                if (gameView.checkbutton.equals("left")) {
                    entityLeft = true;
                    gameView.checkbuttonpressed = true;
                }
                if (gameView.checkbutton.equals("right")) {
                    entityRight = true;
                    gameView.checkbuttonpressed = true;
                }
                if (gameView.checkbutton.equals("up")) {
                    entityUp = true;
                    gameView.checkbuttonpressed = true;
                }
                if (gameView.checkbutton.equals("down")) {
                    entityDown = true;
                    gameView.checkbuttonpressed = true;
                }
        }
    }
    public void miningAnimation(){
        if (gameView.checkbuttonpressed){
            entityAnimCounter ++;
            if (entityAnimCounter>6){
                if (entityAnimNum == 1){
                    entityAnimNum = 2;
                }else if (entityAnimNum == 2){
                    entityAnimNum = 3;
                }else if (entityAnimNum == 3){
                    entityAnimNum = 4;
                }else if (entityAnimNum == 4){
                    entityAnimNum = 1;
                }
                entityAnimCounter = 0;
            }
        }else if (entityAnimCounter < 6 + 1){
            entityAnimNum = 1;
            //entityAttackAnimCounter = 0;
            if (entityAnimNum == 1){
                entityAnimNum = 2;
            }else if (entityAnimNum == 2){
                entityAnimNum = 3;
            }else if (entityAnimNum == 3){
                entityAnimNum = 4;
            }
        }
        if (entityAnimNum == 1 || entityAnimNum == 2) {
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[13];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[14];
            }
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[15];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[16];
            }
        }
        if (entityAnimNum == 3) {
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[9];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[6];
            }
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[3];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[0];
            }
        }
        if (entityAnimNum == 4) {
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[9];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[6];
            }
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[3];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[0];
            }
        }
        if (entityDirection.equals("buttonreleased")) {
            if (entityDefaultDirection.equals("up")) {
                defaultEntityImg = entitySprites[3];
            }
            if (entityDefaultDirection.equals("down")) {
                defaultEntityImg = entitySprites[0];
            }
            if (entityDefaultDirection.equals("right")) {
                defaultEntityImg = entitySprites[9];
            }
            if (entityDefaultDirection.equals("left")) {
                defaultEntityImg = entitySprites[6];
            }
        }
    }
    public void initializeSpriteSheet(){
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(gameView.getResources(),
                R.drawable.player1spritesheet,
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
