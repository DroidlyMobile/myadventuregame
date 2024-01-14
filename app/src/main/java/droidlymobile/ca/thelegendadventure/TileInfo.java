package droidlymobile.ca.thelegendadventure;
import java.io.*;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class TileInfo {

    public InputStream inputStream;
    public BufferedReader bufferedReader;
    public GameView gameView;
    public Paint textpaint = new Paint();
    public int worldTileNumLayer1[][];//Receives the tile at the XY positions in the world
    public int worldTileNumLayer2[][];
    public int tileNum,tileNum2 = 0;//This is to check the tile the player is interacting with
    public int tileAnimNum = 1;
    public int tileAnimCount = 0;
    public int tileAnimMaxCount = 0;
    public boolean tileCollision = false;//Collision of tile is always set to false unless set true
    public boolean mineable = false;
    public int tilehealth = 4;
    public Bitmap defaultTileimg = null;//Default tile image drawn when cycling through world data
    public Bitmap[] tileImgs = new Bitmap[1000];//Add up to 1000 tile images
    public ArrayList<Integer> tilesList = new ArrayList<>();
    public ArrayList<String> collisionTiles = new ArrayList<>();
    public ArrayList<String> collisionTilesLayer2 = new ArrayList<>();
    public ArrayList<String> animatedTileList = new ArrayList<>();
}
