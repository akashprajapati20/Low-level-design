import java.util.Scanner;

public class BehaviourialInterview extends InterviewHandler{
    @Override
    protected void hire(String candidate) {
        System.out.println("Behaviour round 1 for candidate:"+candidate);

        int score= new Scanner(System.in).nextInt();
        if(score>=70){
            System.out.println("candiadte passed Bar Behaviour round");
            callNext(candidate);
            return ;
        }
        System.out.println("candiadte failed in round 3 Behaviour ");

    }
}
