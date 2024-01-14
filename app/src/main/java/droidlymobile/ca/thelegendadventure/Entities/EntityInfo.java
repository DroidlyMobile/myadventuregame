package droidlymobile.ca.thelegendadventure.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import droidlymobile.ca.thelegendadventure.GameView;
import droidlymobile.ca.thelegendadventure.R;
import droidlymobile.ca.thelegendadventure.utils.Hitbox;

public class EntityInfo {
    public GameView gameView;
    public int posX,posY,screenPosX,screenPosY,x,y = 0;
    public int entityWidth,entityHeight = 0;
    public int entityHP,entityMaxHP = 0;
    public int speed = 0;
    public int entityAnimNum = 1;
    public int entityAnimCounter = 0;
    public int entityAnimMaxCount = 0;
    public int checkTile1,checkTile2 = 0;
    public int checkTile1Layer2,checkTile2Layer2 = 0;
    public int checkMineable1,checkMineable2 = 0;
    public int mineableposX,mineableposY = 0;
    public int miningspeed,maxminingspeed = 0;//miningspeed is just the count max is how long it takes to mine items each tool lowers maxminingspeed
    public int playerHealth,playerMaxHealth = 0;
    public int attackTimer,maxAttackTimer = 0;
    public String entityDirection,entityDefaultDirection = "";
    public String equippedweapon = "";
    public String tileType = "";
    public RectF rectF = new RectF();
    public Hitbox hitbox = new Hitbox();
    public int defaultLeft,defaultTop = 0;
    public int healthBarWidth,maxHealthBarWidth = 0;
    public int deathCounter = 0;
    public int randomDirectionCounter = 0;
    public Paint hitboxpaint = new Paint();
    public Paint hitboxpaintbg = new Paint();
    public Paint spritePaint = new Paint();
    public Bitmap defaultEntityImg = null;
    public Bitmap dummyImg = null;
    public Bitmap[] entitySprites = new Bitmap[100];
    public Bitmap[] enemySprites = new Bitmap[100];
    public boolean collision = false;
    public boolean collidable = true;//Some enemies fly so they aren't collidable when on top of the player
    public boolean entityRight,entityLeft,entityUp,entityDown,entityAttacking,entityMining = false;
    public boolean mineableItem = false;
    public boolean nexttoobj = false;
    public boolean beingattacked = false;
    public boolean playerCloseToEnemy = false;
    public boolean entityDead = false;
    public ArrayList<Integer> mineableitemslist = new ArrayList<Integer>();

}
