package models;

import java.sql.SQLOutput;
import java.util.*;

public class Board {
    private final int size;
     private  final int sideLength;
     private final Cell[][] grid;

     public Board(int size)
     {
         this.size=size;
         this.sideLength=(int)Math.sqrt(size);
         this.grid=new Cell[sideLength][sideLength];

         int position=1;
         boolean leftToRight=true;

         for(int i=sideLength-1;i>=0;i--){
             if(leftToRight){
                 for(int j=0;j<sideLength;j++){
                     grid[i][j]=new Cell(position++);
                 }
             }else{
                 for(int j=sideLength;j>=0;j--){
                     grid[i][j]=new Cell(position++);
                 }
             }
             leftToRight=!leftToRight;
         }
     }

    private int getRow(int position){
         int row=(position-1)/sideLength;
         return sideLength-1-row;
    }

    private int getCol(int position){
         int row=getRow(position);
         int col=(position-1)%sideLength;
         return (row%2==0)?sideLength-1-col:col;
    }

    private Cell getCell(int position){
         return grid[getRow(position)][getCol(position)];
    }

    public int getSize() {
        return size;
    }

    public int getSideLength() {
        return sideLength;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean addObstacle(Obstacle obstacle){
         Cell srcCell=getCell(obstacle.getSrc());
         Cell destCell= getCell(obstacle.getDest());

         if(srcCell.hasObstacle() || destCell.hasObstacle()){
             return false;
         }
         srcCell.setObstacle(obstacle);
         return true;

    }

public int getNewPosition(Player player ,int offset){
         int newPosition= player.getPosition()+offset;
         if(newPosition>size){
             System.out.println("You are out of the board.");
             return player.getPosition();
         }
         Cell cell =grid[getRow(newPosition)][getCol(newPosition)];
         int finalPosition=cell.getFinalPosition();

         if(finalPosition<newPosition){
             System.out.println("opps! snakle has bitten");

         }else if(finalPosition>newPosition){
             System.out.println("congrats ! moving up the ladder");

         }else{
             System.out.println(player.getName()+" moved from "+ player.getPosition()+"to "+ finalPosition);

         }
         return finalPosition;
}

    public void printBoard(Queue<Player> players) {

        // Map position -> players present on that position
        Map<Integer, List<String>> playerMap = new HashMap<>();

        for (Player player : players) {
            playerMap
                    .computeIfAbsent(player.getPosition(), k -> new ArrayList<>())
                    .add(player.getName());
        }

        System.out.println("\n================ BOARD ================\n");

        for (int i = 0; i < sideLength; i++) {

            for (int j = 0; j < sideLength; j++) {

                Cell cell = grid[i][j];
                int position = cell.getPosition();

                StringBuilder content = new StringBuilder();

                // Cell Number
                content.append(position);

                // Snake / Ladder
                if (cell.hasObstacle()) {
                    Obstacle obstacle = cell.getObstacle();

                    if (obstacle.getDest() > obstacle.getSrc()) {
                        content.append("(L->").append(obstacle.getDest()).append(")");
                    } else {
                        content.append("(S->").append(obstacle.getDest()).append(")");
                    }
                }

                // Players
                if (playerMap.containsKey(position)) {
                    content.append(" ");
                    content.append(playerMap.get(position));
                }

                System.out.printf("%-25s", content);
            }

            System.out.println();
            System.out.println();
        }

        System.out.println("========================================\n");
    }
}
