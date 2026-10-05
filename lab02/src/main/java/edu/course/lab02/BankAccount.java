package edu.course.lab02;

public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        if (balance < 0){
            throw new IllegalArgumentException("balance can not be negative");
        }
        this.balance = balance;
    }

    public void deposit(int amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("amount can not be negative or zero");
        }

        this.balance += amount;


    }
    public void withdraw(int amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("amount can not be negative or zero");
        }

        if (amount > this.balance) {
            throw new IllegalArgumentException("withdrawal can not be negative");
        }

        this.balance -= amount;


    }

    public int getBalance() {
        return this.balance;
    }
}
