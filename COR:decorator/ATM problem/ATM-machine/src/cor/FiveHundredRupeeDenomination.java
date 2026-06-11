package cor;

import model.ATM;

public class FiveHundredRupeeDenomination implements CashDispenser {
    CashDispenser next;
    @Override
    public void setNextDispenser(CashDispenser next) {
        this.next=next;
    }

    @Override
    public void dispense(ATM atm, int amount) {
         int notes=atm.getFiveHundredCount();

    }
}
