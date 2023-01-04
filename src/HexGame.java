import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HexGame {
    public static final String STOP = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLU = "\u001B[34m";
    public static final String GRN = "\u001B[32m";

    private int BOARD_WIDTH = 11;
    private int BOARD_HEIGHT = 11;
    private UnionFind uf;
    private String[] colors;
    private int TOP;
    private int BOTTOM;
    private int LEFT;
    private int RIGHT;
    private String file;


    //for user size input
    public HexGame(int h, int w) throws IOException {
        BOARD_HEIGHT = h;
        BOARD_WIDTH = w;
        colors = new String[BOARD_HEIGHT * BOARD_WIDTH];

        File newFile = new File("randomMoves.txt");
        FileOutputStream fos = new FileOutputStream(newFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

        ArrayList<Integer> moves = new ArrayList();
        for(int i = 1; i <= (BOARD_WIDTH*BOARD_HEIGHT); i++){
            moves.add(i);
        }

        Random rand = new Random();
        while(moves.size() > 0){
            int index = rand.nextInt(moves.size());
            writer.write(moves.get(index).toString());
            moves.remove(index);
            writer.newLine();
        }
        writer.close();
        fos.close();
        file = "randomMoves.txt";
    }

    //for file read input
    public HexGame(String f){
        file = f;
    }

    private void setNeighbors(int[] n, int[] s){
        for(int i = 0; i < n.length; i++){
            n[i] = s[i];
        }
    }

    private int[] checkNeighbors(int space){
        //array is NW, NE, W, E, SW, SE
        int[] neighbors = new int[6];
        //check if on top edge
        if(space <= BOARD_WIDTH - 1){
            //if left top corner
            if(space == 0){
                setNeighbors(neighbors, new int[]{-1,-1,-1,space + 1, -1, space + BOARD_WIDTH});
            }
            //if right top corner
            else if(space == BOARD_WIDTH - 1){
                setNeighbors(neighbors, new int[]{-1,-1,space - 1,-1 , space + BOARD_WIDTH - 1, space + BOARD_WIDTH});
            }else {
                setNeighbors(neighbors, new int[]{-1,-1,space - 1,space + 1, space + BOARD_WIDTH - 1, space + BOARD_WIDTH});
            }
        //if on bottom edge
        }else if(space >= (BOARD_WIDTH*BOARD_HEIGHT) - BOARD_WIDTH){
            //if bottom left corner
            if(space == (BOARD_WIDTH*BOARD_HEIGHT) - BOARD_WIDTH){
                setNeighbors(neighbors, new int[]{space - BOARD_WIDTH, space - BOARD_WIDTH + 1,-1,space + 1, -1, -1});
            }
            //if bottom right corner
            else if(space == (BOARD_WIDTH*BOARD_HEIGHT) - 1){
                setNeighbors(neighbors, new int[]{space - BOARD_WIDTH,-1,space - 1,-1, -1, -1});
            }
            else{
                setNeighbors(neighbors, new int[]{space - BOARD_WIDTH, space - BOARD_WIDTH + 1, space - 1,space + 1, -1, -1});
            }
        //if on left edge
        }else if((space + 1) % (BOARD_WIDTH) == 1){
            setNeighbors(neighbors, new int[]{space - BOARD_WIDTH, space - BOARD_WIDTH + 1, -1 ,space + 1, -1, space + BOARD_WIDTH});
        //if on right edge
        }else if((space + 1) % (BOARD_WIDTH) == 0){
            setNeighbors(neighbors, new int[]{space - BOARD_WIDTH, -1, space - 1, -1, space + BOARD_WIDTH - 1, space + BOARD_WIDTH});
        //if anywhere in the middle
        }else{
            setNeighbors(neighbors, new int[]{space - BOARD_WIDTH, space - BOARD_WIDTH + 1, space - 1, space + 1, space + BOARD_WIDTH - 1, space + BOARD_WIDTH});
        }
        return neighbors;
    }

    public void nextMove(int space, String color){
        int[] neighbors = checkNeighbors(space);
        colors[space] = color;
        //check if on edge
        if(color.equals("RED")){
            //if on top
            if(space <= BOARD_WIDTH - 1){
                uf.union(TOP, space);
            }
            //if on bottom
            if(space >= (BOARD_WIDTH*BOARD_HEIGHT) - BOARD_WIDTH){
                uf.union(BOTTOM, space);
            }
        }
        if(color.equals("BLUE")){
            //if on right edge
            if((space + 1) % (BOARD_WIDTH) == 0) {
                uf.union(space, RIGHT);
            }
            //if on left edge
            if((space + 1) % (BOARD_WIDTH) == 1){
                uf.union(space, LEFT);
            }
        }

        for(int i = 0; i < neighbors.length; i++){
            //if a neighbor matches color
            if(neighbors[i] != -1) { //can't be outside board
                if (colors[neighbors[i]] != null) { //can't have an empty color
                    if (colors[neighbors[i]].equals(color)) {
                        uf.union(space, neighbors[i]);
                    }
                }
            }
        }

    }

    public void runGame(){
        uf = new UnionFind((BOARD_HEIGHT*BOARD_WIDTH)+4);
        colors = new String[(BOARD_HEIGHT*BOARD_WIDTH)+4];
        TOP = (BOARD_HEIGHT*BOARD_WIDTH);
        BOTTOM = (BOARD_HEIGHT*BOARD_WIDTH) + 1;
        LEFT = (BOARD_HEIGHT*BOARD_WIDTH) + 2;
        RIGHT = (BOARD_HEIGHT*BOARD_WIDTH) + 3;
        colors[TOP] = "RED";
        colors[BOTTOM] = "RED";
        colors[LEFT] = "BLUE";
        colors[RIGHT] = "BLUE";
        String winner = "NONE";

        try {
            Scanner scan = new Scanner(new File(file));
            int count = 0;
            while(scan.hasNext()){
                if(count % 2 == 0){ //even numbers are blue
                    nextMove(scan.nextInt() - 1, "BLUE");
                    count++;
                }
                else{
                    nextMove(scan.nextInt() - 1, "RED");
                    count++;
                }
                if(uf.find(TOP) == uf.find(BOTTOM)){
                    winner = "RED";
                    break;
                }else if(uf.find(LEFT) == uf.find(RIGHT)) {
                    winner = "BLUE";
                    break;
                }
            }
            System.out.println("-------> " + winner +  " has won after " + (count + 1) + " moves! Here is the final board");
            printBoard();

        }catch (IOException e){
            System.out.println("Bad file name");
        }
    }

    private void printBoard(){
        String indent = " ";
        for(int row = 0; row < BOARD_HEIGHT; row++){
            System.out.print(indent);
            for(int col = 0; col < BOARD_WIDTH; col++){
                String cur = colors[(BOARD_WIDTH*row) + col];
                if(cur == null){
                    System.out.print(" " + 0);
                }
                else if(cur.equals("RED")){
                    System.out.print(" " + RED + "R" + STOP);
                }
                else if(cur.equals("BLUE")){
                    System.out.print(" " + BLU + "B" + STOP);
                }

            }
            System.out.println();
            indent += " ";
        }
    }

}