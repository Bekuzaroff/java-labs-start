package edu.course.lab02;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    @Test
    void withDrawChangesBalanceCorrect(){
        BankAccount account = new BankAccount(120000);
        account.withdraw(1000);

        assertEquals(119000, account.getBalance());
    }

    @Test
    void throwsExceptionAmountMoreBalance(){
        BankAccount account = new BankAccount(120000);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(121000));
    }
}
