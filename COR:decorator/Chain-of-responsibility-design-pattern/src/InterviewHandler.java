
public abstract class InterviewHandler {
    protected InterviewHandler next;

    public InterviewHandler setNext(InterviewHandler next) {
        this.next = next;
        return next;
    }
    public void callNext(String candidate){
        if(next!=null){
         next.hire(candidate);
        }else{
            System.out.println("Candidate :"+candidate+" {} has passed all round");
        }
    }

    protected abstract void hire(String candidate) ;
}
