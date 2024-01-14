package droidlymobile.ca.thelegendadventure.utils;

import java.util.Random;

import droidlymobile.ca.thelegendadventure.GameView;

public class BigGenerator {
    public GameView gameView;
    public LoadRoomType loadRoomType;
    public String roomdata = "";

    public BigGenerator(GameView gameView){
        this.gameView = gameView;
        loadRoomType = new LoadRoomType(gameView);
    }

    public void startGeneration(){
        //Sets up the room type then moves to the next room type
        final int x = 5;
        final int y = 5;

        int layout_room_types[][] = new int[x][y];

        Random rand = new Random();

        int curr_x = rand.nextInt(5);

        int curr_y = y - 1;
        int direction = 0;

        layout_room_types[curr_x][curr_y] = 1;

        //System.out.println("Starting with " + curr_x + " " + curr_y);

        while (curr_y >= 0) {


            if (direction == 0) {
                if (curr_y == 0)
                    direction = rand.nextInt(3) + 1;
                else {

                    int remaining_left_tiles = x - curr_x;
                    int remaining_right_tiles = x - remaining_left_tiles;


                    int l_chances = 5;
                    int r_chances = 5;

                    if (curr_x == 3 || curr_x == 4) {
                        l_chances = 25;
                    }
                    if (curr_x == 0) {
                        r_chances = 25;
                    }

                    int r = rand.nextInt(r_chances + l_chances) + 1;

                    if (r < l_chances) {
                        direction = 1;
                    }else if (r >= l_chances && r <= l_chances + r_chances){
                        direction = 2;
                    }else {
                        //System.out.println("direction 3 !!!");
                        direction = 3;
                    }

                }
            }

            if (direction == 1) {

                if (curr_x == 0) {
                    direction = 3;
                } else {
                    curr_x--;
                    layout_room_types[curr_x][curr_y] = 1;
                    //System.out.println("Direction " + direction + " Setting " + curr_x + "," + curr_y + " as " + 1);
                    for (int b = 0; b < y; b++) {
                        //System.out.print(" " + layout_room_types[b][curr_y]);
                    }
                    //System.out.println();
                }
            } else if (direction == 2) {

                if (curr_x == 4) {
                    direction = 3;
                } else {
                    curr_x++;
                    layout_room_types[curr_x][curr_y] = 1;
                    //System.out.println("Direction " + direction + " Setting " + curr_x + "," + curr_y + " as " + 1);
                    for (int b = 0; b < y; b++) {
                        //System.out.print(" " + layout_room_types[b][curr_y]);
                    }
                    //System.out.println();
                }

            } else if (direction == 3) {

                if (curr_y > 0) {

                    //System.out.println("Direction " + direction + " Setting " + curr_x + "," + curr_y + " as " + 2);
                    layout_room_types[curr_x][curr_y] = 2;

                    for (int b = 0; b < y; b++) {
                        //System.out.print(" " + layout_room_types[b][curr_y]);
                    }
                    //System.out.println();

                    if (curr_y >= 1) {
                        layout_room_types[curr_x][curr_y - 1] = 3;
                        //System.out.println("Direction " + direction + " Setting " + curr_x + "," + (curr_y - 1) + " as " + 3);
                        for (int b = 0; b < y; b++) {
                            //System.out.print(" " + layout_room_types[b][curr_y]);
                        }
                        //System.out.println();
                    }

                    direction = 0;
                    curr_y--;
                } else
                    break;

            }
        }

        for (int b = y - 1; b >= 0; b--) {
            for (int a = 0; a < x; a++) {
                if (roomdata.equals("")){
                    roomdata = String.valueOf(layout_room_types[a][b]);
                }else {
                    roomdata = roomdata + " " + layout_room_types[a][b];
                }
            }
        }
        generateRoomLayer1();

    }

    public String getRoomData(String data){
        return data;
    }
    public void generateRoomLayer1(){
        int nextRoomX = 0;
        int nextRoomY = 0;
        int roomX = 0;
        int roomY = 0;
        int nextRoomType = 0;
        int i = 0;
        int type[] = new int[30];
        String[] numbers = roomdata.split(" ");
        while (nextRoomY < 5 && nextRoomType<25) {
            roomX = 10 * nextRoomX;
            roomY = 10 * nextRoomY;
            loadRoomType.loadRoomLayer1(Integer.parseInt(numbers[nextRoomType]), roomX, roomY,
                    10, 10);
            nextRoomType++;
            nextRoomX++;

            if (nextRoomX == 5) {
                nextRoomX = 0;
                nextRoomY++;
            }
        }
    }
    public void generateRoomLayer2(){
        int nextRoomX = 0;
        int nextRoomY = 0;
        int roomX = 0;
        int roomY = 0;
        int nextRoomType = 0;
        int i = 0;
        int type[] = new int[30];
        String[] numbers = roomdata.split(" ");
        while (nextRoomY < 5 && nextRoomType<25) {
            roomX = 10 * nextRoomX;
            roomY = 10 * nextRoomY;
            loadRoomType.loadRoomLayer2(Integer.parseInt(numbers[nextRoomType]), roomX, roomY,
                    10, 10);
            nextRoomType++;
            nextRoomX++;

            if (nextRoomX == 5) {
                nextRoomX = 0;
                nextRoomY++;
            }
        }
    }
}
