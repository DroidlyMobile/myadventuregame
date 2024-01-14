package droidlymobile.ca.thelegendadventure.Entities;

import android.content.Context;

import java.util.ArrayList;

import droidlymobile.ca.thelegendadventure.Entities.EnemyBat;
import droidlymobile.ca.thelegendadventure.Entities.EnemyBomber;
import droidlymobile.ca.thelegendadventure.GameView;

public class EnemySpawner{

    public GameView gameView;
    public ArrayList<Integer> bomberXpos = new ArrayList<>();
    public ArrayList<Integer> bomberYpos = new ArrayList<>();
    public ArrayList<Integer> objectsXArray = new ArrayList<>();
    public ArrayList<Integer> objectsYArray = new ArrayList<>();
    public int numofbombers,numofbats = 0;

    public EnemySpawner(GameView gameView) {
        this.gameView = gameView;
    }

    public void spawnBombers(int posX, int posY){
        if (numofbombers < gameView.bombers.length-1){
            gameView.bombers[numofbombers] = new EnemyBomber(gameView);
            gameView.bombers[numofbombers].posX = posX * gameView.defaultTilesize;
            gameView.bombers[numofbombers].posY = posY * gameView.defaultTilesize;
        }
        numofbombers++;

    }
    public void spawnBats(int posX,int posY){
        if (numofbats < gameView.bats.length-1){
            gameView.bats[numofbats] = new EnemyBat(gameView);
            gameView.bats[numofbats].posX = posX * gameView.defaultTilesize;
            gameView.bats[numofbats].posY = posY * gameView.defaultTilesize;
        }
        numofbats++;
    }

    public void spawnEnemies(int random,int posX,int posY) {
        if (random == 2){
            spawnBombers(posX,posY);
        }
        if (random == 3){
            spawnBats(posX,posY);
        }
    }
}
