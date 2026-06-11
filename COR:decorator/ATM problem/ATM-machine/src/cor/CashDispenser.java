package cor;

import model.ATM;

public interface CashDispenser {
    void setNextDispenser(CashDispenser next);
    void dispense(ATM atm, int amount);
}
