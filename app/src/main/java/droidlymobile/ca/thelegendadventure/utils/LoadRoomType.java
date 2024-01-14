package droidlymobile.ca.thelegendadventure.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import droidlymobile.ca.thelegendadventure.GameView;

public class LoadRoomType {

    public GameView gameView;
    public LoadRoomType(GameView gameView){
        this.gameView = gameView;
    }

    public void loadRoomLayer1(int type,int posX,int posY,int roomSizeW,int roomSizeH){//Used to load map from the raw folder in res
        //Loads room file then draws it at the request location

        try {
            InputStream inputStream = gameView.getContext().getResources().openRawResource(
                    gameView.getContext().getResources().getIdentifier(
                            checkRoomTypeSetName(type),"raw", gameView.getContext().getPackageName()));
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< roomSizeW && row < roomSizeH){
                String line = bufferedReader.readLine();
                while (column < roomSizeW){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    gameView.tileManager.worldTileNumLayer1[column + posX][row +posY]= num;
                    column ++;
                }
                if (column == roomSizeW){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){

        }
    }

    private String checkRoomTypeSetName(int type) {
        String name = "";
        if (type == 0){
            //Select from 0 type rooms at random
            name = pickrandomroom0();
        }
        if (type == 1){
            //Select from 1 type rooms at random
            name = pickrandomroom1();
        }
        if (type == 2){
            //Select from 2 type rooms at random
            name = pickrandomroom2();
        }
        if (type == 3){
            //Select from 3 type rooms at random
            name = pickrandomroom3();
        }
        return name;
    }

    private String pickrandomroom0() {
        String roomname = "";
        int roomnumber = gameView.getRandom(0,0);
        if (roomnumber == 0){
            roomname = "zero1";
        }
        if (roomnumber == 1){
            roomname = "zero2";
        }
        if (roomnumber == 2){
            roomname = "zero3";
        }
        if (roomnumber == 3){
            roomname = "zero4";
        }
        if (roomnumber == 4){
            roomname = "zero5";
        }
        return roomname;
    }
    private String pickrandomroom1() {
        String roomname = "";
        int roomnumber = gameView.getRandom(0,0);
        if (roomnumber == 0){
            roomname = "one1";
        }
        if (roomnumber == 1){
            roomname = "one2";
        }
        if (roomnumber == 2){
            roomname = "one3";
        }
        if (roomnumber == 3){
            roomname = "one4";
        }
        if (roomnumber == 4){
            roomname = "one5";
        }
        return roomname;
    }
    private String pickrandomroom2() {
        String roomname = "";
        int roomnumber = gameView.getRandom(0,0);
        if (roomnumber == 0){
            roomname = "two1";
        }
        if (roomnumber == 1){
            roomname = "two2";
        }
        if (roomnumber == 2){
            roomname = "two3";
        }
        if (roomnumber == 3){
            roomname = "two4";
        }
        if (roomnumber == 4){
            roomname = "two5";
        }
        return roomname;
    }
    private String pickrandomroom3() {
        String roomname = "";
        int roomnumber = gameView.getRandom(0,0);
        if (roomnumber == 0){
            roomname = "three1";
        }
        if (roomnumber == 1){
            roomname = "three2";
        }
        if (roomnumber == 2){
            roomname = "three3";
        }
        if (roomnumber == 3){
            roomname = "three4";
        }
        if (roomnumber == 4){
            roomname = "three5";
        }
        return roomname;
    }

    public void loadRoomLayer2(int type,int posX,int posY,int roomSizeW,int roomSizeH){//Used to load map from the raw folder in res
        //Loads room file then draws it at the request location

        try {
            InputStream inputStream = gameView.getContext().getResources().openRawResource(
                    gameView.getContext().getResources().getIdentifier(
                            checkRoomTypeSetName(type),"raw", gameView.getContext().getPackageName()));
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< roomSizeW && row < roomSizeH){
                String line = bufferedReader.readLine();
                while (column < roomSizeW){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    gameView.tileManager.worldTileNumLayer2[column + posX][row +posY]= num;
                    column ++;
                }
                if (column == roomSizeW){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){

        }
    }
}
