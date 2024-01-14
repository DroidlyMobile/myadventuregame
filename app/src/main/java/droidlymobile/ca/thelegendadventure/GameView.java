package droidlymobile.ca.thelegendadventure;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import droidlymobile.ca.thelegendadventure.Entities.EnemyBat;
import droidlymobile.ca.thelegendadventure.Entities.EnemyBomber;
import droidlymobile.ca.thelegendadventure.Entities.EnemySetup;
import droidlymobile.ca.thelegendadventure.Entities.EnemySpawner;
import droidlymobile.ca.thelegendadventure.Entities.Player;
import droidlymobile.ca.thelegendadventure.Entities.PlayerHealthbar;
import droidlymobile.ca.thelegendadventure.Objects.ObjectInfo;
import droidlymobile.ca.thelegendadventure.Objects.Objects;
import droidlymobile.ca.thelegendadventure.utils.BigGenerator;
import droidlymobile.ca.thelegendadventure.utils.CollisionChecker;
import droidlymobile.ca.thelegendadventure.utils.ControllerButtons;
import droidlymobile.ca.thelegendadventure.utils.DeadScreenPlayAgain;
import droidlymobile.ca.thelegendadventure.utils.Joystick;
import droidlymobile.ca.thelegendadventure.utils.YOUWINPLAYAGAIN;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    public GameLoop2 gameLoop;
    public Paint textpaint = new Paint();
    public boolean buttontouched = false;
    public Player player;
    public boolean checkbuttonpressed = false;
    public boolean buttonright,buttonleft,buttonup,buttondown,buttonattack = false;
    public CollisionChecker collisionChecker;
    public int maxColumns,maxRows,defaultTilesize;
    public TileManager tileManager;
    public String checkbutton = "none";
    public String checkbutton2 = "none";
    public int pointerid,pointerindex = 0;
    public double actuator = 0;
    public boolean leftbutton = false;
    public boolean abutton = false;
    public Joystick joystick,joystick2;
    public int joystickPointerId,joystickPointerId2 = 0;
    public int dpadpointerid,buttonapointerid = 0;
    public ControllerButtons buttonLeft,buttonRight,buttonDown,buttonUp,buttonA;
    public int buttonWidth,buttonHeight = 0;
    public int fingerX,fingerY =0;
    public Paint uipaint = new Paint();
    public int poopX = - 1;
    public int poopY = 0;
    public   ArrayList<Integer> yPositions = new ArrayList<>();
    public   ArrayList<Integer> xPositions = new ArrayList<>();
    public boolean entranceloaded = false;
    public int x1 = 0;
    public int tilecheck = 0;
    public ArrayList<Integer> holes = new ArrayList<Integer>();
    public Objects[] objects;
    public ArrayList<ObjectInfo> objectslist = new ArrayList<>();
    public ArrayList<Integer> objectsPosX = new ArrayList<Integer>();
    public ArrayList<Integer> objectsPosY = new ArrayList<Integer>();
    public ArrayList<Integer> tileIDS = new ArrayList<Integer>();
    public ArrayList<String> buttonlist = new ArrayList<>();
    public ArrayList<String> buttonpointerlist = new ArrayList<>();
    public int pointerID = 0;
    Vibrator v;
    public EnemySetup enemySetup;
    public EnemyBomber[] bombers = new EnemyBomber[200];
    public EnemyBat[] bats = new EnemyBat[200];
    public PlayerHealthbar healthbar;
    public ArrayList<Integer> collisionObjects = new ArrayList<>();
    public long currentime = 0;
    public long futuretime = 0;
    public long totaltime = 10;
    public EnemySpawner enemySpawner;
    Typeface plain;
    Typeface bold;
    public String numberofbombers = "";
    public String timeleft = "";
    public DeadScreenPlayAgain deadScreenPlayAgain;
    public boolean gameover = false;
    public boolean youwin = false;
    public YOUWINPLAYAGAIN youwinplayagain;

    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop2(this,surfaceHolder);
       /* plain = Typeface.createFromAsset(context.getAssets(),"font/mainfont.ttf");
        bold = Typeface.create(plain, Typeface.NORMAL);*/

        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(100);
        //textpaint.setTypeface(bold);
        uipaint.setColor(Color.BLACK);
        defaultTilesize = 192;
        player = new Player(this);
        collisionChecker = new CollisionChecker(this);
        maxColumns = 50;
        maxRows = 50;
        tileManager = new TileManager(this);
        buttonWidth = getDisplayHeight()/6;
        buttonHeight = getDisplayHeight()/6;
        setupButtons();
        new BigGenerator(this).startGeneration();
        objects = new Objects[2500];
        v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        enemySetup = new EnemySetup(this);
        enemySpawner = new EnemySpawner(this);
        generateObjects();
        healthbar = new PlayerHealthbar(this);
        currentime = System.currentTimeMillis();
        futuretime = currentime + 60000;
        deadScreenPlayAgain = new DeadScreenPlayAgain(this);
        youwinplayagain = new YOUWINPLAYAGAIN(this);
    }

    public void update(){
        for (int b = 0; b < bombers.length; b++){
            if (bombers[b]!=null){
                bombers[b].update();
                bombers[b].playerCloseToEnemy = false;
                bombers[b].exploding = false;
                if (bombers[b].playerHealth < 1){
                   bombers[b].exploding = true;
                   if (bombers[b].playerHealth == -1){
                       bombers[b]=null;
                       enemySpawner.numofbombers --;
                   }
                }
            }
        }
        for (int b = 0; b < bats.length; b++){
            if (bats[b]!=null){
                bats[b].update();
                if (bats[b].entityDead){
                    bats[b]=null;
                }
            }
        }
        for (int o = 0; o < objects.length; o++){
            if (objects[o]!=null){
                objects[o].update();
                objects[o].mineAble = false;
                objects[o].beingAttacked = false;
                if ((objects[o].objPosX/defaultTilesize) == 10
                        && (objects[o].objPosY/defaultTilesize) ==3){//Removes the obj from players default position when starting game
                    objects[o] = null;
                }

            }
        }
        player.update();
        healthbar.update();

        currentime = System.currentTimeMillis();
        if (futuretime-currentime <= 0){
            totaltime--;
            resetTime();
        }
        checkTimerorDead();
    }

    private void checkTimerorDead() {

    }

    private void resetTime() {
        currentime = System.currentTimeMillis();
        futuretime = currentime+60000;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        tileManager.draw(canvas);
        tileManager.drawAllAnimatedTiles(canvas);

        for (int o = 0; o < objects.length; o++){
            if (objects[o]!=null){
                objects[o].draw(canvas);
            }
        }
        for (int b = 0; b < bombers.length; b++){
            if (bombers[b]!=null){
                bombers[b].draw(canvas);
            }
        }
        for (int b = 0; b < bats.length; b++){
            if (bats[b]!=null){
                bats[b].draw(canvas);
            }
        }

        player.draw(canvas);

        buttonLeft.draw(canvas);
        buttonRight.draw(canvas);
        buttonDown.draw(canvas);
        buttonUp.draw(canvas);
        buttonA.draw(canvas);
        healthbar.draw(canvas);
        numberofbombers = "# Of Bombers " + String.valueOf(enemySpawner.numofbombers);
        timeleft = "Time Left " + totaltime + " min";
        canvas.drawText(numberofbombers,
                getDisplayWidth()-((numberofbombers.length()*textpaint.getTextSize())/2),
                textpaint.getTextSize()*2,textpaint);
        canvas.drawText(String.valueOf(player.playerHealth),
                getDisplayWidth()-((timeleft.length()*textpaint.getTextSize())/2),
                textpaint.getTextSize()*3,textpaint);
        if (player.playerHealth<1 || totaltime<1) {
            gameover = true;
            deadScreenPlayAgain.draw(canvas);
        }
        if (enemySpawner.numofbombers<1){
            youwin = true;
            gameover = true;
            youwinplayagain.draw(canvas);
        }
    }

    //Need to fix this by using my latest method with LIB GDX June 8th


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointerID = event.getPointerId(event.getActionIndex());
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:

                    //Get pointer ID when pointer goes down
                    //System.out.println("GET POINTER INFO " + pointerID);
                    //Then check what button is being pressed, store the button info into a list along with the pointer ID
                    //handle the action given to each button ex if the list contain A BUTTON then handle the A BUTTON event
                    //This allows for the user to press the DPAD while pressing the action button
                    if (!gameover) {
                        try {
                            if (buttonA.getXY((int) event.getX(pointerID),
                                    (int) event.getY(pointerID))
                                    && !buttonlist.contains("A BUTTON")) {
                                buttonlist.add("A BUTTON");
                                buttonpointerlist.add(String.valueOf(pointerID));
                            } else if (buttonLeft.getXY((int) event.getX(pointerID),
                                    (int) event.getY(pointerID))
                                    && !buttonlist.contains("LEFT BUTTON")
                                    && !buttonlist.contains("RIGHT BUTTON")
                                    && !buttonlist.contains("UP BUTTON")
                                    && !buttonlist.contains("DOWN BUTTON")) {
                                buttonlist.add("LEFT BUTTON");
                                buttonpointerlist.add(String.valueOf(pointerID));
                            } else if (buttonRight.getXY((int) event.getX(pointerID),
                                    (int) event.getY(pointerID))
                                    && !buttonlist.contains("RIGHT BUTTON")
                                    && !buttonlist.contains("LEFT BUTTON")
                                    && !buttonlist.contains("UP BUTTON")
                                    && !buttonlist.contains("DOWN BUTTON")) {
                                buttonlist.add("RIGHT BUTTON");
                                buttonpointerlist.add(String.valueOf(pointerID));
                            } else if (buttonUp.getXY((int) event.getX(pointerID),
                                    (int) event.getY(pointerID))
                                    && !buttonlist.contains("UP BUTTON")
                                    && !buttonlist.contains("LEFT BUTTON")
                                    && !buttonlist.contains("RIGHT BUTTON")
                                    && !buttonlist.contains("DOWN BUTTON")) {
                                buttonlist.add("UP BUTTON");
                                buttonpointerlist.add(String.valueOf(pointerID));
                            } else if (buttonDown.getXY((int) event.getX(pointerID),
                                    (int) event.getY(pointerID))
                                    && !buttonlist.contains("UP BUTTON")
                                    && !buttonlist.contains("LEFT BUTTON")
                                    && !buttonlist.contains("RIGHT BUTTON")
                                    && !buttonlist.contains("DOWN BUTTON")) {
                                buttonlist.add("DOWN BUTTON");
                                buttonpointerlist.add(String.valueOf(pointerID));
                            }
                            handleButtonsPressed();
                        } catch (IllegalArgumentException iae) {

                        }
                    /*return true;
                    case MotionEvent.ACTION_MOVE://GOING TO MESS WITH THIS MORE IF IT BECOMES AN ISSUE
                        if (!buttonLeft.getXY((int) event.getX(pointerID),
                                (int) event.getY(pointerID))){
                            if (buttonpointerlist.contains(String.valueOf(pointerID))) {
                                buttonlist.remove(buttonpointerlist.indexOf(String.valueOf(pointerID)));
                                buttonpointerlist.remove(buttonpointerlist.indexOf(String.valueOf(pointerID)));
                            }
                        }*/
                    }else {
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getContext().startActivity(intent);
                    }
                return true;
            //When the user releases the finger at pointer location, the indexed pointer removes the pointer and the
            //button details
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                //pointerID = event.getPointerId(event.getActionIndex());
                if (buttonpointerlist.contains(String.valueOf(pointerID))) {
                    handButtonsReleased(pointerID);
                    buttonlist.remove(buttonpointerlist.indexOf(String.valueOf(pointerID)));
                    buttonpointerlist.remove(buttonpointerlist.indexOf(String.valueOf(pointerID)));
                }

                //return true;

        }
        return super.onTouchEvent(event);
    }

    private void handleButtonsPressed() {
        if (buttonlist.contains("LEFT BUTTON")){
            checkbuttonpressed = true;
            player.entityLeft = true;
            checkbutton = "left";
            player.entityMining = false;
            buttonLeft.buttonpaint.setAlpha(100);
        }else
        if (buttonlist.contains("RIGHT BUTTON")){
            checkbuttonpressed = true;
            player.entityRight = true;
            checkbutton = "right";
            player.entityMining = false;
            buttonRight.buttonpaint.setAlpha(100);
        }else
        if (buttonlist.contains("UP BUTTON")){
            checkbuttonpressed = true;
            player.entityUp = true;
            checkbutton = "up";
            player.entityMining = false;
            buttonUp.buttonpaint.setAlpha(100);
        }else
        if (buttonlist.contains("DOWN BUTTON")){
            checkbuttonpressed = true;
            player.entityDown = true;
            checkbutton = "down";
            player.entityMining = false;
            buttonDown.buttonpaint.setAlpha(100);
        }else {
            if (!player.entityMining) {
            checkbuttonpressed = false;
            player.entityRight = false;
            player.entityLeft = false;
            player.entityUp = false;
            player.entityDown = false;

            }
            checkbutton = "none";
        }
        if (buttonlist.contains("A BUTTON")){
            if (!player.entityMining) {//Mining function
                checkbuttonpressed = true;
                player.entityMining = true;
                player.entityAnimCounter = 0;
                player.entityAnimNum = 1;
                player.entityRight = false;
                player.entityLeft = false;
                player.entityUp = false;
                player.entityDown = false;
                buttonA.buttonpaint.setAlpha(100);

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(10,
                            VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(10);
                }*/
            }
        }else {

        }
    }
    public void handButtonsReleased(int pointerID){
        String getPointInfo = buttonlist.get(buttonpointerlist.indexOf(String.valueOf(pointerID)));

        if (getPointInfo.equals("A BUTTON")){
            checkbuttonpressed = false;
            player.entityMining = false;
            player.entityDirection = "buttonreleased";
            player.entityAnimCounter = 0;
            player.entityAnimNum = 1;
            buttonA.buttonpaint.setAlpha(255);
            //handleButtonsPressed();
            if (player.entityDefaultDirection.equals("right") && buttonlist.contains("RIGHT BUTTON")){
                player.entityRight = true;
                checkbuttonpressed = true;
            }
            if (player.entityDefaultDirection.equals("left") && buttonlist.contains("LEFT BUTTON")){
                player.entityLeft = true;
                checkbuttonpressed = true;
            }
            if (player.entityDefaultDirection.equals("up") && buttonlist.contains("UP BUTTON")){
                player.entityUp = true;
                checkbuttonpressed = true;
            }
            if (player.entityDefaultDirection.equals("down") && buttonlist.contains("DOWN BUTTON")){
                player.entityDown = true;
                checkbuttonpressed = true;
            }
        }

        if (getPointInfo.equals("LEFT BUTTON")||
                getPointInfo.equals("RIGHT BUTTON")||
                getPointInfo.equals("UP BUTTON")||
                getPointInfo.equals("DOWN BUTTON")){
            if (!player.entityMining) {
                checkbuttonpressed = false;
            }
            buttonLeft.buttonpaint.setAlpha(255);
            buttonRight.buttonpaint.setAlpha(255);
            buttonUp.buttonpaint.setAlpha(255);
            buttonDown.buttonpaint.setAlpha(255);
            buttonA.buttonpaint.setAlpha(255);
            player.entityLeft = false;
            player.entityRight = false;
            player.entityUp = false;
            player.entityDown = false;
        }

    }

    public void generateObjects(){

        collisionObjects.add(16);
        collisionObjects.add(17);
        collisionObjects.add(14);
        collisionObjects.add(13);
        collisionObjects.add(12);
        collisionObjects.add(11);
        collisionObjects.add(21);

        int numofzeros = 0;
        int currentX = 1;
        int currentY = 1;
        while (currentY < maxRows-1){
            if (tileManager.worldTileNumLayer1[currentX][currentY] == 1){
                //get the position of the x and y positions
                    int tileID = setLayer2Objects(getRandom(0, 15));
                    objectsPosX.add(currentX);
                    objectsPosY.add(currentY);
                    tileIDS.add(tileID);
            }
            currentX++;
            if (currentX == maxColumns-1){
                currentX = 1;
                currentY++;
            }
        }

        for (int i = 0; i < objectsPosX.size(); i++) {
            if (tileIDS.get(i)!=0) {
                objects[i] = new Objects(this);
                objects[i].objPosX = objectsPosX.get(i) * defaultTilesize;
                objects[i].objPosY = objectsPosY.get(i) * defaultTilesize;
                objects[i].tileID = tileIDS.get(i);
                objects[i].objDefaultImage = Bitmap.createScaledBitmap(
                        tileManager.tileImgs[tileIDS.get(i)],
                        defaultTilesize,defaultTilesize,false);
                checkObjIDSetHealth(i);
                checkObjIDSetCollision(i);
            }else {
                //SPAWN BAT OR BOMBER IF NUMBER EQUALS TRUE
                enemySpawner.spawnEnemies(getRandom(0,3),objectsPosX.get(i),objectsPosY.get(i));
                numofzeros++;
            }
        }
        drawWorldBorder();
        int randomX = 1;
        int randomY = 1;
        randomX = getRandom(1,maxColumns-1);
        randomY = getRandom(1,maxRows - 1);
        for (int i = 0; i < objects.length; i++) {
            if (objects[i]==null) {//Spawn tool next to player
                objects[i] = new Objects(this);
                objects[i].objPosX = randomX * defaultTilesize;
                objects[i].objPosY = randomY * defaultTilesize;
                objects[i].tileID = 22;
                objects[i].objDefaultImage = Bitmap.createScaledBitmap(
                        tileManager.tileImgs[22],
                        defaultTilesize, defaultTilesize, false);
                break;
            }
        }
    }

    private void drawWorldBorder() {
        try {
            InputStream inputStream = getContext().getResources().openRawResource(
                    getContext().getResources().getIdentifier(
                            "worldborder","raw",
                            getContext().getPackageName()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< maxColumns && row < maxRows){
                String line = bufferedReader.readLine();
                while (column < maxColumns){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    if (num!=0) {
                        tileManager.worldTileNumLayer1[column][row] = num;
                    }
                    column ++;
                }
                if (column == maxColumns){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){

        }

    }

    private void checkObjIDSetCollision(int i) {
        if (collisionObjects.contains(tileIDS.get(i))){
            objects[i].collision = true;
        }
    }

    private void checkObjIDSetHealth(int i) {
        //Change this to an array list later so index tileID to health
        if (tileIDS.get(i)==16){
            objects[i].objHealth = 7;
            objects[i].objMaxHealth = 7;
        }
        if (tileIDS.get(i)==12 || tileIDS.get(i)==13){
            objects[i].objHealth = 3;
            objects[i].objMaxHealth = 3;
        }
        if (tileIDS.get(i)==11 || tileIDS.get(i)==21){
            objects[i].objHealth = 4;
            objects[i].objMaxHealth = 4;
        }
        if (tileIDS.get(i)==17 || tileIDS.get(i)==19){
            objects[i].objHealth = 8;
            objects[i].objMaxHealth = 8;
        }
    }

    public int setLayer2Objects(int random){
        int tileNum = 0;

        if (random == 0 || random == 5){
            tileNum = 11;
        }else
        if (random == 1 || random == 3){
            tileNum = 16;
        }else
        if (random == 4){
            tileNum = 17;
        }else
        if (random == 2 || random == 6 || random == 7 || random == 9){
            tileNum = 0;
        }else
        if (random == 8 && holes.size() < 8){
            holes.add(6);
            tileNum = 6;
        }else
        if (random == 11){
            tileNum = 13;
        }else
        if (random == 12){
            tileNum = 13;
        }else
        if (random == 13 || random == 14){
            tileNum = 21;
        }

        else {
            tileNum = 0;
        }

        return tileNum;
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)){
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop2(this,surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public int getDisplayWidth(){
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }
    public int getDisplayHeight(){
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }
    public int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    public void setupButtons(){
        buttonLeft = new ControllerButtons(this,0,buttonHeight*4);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        Bitmap dummy = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.buttonleft,
                bitmapOptions);
        Bitmap dummy2 = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.buttonright,
                bitmapOptions);
        Bitmap dummy3 = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.buttondown,
                bitmapOptions);
        Bitmap dummy4 = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.buttonup,
                bitmapOptions);
        Bitmap dummy5 = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.abutton,
                bitmapOptions);
        buttonLeft.buttonImage = Bitmap.createScaledBitmap(Bitmap.createBitmap(dummy,
                        0,
                        0,
                        16,
                        16),192,
                192,false);
        buttonRight = new ControllerButtons(this,buttonWidth*2,buttonHeight*4);
        buttonRight.buttonImage = Bitmap.createScaledBitmap(Bitmap.createBitmap(dummy2,
                        0,
                        0,
                        16,
                        16),192,
                192,false);

        buttonDown = new ControllerButtons(this,buttonWidth,buttonHeight*5);
        buttonDown.buttonImage = Bitmap.createScaledBitmap(Bitmap.createBitmap(dummy3,
                        0,
                        0,
                        16,
                        16),192,
                192,false);
        buttonUp = new ControllerButtons(this,buttonWidth,buttonHeight*3);
        buttonUp.buttonImage = Bitmap.createScaledBitmap(Bitmap.createBitmap(dummy4,
                        0,
                        0,
                        16,
                        16),192,
                192,false);
        buttonA = new ControllerButtons(this,getDisplayWidth() - buttonWidth,
                buttonHeight*4);
        buttonA.buttonImage = Bitmap.createScaledBitmap(Bitmap.createBitmap(dummy5,
                        0,
                        0,
                        16,
                        16),192,
                192,false);
    }
}
