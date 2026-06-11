import java.util.Scanner;

public class BarRaiserInteerview extends InterviewHandler{
    @Override
    protected void hire(String candidate) {
        System.out.println("Bar Raiser round 1 for candidate:"+candidate);

        int score= new Scanner(System.in).nextInt();
        if(score>=70){
            System.out.println("candiadte passed Bar Raiser round");
            callNext(candidate);
            return ;
        }
        System.out.println("candiadte failed in round 2 Bar Raiser ");

    }
}
