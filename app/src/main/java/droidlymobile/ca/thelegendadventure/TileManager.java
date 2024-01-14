package droidlymobile.ca.thelegendadventure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TileManager extends TileInfo{

    public TileInfo[] tileInfoLayer1 = new TileInfo[1000];
    public TileInfo[] tileInfoLayer2 = new TileInfo[1000];

    public TileManager(GameView gameView){
        this.gameView = gameView;
        loadTilesheet();
        worldTileNumLayer1 = new int[gameView.maxColumns][gameView.maxRows];
        worldTileNumLayer2 = new int[gameView.maxColumns][gameView.maxRows];
        textpaint.setTextSize(50);
        textpaint.setColor(Color.BLUE);
    }
    public void draw(Canvas canvas){
        int tileCol = 0;
        int tileRow = 0;
        while (tileCol < gameView.maxColumns && tileRow < gameView.maxRows){
            tileNum = worldTileNumLayer1[tileCol][tileRow];
            tileNum2 = worldTileNumLayer2[tileCol][tileRow];//Gets the tileNum at the XY position from the txt data
            int tilePosX = tileCol * gameView.defaultTilesize;//Sets the tile at the position X in the world times the scaled tilesize 160 in example
            int tilePosY = tileRow * gameView.defaultTilesize;//Sets position Y times scaled tilesize
            int tileScreenX = tilePosX - gameView.player.posX + gameView.player.screenPosX;
            int tileScreenY = tilePosY - gameView.player.posY + gameView.player.screenPosY;

            if(tileScreenX > -gameView.defaultTilesize
                    && tileScreenY > -gameView.defaultTilesize
                    && tileScreenX < gameView.getDisplayWidth() + (gameView.defaultTilesize *2)
                    && tileScreenY < (gameView.getDisplayHeight() + gameView.defaultTilesize )){
                if (tileInfoLayer1[tileNum]!=null) {
                    if (tileInfoLayer1[tileNum].defaultTileimg != null) {
                        canvas.drawBitmap(tileInfoLayer1[tileNum].defaultTileimg, tileScreenX, tileScreenY, null);
                    }
                }
                /*if (tileInfoLayer2[tileNum2]!=null) {
                    if (tileInfoLayer2[tileNum2].defaultTileimg != null) {
                        //canvas.drawBitmap(tileInfoLayer2[tileNum2].defaultTileimg, tileScreenX, tileScreenY, null);
                        //canvas.drawText(String.valueOf(tileInfoLayer2[tileNum2].tilehealth),tileScreenX,tileScreenY,textpaint);
                    }
                }*/
            }
            tileCol ++;
            if (tileCol == gameView.maxColumns){//Check if tileCol reaches the end in this case 100 tiles then resets back to 0 then increases rows
                tileCol = 0;
                tileRow++;
            }
        }

    }
    public void loadMapLayer1(final String _mapname){//Used to load map from the raw folder in res
        try {
            inputStream = gameView.getContext().getResources().openRawResource(
                    gameView.getContext().getResources().getIdentifier(
                            _mapname,"raw", gameView.getContext().getPackageName()));
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< gameView.maxColumns && row < gameView.maxRows){
                String line = bufferedReader.readLine();
                while (column < gameView.maxColumns){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    worldTileNumLayer1[column][row]= num;
                    column ++;
                }
                if (column == gameView.maxColumns){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){

        }
    }
    public void loadMapLayer2(final String _mapname){//Used to load map from the raw folder in res
        try {
            inputStream = gameView.getContext().getResources().openRawResource(
                    gameView.getContext().getResources().getIdentifier(
                            _mapname,"raw", gameView.getContext().getPackageName()));
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< gameView.maxColumns && row < gameView.maxRows){
                String line = bufferedReader.readLine();
                while (column < gameView.maxColumns){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    worldTileNumLayer2[column][row]= num;
                    column ++;
                }
                if (column == gameView.maxColumns){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){

        }
    }

    public void loadTilesheet(){
        Bitmap tilesheet;
        int col1 = 0;
        int row1 = 0;
        int numoftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        tilesheet = BitmapFactory.decodeResource(gameView.getResources(),
                R.drawable.groundtilesheet,bitmapOptions);
        int maxcol1 = tilesheet.getWidth()/16;
        int maxrow1 = tilesheet.getHeight()/16;
        while (row1 < maxrow1){
            tileImgs[numoftiles] = Bitmap.createScaledBitmap(Bitmap.createBitmap
                    (tilesheet,col1 * 16,row1 * 16,16,16),gameView.defaultTilesize,
                    gameView.defaultTilesize,false);
            col1 ++;
            if (col1 == maxcol1){
                col1 = 0;
                row1 ++;
            }
            numoftiles ++;
            tilesList.add(numoftiles);
        }
        setUpTileInfo();//After tiles are loaded from tilesheet all tile details are setup
    }
    public void setUpTileInfo() {//This is used to check tiles that are collidable then sets the tile to be collidable
        collisionTiles.add(String.valueOf(0));
        collisionTiles.add(String.valueOf(2));
        collisionTiles.add(String.valueOf(3));
        collisionTiles.add(String.valueOf(9));
        collisionTiles.add(String.valueOf(10));
        collisionTiles.add(String.valueOf(11));
        collisionTiles.add(String.valueOf(7));


        collisionTilesLayer2.add(String.valueOf(11));
        collisionTilesLayer2.add(String.valueOf(16));
        collisionTilesLayer2.add(String.valueOf(17));

        for (int tileID = 0; tileID < tilesList.size(); tileID++) {

            tileInfoLayer1[tileID] = new TileInfo();
            tileInfoLayer1[tileID].defaultTileimg = tileImgs[tileID];
            if (collisionTiles.contains(String.valueOf((int) tileID))) {
                tileInfoLayer1[tileID].tileCollision = true;
            }

            tileInfoLayer2[tileID] = new TileInfo();
            tileInfoLayer2[tileID].defaultTileimg = tileImgs[tileID];
            if (collisionTilesLayer2.contains(String.valueOf((int) tileID))) {
                tileInfoLayer2[tileID].tileCollision = true;
                tileInfoLayer2[tileID].mineable = true;
            }
        }
        animatedTileList.add(String.valueOf(9));
        animatedTileList.add(String.valueOf(6));
    }

    public void drawAllAnimatedTiles(Canvas canvas){
        Bitmap animatedTilewater = null;
        Bitmap animatedTileflowers = null;
        tileAnimCount ++;
        if(tileAnimCount > 32){

            if(tileAnimNum == 1){
                tileAnimNum = 2;
            }else
            if(tileAnimNum == 2){
                tileAnimNum = 3;
            }else
            if(tileAnimNum == 3){
                tileAnimNum = 4;
            }else
            if(tileAnimNum == 4){
                tileAnimNum = 1;
            }
            tileAnimCount = 0;
        }

        if (tileAnimNum == 1) {
            animatedTilewater = tileInfoLayer1[10].defaultTileimg;
            animatedTileflowers = tileInfoLayer1[6].defaultTileimg;
        }
        if (tileAnimNum == 2) {
            animatedTilewater = tileInfoLayer1[15].defaultTileimg;
            animatedTileflowers = tileInfoLayer1[5].defaultTileimg;
        }
        if (tileAnimNum == 3) {
            animatedTilewater = tileInfoLayer1[20].defaultTileimg;
            animatedTileflowers = tileInfoLayer1[6].defaultTileimg;
        }
        if (tileAnimNum == 4) {
            animatedTilewater = tileInfoLayer1[25].defaultTileimg;
            animatedTileflowers = tileInfoLayer1[5].defaultTileimg;
        }
        int tileCol = 0;
        int tileRow = 0;

        while (tileCol < gameView.maxColumns && tileRow < gameView.maxRows){
            tileNum2 = worldTileNumLayer1[tileCol][tileRow];//Gets the tileNum at the XY position from the txt data
            int tilePosX = tileCol * gameView.defaultTilesize;//Sets the tile at the position X in the world times the scaled tilesize 160 in example
            int tilePosY = tileRow * gameView.defaultTilesize;//Sets position Y times scaled tilesize
            int tileScreenX = tilePosX - gameView.player.posX + gameView.player.screenPosX;
            int tileScreenY = tilePosY - gameView.player.posY + gameView.player.screenPosY;

            if(tileScreenX > -gameView.defaultTilesize
                    && tileScreenY > -gameView.defaultTilesize
                    && tileScreenX < gameView.getDisplayWidth() + (gameView.defaultTilesize *2)
                    && tileScreenY < (gameView.getDisplayHeight() + gameView.defaultTilesize )){

                if (tileNum2 == 10){
                    if (animatedTilewater != null) {
                        canvas.drawBitmap(animatedTilewater, tileScreenX, tileScreenY, null);
                    }
                }
                if (tileNum2 == 6){
                    if (animatedTileflowers != null) {
                        canvas.drawBitmap(animatedTileflowers, tileScreenX, tileScreenY, null);
                    }
                }
            }
            tileCol ++;
            if (tileCol == gameView.maxColumns){//Check if tileCol reaches the end in this case 100 tiles then resets back to 0 then increases rows
                tileCol = 0;
                tileRow++;
            }
        }
    }
}
