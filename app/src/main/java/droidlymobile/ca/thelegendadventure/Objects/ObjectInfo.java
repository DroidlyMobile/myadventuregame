package droidlymobile.ca.thelegendadventure.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.RectF;

import droidlymobile.ca.thelegendadventure.GameView;
import droidlymobile.ca.thelegendadventure.R;
import droidlymobile.ca.thelegendadventure.utils.Hitbox;

public class ObjectInfo {
    public GameView gameView;
    public int objScreenX,objScreenY = 0;
    public int objPosX,objPosY = 0;
    public int objWidth,objHeight = 0;
    public int objHealth,objMaxHealth = 0;
    public int attackedTimer = 0;
    public int healthBarWidth,maxHealthBarWidth = 0;
    public RectF rectF = new RectF();
    public Hitbox hitbox = new Hitbox();
    public int defaultLeft,defaultTop = 0;
    public Bitmap[] objImages = new Bitmap[500];
    public Bitmap objDefaultImage = null;
    public Bitmap objHighlight = null;
    public boolean mineAble = false;
    public int tileID = 0;
    public boolean collision = false;
    public boolean showHealthBar = false;
    public boolean beingAttacked = false;
    public Paint hitboxpaint = new Paint();
    public Paint hitboxpaintbg = new Paint();
    public Paint textpaint = new Paint();
    public String objType = "";//If food then a timer starts before the food is removed from the world unless player collects it
}
