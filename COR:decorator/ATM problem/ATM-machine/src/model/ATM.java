package model;


import enums.ATMStatus;

public class ATM {
    private final String id;
     private ATMStatus status;
    private double cashAvailable;


    private int twoThousandCount;
     private int fiveHundredCount;
     private int oneHundredCount;

    public ATM(String id, int twoThousandCount, int fiveHundredCount, int oneHundredCount) {
        this.id = id;
        this.cashAvailable = 2000 * twoThousandCount + 500 * fiveHundredCount + 100 * oneHundredCount;
        this.status = ATMStatus.IDLE;
        this.twoThousandCount = twoThousandCount;
        this.fiveHundredCount = fiveHundredCount;
        this.oneHundredCount = oneHundredCount;
    }

    public double getCashAvailable(){
        return cashAvailable;
    }
    public int getFiveHundredCount() {
        return fiveHundredCount;
    }

    public void setFiveHundredCount(int fiveHundredCount) {
        this.fiveHundredCount = fiveHundredCount;
    }

    public int getTwoThousandCount() {
        return twoThousandCount;
    }

    public void setTwoThousandCount(int twoThousandCount) {
        this.twoThousandCount = twoThousandCount;
    }

    public int getOneHundredCount() {
        return oneHundredCount;
    }

    public void setOneHundredCount(int oneHundredCount) {
        this.oneHundredCount = oneHundredCount;
    }

    public void setCashAvailable(double cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public ATMStatus getStatus() {
        return status;
    }

    public void setStatus(ATMStatus status) {
        this.status = status;
    }

}
