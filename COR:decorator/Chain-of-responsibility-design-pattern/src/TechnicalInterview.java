import java.util.Scanner;

public class TechnicalInterview extends InterviewHandler {
    @Override
    protected void hire(String candidate) {
     System.out.println("technical round 1 for candidate:"+candidate);

     int score= new Scanner(System.in).nextInt();
        if(score>=70){
            System.out.println("candiadte passed");
            callNext(candidate);
            return ;
        }
        System.out.println("candiadte failed in round 1");

    }
}
