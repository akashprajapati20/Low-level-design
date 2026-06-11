public class InterviewProcess {
    private final InterviewHandler chain;

    public InterviewProcess(){

        this.chain = new TechnicalInterview();
        this.chain.setNext(new BarRaiserInteerview()).setNext(new BehaviourialInterview());
    }
    public void start(String candidate){
        chain.hire(candidate);
    }
}
