package cor;

import model.ATM;

public class ThousandRupeeDenomination implements CashDispenser{
    private CashDispenser next;

    @Override
    public void setNextDispenser(CashDispenser next) {
      this.next=next;
    }

    @Override
    public void dispense(ATM atm, int amount) {
        int count = atm.getTwoThousandCount();
        int notes = Math.min(amount / 2000, count);
        atm.setTwoThousandCount(count - notes);

        int remainder = amount - notes * 2000;
        if(notes > 0) System.out.println("Dispensed " + notes + " x 2000 notes");

        if (remainder > 0 && next != null) {
            next.dispense(atm, remainder);
        }
    }
}
