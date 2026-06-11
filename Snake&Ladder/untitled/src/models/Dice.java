package models;

import java.util.Random;

public class Dice {
    private final int numberOfDices;
    private final Random random=new Random();

    public Dice(int numberOfDices) {
        this.numberOfDices = numberOfDices;
    }

   public int roll(){
        int sum=0;
        for(int i=0;i<numberOfDices;i++){
            sum+= random.nextInt(6)+1;
        }
        return sum;
   }
}
